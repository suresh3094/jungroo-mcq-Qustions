package com.jungroo.task.service.ServiceImpl;

import com.jungroo.task.constants.MessageCodes;
import com.jungroo.task.dto.UserDto;
import com.jungroo.task.entity.User;
import com.jungroo.task.io.BaseResponse;
import com.jungroo.task.io.StatusMessage;
import com.jungroo.task.repository.UserRepository;
import com.jungroo.task.service.UserService;
import com.jungroo.task.util.JwtUtil;
import com.jungroo.task.util.RandomIdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public BaseResponse CreateUser(UserDto userDto,Principal principal) throws Exception {

        String role=getRolesByLoggedInUser(principal);
        if (role.equals("Admin")) {
            User user = new User();
            BeanUtils.copyProperties(userDto, user);
            user.setUserId(RandomIdGenerator.generateRandomAplhaNumericString(6));
            userRepository.save(user);
        }
        else {
            return BaseResponse.builder()
                    .status(MessageCodes.SUCCESS_MSG)
                    .statusMessage(StatusMessage.builder()
                            .code(MessageCodes.SUCCESS)
                            .description(MessageCodes.SUCCESS_MSG)
                            .build())
                    .data("You have no access to add Role")
                    .build();
        }
        return BaseResponse.builder().status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build()).data("User Added").build();
    }

    @Override
    public BaseResponse Getall() throws Exception {
        List<User> user=userRepository.findAll();
        return BaseResponse.builder().status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build()).data(user).build();
    }

    @Override
    public BaseResponse update(String userId, UserDto userDto,Principal principal) throws Exception {
        String role=getRolesByLoggedInUser(principal);
        if (role.equals("Admin")) {
            User user = userRepository.getById(userId);
            BeanUtils.copyProperties(userDto, user);
            userRepository.save(user);
        }
        else {
            return BaseResponse.builder()
                    .status(MessageCodes.SUCCESS_MSG)
                    .statusMessage(StatusMessage.builder()
                            .code(MessageCodes.SUCCESS)
                            .description(MessageCodes.SUCCESS_MSG)
                            .build())
                    .data("You have no access to edit")
                    .build();
        }
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data("Updated Successfully")
                .build();
    }

    @Override
    public BaseResponse delete(String userId,Principal principal) throws Exception {
        String role=getRolesByLoggedInUser(principal);
        if(role.equals("Admin")) {
            userRepository.deleteById(userId);
        }
        else {
            return BaseResponse.builder()
                    .status(MessageCodes.SUCCESS_MSG)
                    .statusMessage(StatusMessage.builder()
                            .code(MessageCodes.SUCCESS)
                            .description(MessageCodes.SUCCESS_MSG)
                            .build())
                    .data("You have no access to delete user")
                    .build();
        }
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(userId+" "+"deleted user successfully")
                .build();
    }

    @Override
    public BaseResponse Login(UserDto userDto) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getUserName(), userDto.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        User user= userRepository.findByUserName(userDto.getUserName());
        UserDto userDto1=new UserDto();
        BeanUtils.copyProperties(user,userDto1);
        userDto1.setPassword(null);
        userDto1.setToken(jwtUtil.generateToken(userDto.getUserName()));
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(userDto1)
                .build();
    }

    public String getRolesByLoggedInUser(Principal principal) {
        String roles = getLoggedInUser(principal).getRole();
        List<String> assignRoles = Arrays.stream(roles.split(",")).collect(Collectors.toList());
        if (assignRoles.contains("Admin")) {
            return "Admin";
        }
        if (assignRoles.contains("ContentCreator")) {
            return "ContentCreator";
        }
        return "Student";
    }

    public User getLoggedInUser(Principal principal) {
        return userRepository.findByUserName(principal.getName());
    }
}

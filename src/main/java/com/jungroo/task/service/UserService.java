package com.jungroo.task.service;

import com.jungroo.task.dto.UserDto;
import com.jungroo.task.io.BaseResponse;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public interface UserService {
    BaseResponse CreateUser(UserDto userDto, Principal principal) throws Exception;
    BaseResponse Getall()throws Exception;
    BaseResponse update(String userId,UserDto userDto,Principal principal) throws Exception;
    BaseResponse delete(String userId,Principal principal) throws Exception;
    BaseResponse Login(UserDto userDto)throws Exception;
    public String getRolesByLoggedInUser(Principal principal) throws Exception;
}

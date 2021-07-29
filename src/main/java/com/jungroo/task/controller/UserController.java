package com.jungroo.task.controller;


import com.jungroo.task.dto.UserDto;
import com.jungroo.task.io.BaseResponse;
import com.jungroo.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse> CreateUser(@RequestBody UserDto userDto, Principal principal) throws Exception {
        BaseResponse response = userService.CreateUser(userDto,principal);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<BaseResponse> getAllUser() throws Exception {
        BaseResponse baseResponse = userService.Getall();
        return ResponseEntity.ok(baseResponse);
    }
    @PutMapping("/update/{userId}")
    public ResponseEntity<BaseResponse>updateUser(@PathVariable String userId,@RequestBody UserDto userDto,Principal principal) throws Exception{
        BaseResponse baseResponse=userService.update(userId,userDto,principal);
        return ResponseEntity.ok(baseResponse);
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<BaseResponse> deleteUser(@PathVariable String userId,Principal principal) throws Exception{
        BaseResponse baseResponse=userService.delete(userId,principal);
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/Login")
    public ResponseEntity<BaseResponse> processLogin(@RequestBody UserDto userDto) throws Exception {
        BaseResponse response = userService.Login(userDto);
        return ResponseEntity.ok(response);
    }
}
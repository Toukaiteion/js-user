package com.hseea.jsuser.controller;

import com.hseea.jsuser.entity.model.UserInfo;
import com.hseea.jsuser.service.UserService;
import com.hseea.jsuser.util.ResponseModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/user-menu/{username}")
    public ResponseModel<UserInfo> getMenu(@PathVariable("username") String username){
        return new ResponseModel<UserInfo>().success(userService.getMenu(username));
    }

}

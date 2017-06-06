package app.controller;

import app.Model.CommonResult;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xdcao on 2017/6/6.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login")
    public CommonResult login(@RequestParam String username,@RequestParam String password){
        return userService.login(username,password);
    }


    @RequestMapping(value = "/regist")
    public CommonResult regist(@RequestParam String username,@RequestParam String password){
        return userService.regist(username,password);
    }


}

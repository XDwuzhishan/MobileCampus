package app.controller;

import app.Model.CommonResult;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public CommonResult login(@RequestParam(required = true) String username,@RequestParam(required = true) String password){
        System.out.println("loginController");
        System.out.println("loginController");
        return userService.login(username,password);
    }

    @RequestMapping(value = "/check",method = RequestMethod.GET)
    public CommonResult check(@RequestParam(required = true) String username){
        System.out.println("checkController");
        System.out.println("checkController");
        return userService.check(username);
    }


    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public CommonResult regist(@RequestParam(required = true) String username,@RequestParam(required = true) String password,@RequestParam(required = false) String picUrl){
        System.out.println("registerController");
        System.out.println("registerController");
        return userService.regist(username,password,picUrl);
    }


}

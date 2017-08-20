package app.controller;

import app.Model.CommonResult;
import app.entity.User;
import app.manager.DataGridResult;
import app.service.UserService;


import ch.qos.logback.classic.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xdcao on 2017/6/6.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private org.slf4j.Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public CommonResult login(@RequestParam(required = true) String username,@RequestParam(required = true) String password){
        CommonResult result = userService.login(username, password);
        logger.info("login: "+username+" ,result: "+result.getMessage());
        return result;
    }

    @RequestMapping(value = "/check",method = RequestMethod.POST)
    public CommonResult check(@RequestParam(required = true) String username){
        CommonResult result = userService.check(username);
        logger.info("check: "+username+" ,result: "+result.getMessage());
        return result;
    }


    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public CommonResult regist(@RequestParam(required = true) String username,@RequestParam(required = true) String password,@RequestParam(required = false) String picUrl){
        CommonResult result = userService.regist(username, password, picUrl);
        logger.info("register: "+username+" ,result: "+result.getMessage());
        return result;
    }

    @RequestMapping(value = "/list")
    public DataGridResult listUsers(@RequestParam Integer page, @RequestParam Integer rows){
        logger.info("listUsers");
        return userService.getUsersByPage(page,rows);
    }

    @RequestMapping(value = "/delete")
    public CommonResult deleteUsers(@RequestParam List<Long> ids){

        return userService.deleteUsers(ids);
    }

    @RequestMapping(value = "/save")
    public CommonResult saveUser(String username,String password,String school,String sex,String nick,String image){
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setSchool(school);
        if (sex.equalsIgnoreCase("男")){
            user.setSex(true);
        }else if(sex.equalsIgnoreCase("女")){
            user.setSex(false);
        }
        user.setNick(nick);
        user.setPicUrl(image);
        CommonResult commonResult=userService.managerAddUser(user);
        logger.info("saveUser: "+user.getUsername()+" ,result: "+commonResult.getMessage());
        return commonResult;

    }


}

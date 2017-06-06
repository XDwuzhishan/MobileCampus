package app.service;

import app.Model.CommonResult;
import app.entity.User;
import app.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by xdcao on 2017/6/6.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public CommonResult login(String username,String password){
        User user=userMapper.getUserByName(username);
        if (user==null){
            return new CommonResult(200,"该用户不存在",null);
        }else {
            if (user.getPassword().equals(password)){
                return new CommonResult(200,"ok",null);
            }else {
                return new CommonResult(200,"密码错误",null);
            }
        }

    }

    @Transactional
    public CommonResult regist(String username, String password) {
        User check=userMapper.getUserByName(username);
        if (check!=null){
            return new CommonResult(200,"该用户名已被使用",null);
        }else {
            User newUser=new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            Date date=new Date();
            newUser.setCreated(date);
            newUser.setUpdated(date);
            userMapper.insert(newUser);
            return new CommonResult(200,"注册成功",null);
        }
    }


}

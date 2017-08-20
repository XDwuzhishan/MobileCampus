package app.service;

import app.Model.AnswerAndAuthor;
import app.Model.AnswerWithStar;
import app.Model.CommonResult;
import app.entity.User;
import app.manager.DataGridResult;
import app.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            return new CommonResult(500,"该用户不存在",null);
        }else {
            if (user.getPassword().equals(password)){
                return new CommonResult(200,"登陆成功",user);
            }else {
                return new CommonResult(500,"密码错误",null);
            }
        }

    }


    public CommonResult check(String username){
        User check=userMapper.getUserByName(username);
        if (check!=null){
            return new CommonResult(500,"该用户名已被使用",null);
        }else {
            return new CommonResult(200,"可以使用",null);
        }
    }

    @Transactional
    public CommonResult regist(String username, String password,String picUrl) {


        User newUser=new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setPicUrl(picUrl);
        Date date=new Date();
        newUser.setCreated(date);
        newUser.setUpdated(date);
        userMapper.insert(newUser);
        return new CommonResult(200,"注册成功",null);

    }


    public DataGridResult getUsersByPage(Integer page, Integer rows) {

        PageHelper.startPage(page,rows);
        List<User> list = userMapper.getAllUsers();
        PageInfo<User> pageInfo=new PageInfo<User>(list);
        DataGridResult dataGridResult=new DataGridResult();
        dataGridResult.setRows(list);
        dataGridResult.setTotal(pageInfo.getTotal());
        return dataGridResult;

    }

    // TODO: 2017/8/20 用户管理部分代码
    @Transactional
    public void deleteUserById(Long id){
        userMapper.delete(id);
    }

}

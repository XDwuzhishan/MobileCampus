package app.Shiro.t_controller;

import app.Shiro.t_entity.t_user;
import app.Shiro.t_service.ShiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xdcao on 2017/7/20.
 */
@RestController
public class ShiroController {

    @Autowired
    private ShiroService shiroService;

    @RequestMapping(value = "/shiro/userByRole")
    public List<Integer> shiroTest(@RequestParam int roleId){
        return shiroService.getUsersByRoleId(roleId);
    }

    @RequestMapping(value = "/shiro/user")
    public List<t_user> allUsers(){
        return shiroService.getAllUsers();
    }


}

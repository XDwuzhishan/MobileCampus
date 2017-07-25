package app.Shiro.t_service;

import app.mapper.t_permission_mapper;
import app.mapper.t_role_mapper;
import app.mapper.t_user_mapper;
import app.mapper.t_user_role_mapper;
import app.Shiro.t_entity.t_user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xdcao on 2017/7/20.
 */
@Service
public class ShiroService {

    @Autowired
    private t_user_role_mapper t_user_role_mapper;

    @Autowired
    private t_user_mapper t_user_mapper;

    @Autowired
    private t_role_mapper t_role_mapper;

    @Autowired
    private t_permission_mapper t_permission_mapper;


    public List<Integer> getUsersByRoleId(int roleId) {

        List<Integer> userIdsByRoleId = t_user_role_mapper.getUserIdsByRoleId(roleId);
        return userIdsByRoleId;

    }

    public List<t_user> getAllUsers() {
        return t_user_mapper.getAllUsers();
    }
}

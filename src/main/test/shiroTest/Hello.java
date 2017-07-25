package shiroTest;


import app.Shiro.ShiroUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by xdcao on 2017/7/20.
 */
public class Hello {

    @Test
    public void helloShiro(){
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:Shiro.ini");
        SecurityManager securityManager=factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject currentUser=SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken("caohao","123");
        try {
            currentUser.login(token);
            System.out.println("身份认证成功");
        }catch (AuthenticationException e){
            e.printStackTrace();
            System.out.println("身份认证失败");
        }

    }

    @Test
    public void roleShiro(){
        Subject currentUser= ShiroUtil.login("classpath:Shiro.ini","caohao","123");
        System.out.println(currentUser.hasRole("role1")?"有role1这个角色":"没有role1");
        boolean results[]=currentUser.hasRoles(Arrays.asList("role1","role2","role3"));
        System.out.println(results[0] ? "有role1这个角色" : "没有role1这个角色");
        System.out.println(results[1] ? "有role2这个角色" : "没有role2这个角色");
        System.out.println(results[2] ? "有role3这个角色" : "没有role3这个角色");
        System.out.println(currentUser.hasAllRoles(Arrays.asList("role1", "role2")) ? "有role1、role2这两个角色" : "role1、role2这两个角色不全有");
        currentUser.logout();
    }

    @Test
    public void permissionShiro(){
        Subject currentUser=ShiroUtil.login("classpath:Shiro.ini","caohao","123");
        System.out.println(currentUser.isPermitted("user:select") ? "有user:select这个权限"
                : "没有user:select这个权限");

        boolean[] results = currentUser.isPermitted("user:select",
                "user:update", "user:delete");
        System.out.println(results[0] ? "有user:select这个权限"
                : "没有user:select这个权限");
        System.out.println(results[1] ? "有user:update这个权限"
                : "没有user:update这个权限");
        System.out.println(results[2] ? "有user:delete这个权限"
                : "没有user:delete这个权限");

        System.out.println(currentUser.isPermittedAll("user:select",
                "user:update") ? "有user:select,user:update这两个权限"
                : "没有user:select,user:update这两个权限");

        currentUser.logout();
    }


}

package app.Shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * Created by xdcao on 2017/7/20.
 */
public class ShiroUtil {

    private static Factory<SecurityManager> factory=null;

    public static Subject login(String resPath,String username,String password){

        factory=new IniSecurityManagerFactory(resPath);

        SecurityManager manager = factory.getInstance();

        SecurityUtils.setSecurityManager(manager);

        Subject currentUser=SecurityUtils.getSubject();

        UsernamePasswordToken token=new UsernamePasswordToken(username,password);

        currentUser.login(token);

        return currentUser;


    }

}

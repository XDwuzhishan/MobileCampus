package shiroTest;

import app.BootApplication;
import app.mapper.t_user_role_mapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by xdcao on 2017/7/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BootApplication.class)
public class ShiroMapperTest {

    @Autowired
    private t_user_role_mapper t_user_role_mapper;

    @Test
    public void test1(){
        List<Integer> userIds=t_user_role_mapper.getRoleIdsByUserId(1);
        System.out.println(userIds);
    }

}

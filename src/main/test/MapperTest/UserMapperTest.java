package MapperTest;

import app.BootApplication;
import app.entity.User;
import app.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by xdcao on 2017/6/5.
 */
@SpringBootTest(classes = BootApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert(){
        User user=new User();
        user.setUsername("xiaoming");
        user.setPassword("123456");
        Date date=new Date();
        user.setCreated(date);
        user.setUpdated(date);
        userMapper.insert(user);
    }


}

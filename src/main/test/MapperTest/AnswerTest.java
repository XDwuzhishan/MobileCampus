package MapperTest;

import app.BootApplication;
import app.service.AnswerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by xdcao on 2017/6/13.
 */
@SpringBootTest(classes = BootApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class AnswerTest {

    @Autowired
    private AnswerService answerService;

    @Test
    public void insert(){
        answerService.addNewAnswer(4l,3l,"我的回答");
    }

}

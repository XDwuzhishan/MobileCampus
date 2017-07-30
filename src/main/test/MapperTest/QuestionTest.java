package MapperTest;

import app.BootApplication;
import app.entity.Question;
import app.mapper.QuestionMapper;
import app.service.QuestionService;
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
public class QuestionTest {

    @Autowired
    private QuestionService questionService;

    @Test
    public void insert(){
        questionService.addNewQuestion("一个小问题","没什么",4l,null);
    }

    @Test
    public void delete(){
        questionService.deleteQuestion(2);
    }


}

package app.service;

import app.Model.CommonResult;
import app.entity.Question;
import app.entity.User;
import app.mapper.QuestionMapper;
import app.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by xdcao on 2017/6/12.
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;


    @Transactional
    public CommonResult addNewQuestion(String title, String desc, Long ownerId) {

        User curUser=userMapper.getUserById(ownerId);

        Question question=new Question();
        question.setAcknum(0);
        question.setDesc(desc);
        question.setTitle(title);
        question.setOwnerId(ownerId);
        Date date=new Date();
        question.setCreated(date);
        question.setUpdated(date);
        question.setOwnername(curUser.getNick());

        questionMapper.insert(question);

        return new CommonResult(200,"添加问题成功",null);

    }


    @Transactional
    public CommonResult deleteQuestion(long id){
        questionMapper.delete(id);
        return new CommonResult(200,"删除问题成功",null);
    }

    @Transactional
    public void updateQuestion(Question question){
        questionMapper.update(question);
    }

}

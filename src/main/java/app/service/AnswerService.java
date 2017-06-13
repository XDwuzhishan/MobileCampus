package app.service;

import app.Model.CommonResult;
import app.entity.Answer;
import app.entity.Question;
import app.entity.User;
import app.mapper.AnswerMapper;
import app.mapper.QuestionMapper;
import app.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ansi.AnsiElement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by xdcao on 2017/6/12.
 */
@Service
public class AnswerService {

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Transactional
    public CommonResult addNewAnswer(Long userId,Long quesId,String content){
        User user=userMapper.getUserById(userId);
        Answer answer=new Answer();
        answer.setContent(content);
        answer.setOwnerId(userId);
        answer.setOwnername(user.getNick());
        answer.setStar(0);
        answer.setQuesId(quesId);
        Date date=new Date();
        answer.setCreated(date);
        answer.setUpdated(date);
        answerMapper.insert(answer);
        Question question=questionMapper.getById(quesId);
        question.setAcknum(question.getAcknum()+1);
        question.setUpdated(date);
        questionMapper.update(question);
        return new CommonResult(200,"添加回答成功",null);
    }

    @Transactional
    public CommonResult deleteAnswer(long id){
        Answer answer=answerMapper.getById(id);
        answerMapper.delete(id);
        Question question=questionMapper.getById(answer.getQuesId());
        question.setAcknum(question.getAcknum()-1);
        question.setUpdated(new Date());
        questionMapper.update(question);
        return new CommonResult(200,"删除答案成功",null);
    }

}

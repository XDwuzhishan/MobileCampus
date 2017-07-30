package app.service;

import app.Model.CommonResult;
import app.entity.Answer;
import app.entity.Comment;
import app.entity.Question;
import app.entity.User;
import app.mapper.AnswerMapper;
import app.mapper.CommentMapper;
import app.mapper.QuestionMapper;
import app.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ansi.AnsiElement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by xdcao on 2017/6/12.
 */
@Service
public class AnswerService {

    // TODO: 2017/7/30 answer的star与用户的关联


    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private CommentService commentService;

    @Transactional
    public CommonResult addNewAnswer(Long userId,Long quesId,String content,String images){
        User user=userMapper.getUserById(userId);
        Answer answer=new Answer();
        answer.setContent(content);
        answer.setOwnerId(userId);
        if(user.getNick()==null){
            answer.setOwnername(user.getUsername());
        }else {
            answer.setOwnername(user.getNick());
        }
        answer.setStar(0);
        answer.setQuesId(quesId);
        answer.setComNum(0);
        Date date=new Date();
        answer.setCreated(date);
        answer.setUpdated(date);
        answer.setImages(images);
        answerMapper.insert(answer);
        Question question=questionMapper.getById(quesId);
        question.setAcknum(question.getAcknum()+1);
        question.setUpdated(date);
        questionMapper.update(question);
        logger.info("添加回答成功");
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
        List<Comment> comments=commentService.getCommentsByAnswerId(id);
        if (comments!=null&&comments.size()>0){
            for (Comment comment:comments){
                commentService.deleteComment(comment.getId());
            }
        }
        logger.info("删除答案成功");
        return new CommonResult(200,"删除答案成功",null);
    }


    public CommonResult getAnswerListByPage(long quesId,int page,int rows){
        PageHelper.startPage(page,rows);
        List<Answer> answers=answerMapper.getByQuesId(quesId);
        PageInfo<Answer> pageInfo=new PageInfo<Answer>(answers);
        CommonResult commonResult=new CommonResult(200,"ok",answers);
        return commonResult;
    }



    public List<Answer> getAnswerByQuestionId(long id) {

        return answerMapper.getByQuesId(id);

    }

    @Transactional
    public CommonResult star(Long answerId) {

        Answer answer=answerMapper.getById(answerId);
        answer.setStar(answer.getStar()+1);
        answer.setUpdated(new Date());
        answerMapper.update(answer);
        return new CommonResult(200,"success",null);

    }

    @Transactional
    public CommonResult unStar(Long answerId) {
        Answer answer=answerMapper.getById(answerId);
        int starNum=answer.getStar();
        if (starNum>0){
            answer.setStar(answer.getStar()-1);
            answer.setUpdated(new Date());
            answerMapper.update(answer);
            return new CommonResult(200,"success",null);
        }else {
            return new CommonResult(500,"star is zero",null);
        }
    }



}

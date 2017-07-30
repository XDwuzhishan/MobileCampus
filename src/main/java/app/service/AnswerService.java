package app.service;

import app.Model.AnswerAndAuthor;
import app.Model.AnswerWithStar;
import app.Model.CommonResult;
import app.entity.*;
import app.mapper.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.tools.javac.comp.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ansi.AnsiElement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xdcao on 2017/6/12.
 */
@Service
public class AnswerService {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private CommentService commentService;

    @Autowired
    private StarMapper starMapper;

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
        starMapper.deleteByAnswer(answer.getId());
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


    public CommonResult getAnswerListByPage(long user_id,long quesId,int page,int rows){
        PageHelper.startPage(page,rows);
        List<AnswerWithStar> list = getAnswerByQuestionId(user_id, quesId);
        List<AnswerAndAuthor> answerAndAuthors=new ArrayList<AnswerAndAuthor>();
        for (AnswerWithStar answerWithStar:list){
            User user=userMapper.getUserById(answerWithStar.getAnswer().getOwnerId());
            AnswerAndAuthor answerAndAuthor=new AnswerAndAuthor();
            answerAndAuthor.setAnswerWithStar(answerWithStar);
            answerAndAuthor.setUser(user);
            answerAndAuthors.add(answerAndAuthor);
        }
        PageInfo<AnswerWithStar> pageInfo=new PageInfo<AnswerWithStar>(list);
        CommonResult commonResult=new CommonResult(200,"ok",answerAndAuthors);
        return commonResult;
    }



    public List<AnswerWithStar> getAnswerByQuestionId(long user_id,long id) {

        List<Answer> answerList = answerMapper.getByQuesId(id);
        List<AnswerWithStar> answerWithStarList=new ArrayList<AnswerWithStar>();
        for (Answer answer:answerList){
            Star star=starMapper.getOne(answer.getId(),user_id);
            if (star!=null){
                AnswerWithStar answerWithStar=new AnswerWithStar();
                answerWithStar.setAnswer(answer);
                answerWithStar.setStared(true);
                answerWithStarList.add(answerWithStar);
            }else {
                AnswerWithStar answerWithStar=new AnswerWithStar();
                answerWithStar.setAnswer(answer);
                answerWithStar.setStared(false);
                answerWithStarList.add(answerWithStar);
            }
        }
        return answerWithStarList;

    }

    @Transactional
    public CommonResult star(Long answerId,Long userId) {

        Answer answer=answerMapper.getById(answerId);
        answer.setStar(answer.getStar()+1);
        answer.setUpdated(new Date());
        answerMapper.update(answer);

        Star star=new Star();
        star.setUserId(userId);
        star.setAnswerId(answerId);
        starMapper.insert(star);

        return new CommonResult(200,"success",null);

    }

    @Transactional
    public CommonResult unStar(Long user_id,Long answerId) {
        Answer answer=answerMapper.getById(answerId);
        int starNum=answer.getStar();
        if (starNum>0){
            answer.setStar(answer.getStar()-1);
            answer.setUpdated(new Date());
            answerMapper.update(answer);
            starMapper.deleteOne(user_id,answerId);
            return new CommonResult(200,"success",null);
        }else {
            return new CommonResult(500,"Star is zero",null);
        }
    }



}

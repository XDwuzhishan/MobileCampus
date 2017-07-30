package app.service;

import app.Model.CommonResult;
import app.Model.QuesAndAuthor;
import app.entity.Answer;
import app.entity.Question;
import app.entity.User;
import app.mapper.AnswerMapper;
import app.mapper.QuestionMapper;
import app.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xdcao on 2017/6/12.
 */
@Service
public class QuestionService {


    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AnswerMapper answerMapper;


    @Transactional
    @CacheEvict(value = {"quesByPage","myQues"})
    public CommonResult addNewQuestion(String title, String desc, Long ownerId,String images) {

        User curUser=userMapper.getUserById(ownerId);

        Question question=new Question();
        question.setAcknum(0);
        question.setMydesc(desc);
        question.setTitle(title);
        question.setOwnerId(ownerId);
        Date date=new Date();
        question.setCreated(date);
        question.setUpdated(date);
        question.setImages(images);
        if(curUser.getNick()==null){
            question.setOwnername(curUser.getUsername());
        }else {
            question.setOwnername(curUser.getNick());
        }

        questionMapper.insert(question);

        return new CommonResult(200,"添加问题成功",null);

    }


    @Transactional
    @CacheEvict(value = {"quesByPage","myQues"})
    public CommonResult deleteQuestion(long id){
        List<Answer> answers=answerMapper.getByQuesId(id);
        if (answers!=null&&answers.size()>0){
            for (Answer answer:answers){
                answerMapper.delete(answer.getId());
            }
        }
        questionMapper.delete(id);
        return new CommonResult(200,"删除问题成功",null);
    }

    @Cacheable(value = "quesByPage",keyGenerator = "keyGenerator")
    public CommonResult getQuestionListByPage(int page,int rows){
        PageHelper.startPage(page,rows);
        List<QuesAndAuthor> quesAndAuthorList=new ArrayList<QuesAndAuthor>();
        List<Question> questions=questionMapper.getAll();
        for (Question question:questions){
            QuesAndAuthor quesAndAuthor=new QuesAndAuthor();
            User user=userMapper.getUserById(question.getOwnerId());
            quesAndAuthor.setQuestion(question);
            quesAndAuthor.setUser(user);
            quesAndAuthorList.add(quesAndAuthor);
        }
        PageInfo<Question> pageInfo=new PageInfo<Question>(questions);
        CommonResult commonResult=new CommonResult(200,"ok",quesAndAuthorList);
        return commonResult;
    }

    @Cacheable(value = "myQues",keyGenerator = "keyGenerator")
    public CommonResult getMyQuestions(long ownerId) {

        List<Question> questions=questionMapper.getByOwnerId(ownerId);
        return new CommonResult(200,"ok",questions);

    }
}

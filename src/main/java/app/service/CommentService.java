package app.service;

import app.Model.CommentAndAuthor;
import app.Model.CommonResult;
import app.entity.Answer;
import app.entity.Comment;
import app.entity.User;
import app.mapper.AnswerMapper;
import app.mapper.CommentMapper;
import app.mapper.ReplyMapper;
import app.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xdcao on 2017/6/16.
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private ReplyMapper replyMapper;

    @Transactional
    @CacheEvict(value = "ComByAnswerId")
    public CommonResult addNewComment(Long answerId, String content, Long ownerId,String images) {
        Comment comment=new Comment();
        comment.setAnswer_id(answerId);
        comment.setContent(content);
        comment.setImages(images);
        Date date=new Date();
        comment.setCreated(date);
        comment.setUpdated(date);
        comment.setOwnerId(ownerId);
        User user=userMapper.getUserById(ownerId);
        if(user.getNick()==null){
            comment.setOwnerName(user.getUsername());
        }else {
            comment.setOwnerName(user.getNick());
        }
        comment.setRepNum(0);
        commentMapper.insert(comment);
        Answer answer=answerMapper.getById(answerId);
        answer.setUpdated(date);
        answer.setComNum(answer.getComNum()+1);
        answerMapper.update(answer);
        return new CommonResult(200,"ok",null);
    }


    @Transactional
    @CacheEvict(value = "ComByAnswerId")
    public CommonResult deleteComment(Long id) {
        Comment comment=commentMapper.getComById(id);
        Answer answer=answerMapper.getById(comment.getAnswer_id());
        answer.setComNum(answer.getComNum()-1);
        answer.setUpdated(new Date());
        answerMapper.update(answer);
        commentMapper.delete(id);
        replyMapper.deleteByComId(id);
        return new CommonResult(200,"ok",null);
    }

    @Cacheable(value = "ComByAnswerId",keyGenerator = "keyGenerator")
    public CommonResult getCommentsByAnswerId(long id) {

        List<Comment> commentList = commentMapper.getCommentsByAnswerId(id);
        List<CommentAndAuthor> commentAndAuthors=new ArrayList<CommentAndAuthor>();
        for (Comment comment:commentList){
            CommentAndAuthor commentAndAuthor=new CommentAndAuthor();
            User user=userMapper.getUserById(comment.getOwnerId());
            commentAndAuthor.setComment(comment);
            commentAndAuthor.setUser(user);
            commentAndAuthors.add(commentAndAuthor);
        }
        return new CommonResult(200,"success",commentAndAuthors);

    }
}

package app.service;

import app.Model.CommonResult;
import app.entity.Answer;
import app.entity.Comment;
import app.entity.User;
import app.mapper.AnswerMapper;
import app.mapper.CommentMapper;
import app.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ReplyService replyService;

    @Transactional
    public CommonResult addNewComment(Long answerId, String content, Long ownerId) {
        Comment comment=new Comment();
        comment.setAnswer_id(answerId);
        comment.setContent(content);
        Date date=new Date();
        comment.setCreated(date);
        comment.setUpdated(date);
        comment.setOwnerId(ownerId);
        User user=userMapper.getUserById(ownerId);
        comment.setOwnerName(user.getNick());
        comment.setRepNum(0);
        commentMapper.insert(comment);
        Answer answer=answerMapper.getById(answerId);
        answer.setUpdated(date);
        answer.setComNum(answer.getComNum()+1);
        answerMapper.update(answer);
        return new CommonResult(200,"ok",null);
    }


    public CommonResult deleteComment(Long id) {
        commentMapper.delete(id);
        // TODO: 2017/6/16  把对应的回复也都删掉
        return new CommonResult(200,"ok",null);
    }

    public List<Comment> getCommentsByAnswerId(long id) {

        return commentMapper.getCommentsByAnswerId(id);

    }
}

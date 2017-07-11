package app.service;

import app.Model.CommonResult;
import app.entity.Comment;
import app.entity.Reply;
import app.entity.User;
import app.mapper.CommentMapper;
import app.mapper.ReplyMapper;
import app.mapper.UserMapper;
import com.sun.java.swing.plaf.windows.WindowsBorders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by xdcao on 2017/6/16.
 */
@Service
public class ReplyService {

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Transactional
    public CommonResult addNewReply(Long ownerId, Long comId, String content,Long to) {

        User user=userMapper.getUserById(ownerId);
        Comment comment=commentMapper.getComById(comId);
        Reply reply=new Reply();
        reply.setComId(comId);
        reply.setContent(content);
        reply.setOwnerId(ownerId);
        if(user.getNick()==null){
            reply.setOwnerName(user.getUsername());
        }else {
            reply.setOwnerName(user.getNick());
        }
        reply.setTo(to);
        Date date=new Date();
        reply.setCreated(date);
        reply.setUpdated(date);
        replyMapper.insert(reply);
        comment.setRepNum(comment.getRepNum()+1);
        comment.setUpdated(date);
        commentMapper.update(comment);
        return new CommonResult(200,"ok",null);
    }


    @Transactional
    public CommonResult deleteReply(Long id) {

        Reply reply=replyMapper.getReplyById(id);
        Comment comment=commentMapper.getComById(reply.getComId());
        comment.setRepNum(comment.getRepNum()-1);
        comment.setUpdated(new Date());
        commentMapper.update(comment);
        replyMapper.delete(id);

        return new CommonResult(200,"ok",null);

    }
}

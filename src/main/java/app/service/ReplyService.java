package app.service;

import app.Model.CommonResult;
import app.Model.ReplyAndAuthor;
import app.entity.Comment;
import app.entity.Reply;
import app.entity.User;
import app.mapper.CommentMapper;
import app.mapper.ReplyMapper;
import app.mapper.UserMapper;
import com.sun.java.swing.plaf.windows.WindowsBorders;
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
public class ReplyService {

    @Autowired
    private ReplyMapper replyMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Transactional
    @CacheEvict(value = "getComById")
    public CommonResult addNewReply(Long ownerId, Long comId, String content,Long to,String images) {

        User user=userMapper.getUserById(ownerId);
        Comment comment=commentMapper.getComById(comId);
        Reply reply=new Reply();
        reply.setComId(comId);
        reply.setContent(content);
        reply.setOwnerId(ownerId);
        reply.setImages(images);
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
    @CacheEvict(value = "getComById")
    public CommonResult deleteReply(Long id) {

        Reply reply=replyMapper.getReplyById(id);
        Comment comment=commentMapper.getComById(reply.getComId());
        comment.setRepNum(comment.getRepNum()-1);
        comment.setUpdated(new Date());
        commentMapper.update(comment);
        replyMapper.delete(id);

        return new CommonResult(200,"ok",null);

    }

    @Cacheable(value = "getComById",keyGenerator = "keyGenerator")
    public CommonResult getByComId(Long id) {

        List<Reply> replies=replyMapper.getReplyByComId(id);
        List<ReplyAndAuthor> replyAndAuthors=new ArrayList<ReplyAndAuthor>();
        for (Reply reply:replies){
            User user=userMapper.getUserById(reply.getOwnerId());
            ReplyAndAuthor replyAndAuthor=new ReplyAndAuthor();
            replyAndAuthor.setReply(reply);
            replyAndAuthor.setUser(user);
            replyAndAuthors.add(replyAndAuthor);
        }
        return new CommonResult(200,"success",replyAndAuthors);

    }
}

package app.controller;

import app.Model.CommonResult;
import app.entity.Comment;
import app.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xdcao on 2017/6/16.
 */
@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @RequestMapping(value = "/add")
    public CommonResult addNewComment(Long answerId,String content,Long ownerId,String images){
        return commentService.addNewComment(answerId,content,ownerId,images);
    }

    @RequestMapping(value = "/delete")
    public CommonResult deleteComment(Long id){
        return commentService.deleteComment(id);
    }


    @RequestMapping(value = "/getByAnswerId")
    public CommonResult getCommentsByAnswerId(Long answerId){
        return commentService.getCommentsByAnswerId(answerId);
    }


}

package app.controller;

import app.Model.CommonResult;
import app.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xdcao on 2017/6/13.
 */
@RestController
@RequestMapping(value = "/answer")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @RequestMapping(value = "/add")
    public CommonResult addNewAnswer(Long userId,Long quesId,String content,String images){
        return answerService.addNewAnswer(userId, quesId, content,images);
    }

    @RequestMapping(value = "/delete")
    public CommonResult deleteAnswer(Long id){
        return answerService.deleteAnswer(id);
    }

    @RequestMapping(value = "/showByPage")
    public CommonResult showAnswersByPage(Long user_id,Long quesId,int page,int rows){
        return answerService.getAnswerListByPage(user_id,quesId,page,rows);
    }

    @RequestMapping(value = "/star")
    public CommonResult star(Long answerId,Long userId){
        return answerService.star(answerId,userId);
    }

    @RequestMapping(value = "/unstar")
    public CommonResult unstar(Long answerId,Long userId){
        return answerService.unStar(userId,answerId);
    }


}

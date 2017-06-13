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
    public CommonResult addNewAnswer(Long userId,Long quesId,String content){
        return answerService.addNewAnswer(userId, quesId, content);
    }

    @RequestMapping(value = "/delete")
    public CommonResult deleteAnswer(Long id){
        return answerService.deleteAnswer(id);
    }

}

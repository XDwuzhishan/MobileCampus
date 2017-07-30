package app.controller;

import app.Model.CommonResult;
import app.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xdcao on 2017/6/12.
 */
@RestController
@RequestMapping(value = "/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/add")
    public CommonResult addNewQuestion(@RequestParam String title,@RequestParam String desc,@RequestParam Long ownerId,String images){
        return questionService.addNewQuestion(title,desc,ownerId,images);
    }

    @RequestMapping(value = "/delete")
    public CommonResult deleteQuestion(@RequestParam long id){
        return questionService.deleteQuestion(id);
    }

    @RequestMapping(value = "/showByPage")
    public CommonResult showQuestionsByPage(int page,int rows){
        return questionService.getQuestionListByPage(page,rows);
    }

    @RequestMapping(value = "/showMyQues")
    public CommonResult showMyQuestions(long ownerId){
        return questionService.getMyQuestions(ownerId);
    }


}

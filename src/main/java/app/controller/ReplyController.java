package app.controller;

import app.Model.CommonResult;
import app.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xdcao on 2017/6/19.
 */
@RestController
@RequestMapping(value = "/reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @RequestMapping(value = "/add")
    public CommonResult addNewReply(Long ownerId,Long answerId,String content,Long to,String images){
        return replyService.addNewReply(ownerId,answerId,content,to,images);
    }

    @RequestMapping(value = "/delete")
    public CommonResult deleteReply(Long id){
        return replyService.deleteReply(id);
    }

    @RequestMapping(value = "/getByComId")
    public CommonResult getByComId(Long id){
        return replyService.getByComId(id);
    }


}

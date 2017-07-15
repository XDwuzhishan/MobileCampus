package app.controller;

import app.Model.PictureResult;
import app.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xdcao on 2017/6/9.
 */
@RestController
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @RequestMapping(value = "/pic/upload",method = RequestMethod.POST)
    public PictureResult uploadPic(@RequestParam MultipartFile uploadFile){
        System.out.println("PicController");
        return pictureService.uploadPicture(uploadFile);
    }

    @RequestMapping(value = "/pic/batch",method = RequestMethod.POST)
    public PictureResult batchPic(HttpServletRequest request){
        return pictureService.batchPicture(request);
    }

}

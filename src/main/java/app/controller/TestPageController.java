package app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by xdcao on 2017/7/15.
 */
@Controller
public class TestPageController {

    @RequestMapping(value = "/uploadPage")
    public ModelAndView index(){
        return new ModelAndView("upload");
    }


}

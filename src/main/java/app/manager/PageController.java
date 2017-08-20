package app.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by xdcao on 2017/8/20.
 */
@Controller
public class PageController {

    @RequestMapping(value = "/manager/{page}")
    public ModelAndView page(@PathVariable String page){
        return new ModelAndView(page);
    }

}

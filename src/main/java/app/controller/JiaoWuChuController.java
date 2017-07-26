package app.controller;

import app.Model.CommonResult;
import app.service.LoginJiaoWuchuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xdcao on 2017/7/25.
 */
@RestController
@RequestMapping(value = "/student")
public class JiaoWuChuController {

    @Autowired
    private LoginJiaoWuchuService jiaoWuchuService;

    @RequestMapping(value = "/course",method = RequestMethod.POST)
    public CommonResult getCourseTable(@RequestParam String username,@RequestParam String password){
        return jiaoWuchuService.getCourseTable(username,password);
    }

    @RequestMapping(value = "/loginInfos",method = RequestMethod.POST)
    public CommonResult getAllLoginInfos(){
        return jiaoWuchuService.getAllLoginInfos();
    }



}

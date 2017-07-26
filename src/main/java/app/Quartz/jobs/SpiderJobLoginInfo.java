package app.Quartz.jobs;

import app.service.LoginJiaoWuchuService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by xdcao on 2017/7/26.
 */
public class SpiderJobLoginInfo implements Job{

    @Autowired
    private LoginJiaoWuchuService loginJiaoWuchuService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        try {
            String cookie = loginJiaoWuchuService.login("1601120078", "208037");
            loginJiaoWuchuService.crawAndSaveInfo(cookie);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

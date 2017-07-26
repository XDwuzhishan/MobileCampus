package app.Quartz.jobs;

import app.service.DynamicNewsService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.Spider;

/**
 * Created by xdcao on 2017/7/26.
 */
public class SpiderJobDynamicNews implements Job {

    @Autowired
    private DynamicNewsService dynamicNewsService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        dynamicNewsService.crawAndSaveDynamicNews();

    }


}

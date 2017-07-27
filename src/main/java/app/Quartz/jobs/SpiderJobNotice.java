package app.Quartz.jobs;

import app.service.NoticeInfomationService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xdcao on 2017/7/27.
 */
public class SpiderJobNotice implements Job {

    @Autowired
    private NoticeInfomationService noticeInfomationService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        noticeInfomationService.crawAndSaveNoticeInfomation();

    }

}

package app.Quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.annotation.Resource;

/**
 * Created by xdcao on 2017/7/25.
 */
public class ApplicationJob implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        System.out.println("运行任务。。。。。。。。。。。。。。");


    }



}

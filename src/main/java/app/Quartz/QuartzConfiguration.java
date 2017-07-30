package app.Quartz;

import app.Quartz.jobs.SpiderJobCourse;
import app.Quartz.jobs.SpiderJobDynamicNews;
import app.Quartz.jobs.SpiderJobLoginInfo;
import app.Quartz.jobs.SpiderJobNotice;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Created by xdcao on 2017/7/25.
 */
@Configuration
public class QuartzConfiguration {

// TODO: 2017/7/30 登录后的信息爬虫存在问题，只能第一次生效

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext){
        AutowiringSpringBeanJobFactory jobFactory=new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    //课程表爬虫任务
    @Bean(name = "SpiderJobCourse")
    public JobDetailFactoryBean spiderJobCourseDetail(){
        JobDetailFactoryBean factoryBean=new JobDetailFactoryBean();
        factoryBean.setJobClass(SpiderJobCourse.class);
        factoryBean.setDurability(false);
        return factoryBean;
    }

    //课程表爬虫触发器
    @Bean(name = "SpiderJobCourseTrigger")
    public CronTriggerFactoryBean spiderJobCourseTrigger(@Qualifier("SpiderJobCourse") JobDetail jobDetail){
        CronTriggerFactoryBean factoryBean=new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(0L);
        factoryBean.setCronExpression("0 0 10 * * ?");
        return factoryBean;
    }

    //登陆后的通知信息
    @Bean(name = "SpiderJobLoginInfo")
    public JobDetailFactoryBean spiderJobLoginInfoDetail(){
        JobDetailFactoryBean factoryBean=new JobDetailFactoryBean();
        factoryBean.setJobClass(SpiderJobLoginInfo.class);
        factoryBean.setDurability(false);
        return factoryBean;
    }

    //登录后通知信息触发器
    @Bean(name = "SpiderJobLoginInfoTrigger")
    public CronTriggerFactoryBean spiderJobLoginInfoTrigger(@Qualifier("SpiderJobLoginInfo") JobDetail jobDetail){
        CronTriggerFactoryBean factoryBean=new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(0L);
        factoryBean.setCronExpression("0 01 10 * * ?");
        return factoryBean;
    }

    //最新动态任务
    @Bean(name = "SpiderJobDynamicNews")
    public JobDetailFactoryBean spiderJobDynamicNewsDetail(){
        JobDetailFactoryBean factoryBean=new JobDetailFactoryBean();
        factoryBean.setJobClass(SpiderJobDynamicNews.class);
        factoryBean.setDurability(false);
        return factoryBean;
    }

    //最新动态触发器
    @Bean(name = "SpiderJobDynamicNewsTrigger")
    public CronTriggerFactoryBean spiderJobDynamicNewsTrigger(@Qualifier("SpiderJobDynamicNews") JobDetail jobDetail){
        CronTriggerFactoryBean factoryBean=new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(0L);
        factoryBean.setCronExpression("0 02 10 * * ?");
        return factoryBean;
    }

    //首页通知任务
    @Bean(name = "SpiderJobNotice")
    public JobDetailFactoryBean spiderJobNoticeDetail(){
        JobDetailFactoryBean factoryBean=new JobDetailFactoryBean();
        factoryBean.setJobClass(SpiderJobNotice.class);
        factoryBean.setDurability(false);
        return factoryBean;
    }

    //首页通知触发器
    @Bean(name = "SpiderJobNoticeTrigger")
    public CronTriggerFactoryBean spiderJobNoticeTrigger(@Qualifier("SpiderJobNotice") JobDetail jobDetail){
        CronTriggerFactoryBean factoryBean=new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(0L);
        factoryBean.setCronExpression("0 03 10 * * ?");
        return factoryBean;
    }


    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory,
                                                     @Qualifier("SpiderJobCourseTrigger")CronTrigger courseCronTrigger,
                                                     @Qualifier("SpiderJobLoginInfoTrigger") CronTrigger loginInfoCronTrigger,
                                                     @Qualifier("SpiderJobDynamicNewsTrigger") CronTrigger dynamicNewsTrigger,
                                                     @Qualifier("SpiderJobNoticeTrigger") CronTrigger noticeTrigger){
        SchedulerFactoryBean factoryBean=new SchedulerFactoryBean();
        factoryBean.setOverwriteExistingJobs(true);
        factoryBean.setJobFactory(jobFactory);
        factoryBean.setStartupDelay(0);
        factoryBean.setTriggers(loginInfoCronTrigger,dynamicNewsTrigger,noticeTrigger);
        return factoryBean;
    }




}

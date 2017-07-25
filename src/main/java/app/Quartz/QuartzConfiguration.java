package app.Quartz;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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


    // TODO: 2017/7/25 将不同信息分开触发

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext){
        AutowiringSpringBeanJobFactory jobFactory=new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }


    @Bean(name = "SpiderJob")
    public JobDetailFactoryBean spiderJobDetail(){
        JobDetailFactoryBean factoryBean=new JobDetailFactoryBean();
        factoryBean.setJobClass(SpiderJob.class);
        factoryBean.setDurability(false);
        return factoryBean;
    }

    @Bean(name = "SpiderJobTrigger")
    public CronTriggerFactoryBean spiderJobTrigger(@Qualifier("SpiderJob") JobDetail jobDetail, @Value("${sampleJob.frequency}") long frequency){
        CronTriggerFactoryBean factoryBean=new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(0L);
        factoryBean.setCronExpression("0 47 22 * * ?");
        return factoryBean;
    }



    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, @Qualifier("SpiderJobTrigger")CronTrigger cronTrigger){
        SchedulerFactoryBean factoryBean=new SchedulerFactoryBean();
        factoryBean.setOverwriteExistingJobs(true);
        factoryBean.setJobFactory(jobFactory);
        factoryBean.setStartupDelay(0);
        factoryBean.setTriggers(cronTrigger);
        return factoryBean;
    }




}

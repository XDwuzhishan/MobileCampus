package app.Quartz;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * Created by xdcao on 2017/7/25.
 */
@Configuration
public class QuartzConfiguration {

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext){
        AutowiringSpringBeanJobFactory jobFactory=new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, @Qualifier("cronJobTrigger")CronTrigger cronTrigger){
        SchedulerFactoryBean factoryBean=new SchedulerFactoryBean();
        factoryBean.setOverwriteExistingJobs(true);
        factoryBean.setJobFactory(jobFactory);
        factoryBean.setStartupDelay(10);
        factoryBean.setTriggers(cronTrigger);
        return factoryBean;
    }


    @Bean
    public JobDetailFactoryBean sampleJobDetail(){
        return createJobDetail(ApplicationJob.class);
    }

    @Bean(name = "cronJobTrigger")
    public CronTriggerFactoryBean sampleJobTrigger(@Qualifier("sampleJobDetail") JobDetail jobDetail, @Value("${sampleJob.frequency}") long frequency){
        return createTrigger(jobDetail,frequency);
    }

    private CronTriggerFactoryBean createTrigger(JobDetail jobDetail, long frequency) {
        CronTriggerFactoryBean factoryBean=new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(0L);
        factoryBean.setCronExpression("0/5 * * * * ?");
        return factoryBean;
    }

    private JobDetailFactoryBean createJobDetail(Class applicationJobClass) {

        JobDetailFactoryBean factoryBean=new JobDetailFactoryBean();
        factoryBean.setJobClass(applicationJobClass);
        factoryBean.setDurability(false);
        return factoryBean;

    }


}

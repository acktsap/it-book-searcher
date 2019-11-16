/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.books.config;

import static org.slf4j.LoggerFactory.getLogger;

import acktsap.books.nl.NationalLibraryClient;
import acktsap.books.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Configuration
@RequiredArgsConstructor
public class QuartzConfig {

  protected final Logger logger = getLogger(getClass());

  protected final ApplicationContext applicationContext;

  protected final JobLauncher jobLauncher;

  protected final JobLocator jobLocator;

  // TODO: remove after batch job is implemented
  protected final NationalLibraryClient nationalLibraryClient;
  protected final BookRepository bookRepository;

  @Bean
  public SpringBeanJobFactory springBeanJobFactory() {
    AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
    logger.debug("Configuring Job factory");
    jobFactory.setApplicationContext(this.applicationContext);
    return jobFactory;
  }

  @Bean
  public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(final JobRegistry jobRegistry) {
    JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
    jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
    return jobRegistryBeanPostProcessor;
  }

  @Bean
  public JobDetail indexingJobDetail() {
    final JobDataMap jobDataMap = new JobDataMap();
    // TODO: uncomment after batch job is implemented
    // jobDataMap.put("jobName", "indexingJob");
    jobDataMap.put("jobLauncher", this.jobLauncher);
    jobDataMap.put("jobLocator", this.jobLocator);

    // TODO: remove after batch job is implemented
    jobDataMap.put("nationalLibraryClient", this.nationalLibraryClient);
    jobDataMap.put("bookRepository", this.bookRepository);

    return JobBuilder.newJob(BatchQuartzJobBridge.class)
        .withIdentity("indexingJob")
        .setJobData(jobDataMap)
        .storeDurably()
        .build();
  }

  @Bean
  public Trigger itemJobTrigger() {
    final SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
        .withIntervalInSeconds(300)
        .repeatForever();
    return TriggerBuilder.newTrigger()
        .forJob(indexingJobDetail())
        .withIdentity("itemJobDetailTrigger")
        .withSchedule(scheduleBuilder)
        .build();
  }

  @Bean
  public SchedulerFactoryBean schedulerFactoryBean() {
    SchedulerFactoryBean factory = new SchedulerFactoryBean();
    factory.setJobFactory(springBeanJobFactory());
    factory.setTriggers(itemJobTrigger());
    factory.setJobDetails(indexingJobDetail());
    return factory;
  }

}

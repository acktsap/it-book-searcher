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

  /**
   * SpringBeanJobFactory bean. Used for providing bean to quartz.
   *
   * @return a SpringBeanJobFactory bean
   */
  @Bean
  public SpringBeanJobFactory springBeanJobFactory() {
    final AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();
    logger.debug("Configuring Job factory");
    jobFactory.setApplicationContext(this.applicationContext);
    return jobFactory;
  }

  /**
   * IndexingJobDetail bean.
   *
   * @return an IndexingJobDetail bean
   */
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

  /**
   * IndexJobTrigger bean which uses indexingJobDetail.
   *
   * @return a IndexJobTrigger bean
   */
  @Bean
  public Trigger indexJobTrigger() {
    final SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
        .withIntervalInSeconds(300)
        .repeatForever();
    return TriggerBuilder.newTrigger()
        .forJob(indexingJobDetail())
        .withIdentity("indexJobTrigger")
        .withSchedule(scheduleBuilder)
        .build();
  }

  /**
   * SchedulerFactoryBean instance providing quartz bean to the spring.
   *
   * @return a SchedulerFactoryBean instance
   */
  @Bean
  public SchedulerFactoryBean schedulerFactoryBean() {
    final SchedulerFactoryBean factory = new SchedulerFactoryBean();
    factory.setJobFactory(springBeanJobFactory());
    factory.setTriggers(indexJobTrigger());
    factory.setJobDetails(indexingJobDetail());
    return factory;
  }

}

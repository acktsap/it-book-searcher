/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.books.config;

import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;

import acktsap.books.EsBook;
import acktsap.books.converter.BookConverter;
import acktsap.books.converter.ModelConverter;
import acktsap.books.model.RawBook;
import acktsap.books.nl.NationalLibraryClient;
import acktsap.books.repository.BookRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;
import lombok.Setter;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class BatchQuartzJobBridge extends QuartzJobBean {

  protected final Logger logger = getLogger(getClass());

  // TODO: remove after batch job is implemented
  @Setter
  protected NationalLibraryClient nationalLibraryClient;
  @Setter
  protected BookRepository bookRepository;
  protected final ModelConverter<RawBook, EsBook> bookConverter = new BookConverter();

  @Setter
  protected JobLauncher jobLauncher;

  @Setter
  protected JobLocator jobLocator;

  @Setter
  protected String jobName;

  @Setter
  protected JobParameters jobParameters = new JobParametersBuilder().toJobParameters();

  @Override
  protected void executeInternal(final JobExecutionContext context) throws JobExecutionException {
    try {
      final long startTime = System.currentTimeMillis();

      final LocalDate now = LocalDate.now();
      final LocalDate before = now.minusMonths(3);
      final int itemCount = nationalLibraryClient.getBookCount(before, now);
      logger.info("Item count: {}", itemCount);

      final int pageSize = 1000;
      final int maxPageNum = itemCount / pageSize + 1;
      IntStream.range(1, maxPageNum + 1).forEach(i -> {
        logger.info("Processing page {} of {}", i, maxPageNum);
        final List<RawBook> rawBooks =
            this.nationalLibraryClient.listBooksByStartDateAndEndDate(i, pageSize, before, now);
        final List<EsBook> esBooks = rawBooks.stream()
            .map(this.bookConverter::convertToEsModel)
            .filter(b -> !b.getIsbn().isEmpty())
            .collect(toList());
        bookRepository.save(esBooks);
      });

      final long endTime = System.currentTimeMillis();
      logger.info("Quartz job complete (processed books: {}, time spent: {}ms)", itemCount,
          endTime - startTime);

      // TODO: run as batch job
      // final Job job = this.jobLocator.getJob(jobName);
      // logger.debug("Job name: {}", jobName);
      // final JobExecution execution = this.jobLauncher.run(job, jobParameters);
      // logger.debug("Job status: {}", execution.getStatus());
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

}

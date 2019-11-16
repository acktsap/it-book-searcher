/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.books.config;

import static org.slf4j.LoggerFactory.getLogger;

import acktsap.books.nl.NationalLibraryClient;
import acktsap.books.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

  protected final Logger logger = getLogger(getClass());

  protected final JobBuilderFactory jobBuilderFactory;

  protected final StepBuilderFactory stepBuilderFactory;

  protected final NationalLibraryClient nationalLibraryClient;

  protected final BookRepository bookRepository;

  // TODO: run as batch job
  // @Bean
  // public Job indexingJob() {
  // return this.jobBuilderFactory.get("indexingJob")
  // .start(processItem())
  // .build();
  // }
  //
  // @Bean
  // public Step processItem() {
  // return this.stepBuilderFactory.get("processItem")
  // .<List<RawBook>, List<EsBook>>chunk(5)
  // .build();
  // }

}

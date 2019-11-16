/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.books.config;

import acktsap.books.EsBook;
import acktsap.books.converter.BookConverter;
import acktsap.books.converter.ModelConverter;
import acktsap.books.model.RawBook;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConverterConfig {

  @Bean
  public ModelConverter<RawBook, EsBook> bookConverter() {
    return new BookConverter();
  }

}

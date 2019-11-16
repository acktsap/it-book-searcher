/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.books.config;

import acktsap.books.nl.NationalLibraryClient;
import acktsap.books.nl.NationalLibraryClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NationalLibraryConfig {

  @Value("${nl.endpoint}")
  protected String endpoint;

  @Value("${nl.certkey}")
  protected String certkey;

  /**
   * NationalLibraryClient bean.
   *
   * @return an NationalLibraryClient bean
   */
  @Bean
  public NationalLibraryClient nationalLibraryClient() {
    return new NationalLibraryClientFactory().create(endpoint, certkey);
  }

}

/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.books.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EsConfig {

  @Value("${elasticsearch.host}")
  protected String esHost;

  @Value("${elasticsearch.port}")
  protected int esPort;

  @Value("${elasticsearch.scheme}")
  protected String scheme;

  /**
   * RestHighLevelClient bean.
   *
   * @return an RestHighLevelClient bean
   */
  @Bean
  public RestHighLevelClient restHighLevelClient() {
    final RestClientBuilder restClientBuilder =
        RestClient.builder(new HttpHost(esHost, esPort, scheme));
    return new RestHighLevelClient(restClientBuilder);
  }

}

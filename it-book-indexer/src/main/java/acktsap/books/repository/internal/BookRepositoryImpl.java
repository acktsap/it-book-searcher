/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.books.repository.internal;

import static org.slf4j.LoggerFactory.getLogger;

import acktsap.books.EsBook;
import acktsap.books.repository.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

  protected final Logger logger = getLogger(getClass());

  protected final ObjectWriter objectWriter = new ObjectMapper().writer();

  @Value("${elasticsearch.index}")
  protected String index;

  protected final RestHighLevelClient highLevelClient;

  @Override
  public Collection<EsBook> save(final Collection<EsBook> esBooks) {
    try {
      logger.debug("Bulk request with es books: {}", esBooks);
      final BulkRequest bulkRequest = new BulkRequest();
      for (final EsBook esBook : esBooks) {
        bulkRequest.add(toIndexRequest(esBook));
      }
      final BulkResponse bulkResponse =
          this.highLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
      logger.debug("Bulk response status: {}", bulkResponse.status());
      return esBooks;
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  protected IndexRequest toIndexRequest(final EsBook esBook) throws JsonProcessingException {
    final String esBookInJson = objectWriter.writeValueAsString(esBook);
    return new IndexRequest(index)
        .id(esBook.getIsbn())
        .source(esBookInJson, XContentType.JSON);
  }

}

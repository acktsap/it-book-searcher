/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.books.repository.internal;

import acktsap.books.EsBook;
import acktsap.books.repository.BookRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {

  protected final ObjectWriter objectWriter = new ObjectMapper().writer();

  @Value("${elasticsearch.index}")
  protected String index;

  protected final RestHighLevelClient highLevelClient;

  @Override
  public Collection<EsBook> save(final Collection<EsBook> esBooks) {
    try {
      final BulkRequest bulkRequest = new BulkRequest();
      for (final EsBook esBook : esBooks) {
        bulkRequest.add(toIndexRequest(esBook));
      }
      this.highLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
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

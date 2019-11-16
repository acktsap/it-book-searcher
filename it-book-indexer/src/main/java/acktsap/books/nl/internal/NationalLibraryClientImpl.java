/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.books.nl.internal;

import static org.slf4j.LoggerFactory.getLogger;

import acktsap.books.model.PageInfo;
import acktsap.books.model.RawBookInformation;
import acktsap.books.nl.NationalLibraryClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * See also https://www.nl.go.kr/nl/service/open/api_util_isbn.jsp.
 *
 */
@RequiredArgsConstructor
public class NationalLibraryClientImpl implements NationalLibraryClient {

  protected static final int MAX_PAGE_SIZE = 1000;

  protected final Logger logger = getLogger(getClass());

  protected final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

  protected final RestTemplate restTemplate = new RestTemplate();

  protected final ObjectMapper objectMapper = new ObjectMapper();

  protected final String endpoint;

  protected final String certkey;

  protected final String resultStyle = "json";

  @Override
  public int fetchItemCount(final LocalDate startDate,
      final LocalDate endDate) {
    try {
      assert null != startDate;
      assert null != endDate;
      assert startDate.isBefore(endDate);

      final PageInfo pageInfo = request(1, 1, startDate, endDate);
      return pageInfo.getTotalCount();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public List<RawBookInformation> fetchByStartDateAndEndDate(final int pageNum, final int pageSize,
      final LocalDate startDate, final LocalDate endDate) {
    try {
      assert 0 < pageNum;
      assert 0 < pageSize && pageSize <= MAX_PAGE_SIZE;
      assert null != startDate;
      assert null != endDate;
      assert startDate.isBefore(endDate);

      final PageInfo pageInfo = request(pageNum, pageSize, startDate, endDate);
      return pageInfo.getRawBookInformations();
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }

  private PageInfo request(final int pageNum, final int pageSize, final LocalDate startDate,
      final LocalDate endDate) throws JsonProcessingException, JsonMappingException {
    final URI requestUri = createRequestUri(pageNum, pageSize, startDate, endDate);
    logger.debug("Request params: {}", requestUri);

    final ResponseEntity<String> response =
        this.restTemplate.getForEntity(requestUri, String.class);
    logger.debug("Response: {}", response);

    final PageInfo pageInfo = objectMapper.readValue(response.getBody(), PageInfo.class);
    logger.debug("Page info: {}", pageInfo);
    return pageInfo;
  }

  protected URI createRequestUri(final int pageNum, final int pageSize, final LocalDate startDate,
      final LocalDate endDate) {
    final UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(this.endpoint)
        .queryParam("cert_key", certkey)
        .queryParam("result_style", resultStyle)
        .queryParam("page_no", Integer.toString(pageNum))
        .queryParam("page_size", Integer.toString(pageSize))
        .queryParam("start_publish_date", startDate.format(timeFormatter))
        .queryParam("end_publish_date", endDate.format(timeFormatter))
        .build();
    return uriComponents.encode().toUri();
  }

}

/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.books.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class PageInfo {

  @JsonProperty("PAGE_NO")
  protected int pageNumber;

  @JsonProperty("TOTAL_COUNT")
  protected int totalCount;

  @JsonProperty("docs")
  protected List<RawBookInformation> rawBookInformations;

}

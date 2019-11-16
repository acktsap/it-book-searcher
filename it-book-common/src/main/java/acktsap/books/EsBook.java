/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.books;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder(builderMethodName = "newBuilder")
public class EsBook {

  @NonNull
  protected String title;

  @NonNull
  protected String author;

  @NonNull
  protected String publisher;

  @NonNull
  protected LocalDate publishDate;

  @NonNull
  protected String isbn;

}

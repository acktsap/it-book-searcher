/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.books.converter;

import static org.slf4j.LoggerFactory.getLogger;

import acktsap.books.EsBook;
import acktsap.books.model.RawBook;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;

public class BookConverter implements ModelConverter<RawBook, EsBook> {

  protected final Logger logger = getLogger(getClass());

  protected final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

  @Override
  public RawBook convertToOrigin(final EsBook elasticSearchModel) {
    throw new UnsupportedOperationException();
  }

  @Override
  public EsBook convertToEsModel(final RawBook rawBook) {
    try {
      logger.trace("Raw book to convert: {}", rawBook);
      // if isbn is empty, use set isbn
      final String isbn =
          rawBook.getIsbn().trim().isEmpty() ? rawBook.getSetIsbn() : rawBook.getIsbn();
      final EsBook converted = EsBook.newBuilder()
          .title(rawBook.getTitle())
          .author(rawBook.getAuthor())
          .publisher(rawBook.getPublisher())
          .publishDate(LocalDate.parse(rawBook.getPublishPredate(), dateTimeFormatter))
          .isbn(isbn)
          .build();
      logger.trace("Convertd book: {}", converted);
      return converted;
    } catch (Exception e) {
      throw new IllegalArgumentException(e);
    }
  }

}

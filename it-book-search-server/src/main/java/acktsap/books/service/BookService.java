package acktsap.books.service;

import acktsap.books.model.Book;
import java.util.Date;
import java.util.List;

public interface BookService {

  default List<Book> findByTitle(String title) {
    return findByTitleAndAuthorAndDate(title, null, null, null);
  }

  default List<Book> findByAuthor(List<String> authors) {
    return findByTitleAndAuthorAndDate(null, authors, null, null);
  }

  default List<Book> findByDate(final Date startDate, final Date endDate) {
    return findByTitleAndAuthorAndDate(null, null, startDate, endDate);
  }

  List<Book> findByTitleAndAuthorAndDate(String title, List<String> author, Date startDate,
      Date endDate);

}

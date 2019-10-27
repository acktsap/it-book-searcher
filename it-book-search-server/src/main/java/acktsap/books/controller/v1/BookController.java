package acktsap.books.controller.v1;

import static org.slf4j.LoggerFactory.getLogger;

import acktsap.books.model.Book;
import acktsap.books.service.BookService;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {

  protected final Logger logger = getLogger(getClass());

  protected final BookService bookService;

  /**
   * Get all items.
   *
   * @return item list
   */
  @GetMapping
  public List<Book> filterBooks(
      @RequestParam(value = "title", required = false) final String title,
      @RequestParam(value = "author", required = false) final List<String> author,
      @RequestParam(value = "startDate", required = false) final Date startDate,
      @RequestParam(value = "endDate", required = false) final Date endDate) {
    logger.debug("GET /books title: {}, author: {}, startDate: {}, endDate: {}", title,
        author, startDate, endDate);
    return this.bookService.findByTitleAndAuthorAndDate(title, author, startDate, endDate);
  }

}

package acktsap.books.service.internal;

import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;

import acktsap.books.model.Book;
import acktsap.books.service.BookService;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

  @Override
  public List<Book> findByTitleAndAuthorAndDate(final String title, final List<String> authors,
      final Date startDate, final Date endDate) {
    // TODO: replace dummy datas
    return IntStream.range(0, new Random().nextInt(100))
        .mapToObj(Integer::new)
        .map(i -> Book.newBuilder()
            .author(randomUUID().toString())
            .title(randomUUID().toString())
            .publish(new Date())
            .build())
        .collect(toList());
  }

}

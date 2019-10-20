package acktsap.books.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import acktsap.books.service.BookService;

@RestController
@RequiredArgsConstructor
public class BookController {

  protected final BookService bookService;

  @GetMapping("books")
  public String search() {
    return this.bookService.search();
  }

}

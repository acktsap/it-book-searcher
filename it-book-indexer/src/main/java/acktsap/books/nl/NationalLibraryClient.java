/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.books.nl;

import acktsap.books.model.RawBook;
import java.time.LocalDate;
import java.util.List;

public interface NationalLibraryClient {

  int getBookCount(LocalDate startDate, LocalDate endDate);

  default List<RawBook> listBooks(final int pageNum, final int pageSize) {
    return listBooksByStartDateAndEndDate(pageNum, pageSize, LocalDate.ofEpochDay(0),
        LocalDate.now());
  }

  default List<RawBook> listBooksByStartDate(final int pageNum, final int pageSize,
      final LocalDate startDate) {
    return listBooksByStartDateAndEndDate(pageNum, pageSize, startDate, LocalDate.now());
  }

  default List<RawBook> listBooksByEndDate(final int pageNum, final int pageSize,
      final LocalDate endDate) {
    return listBooksByStartDateAndEndDate(pageNum, pageSize, LocalDate.ofEpochDay(0), endDate);
  }

  List<RawBook> listBooksByStartDateAndEndDate(int pageNum, int pageSize,
      LocalDate startDate, LocalDate endDate);

}

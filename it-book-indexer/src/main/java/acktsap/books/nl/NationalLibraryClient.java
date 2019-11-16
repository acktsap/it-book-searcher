/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.books.nl;

import acktsap.books.model.RawBook;
import java.time.LocalDate;
import java.util.List;

public interface NationalLibraryClient {

  int getBookCount(LocalDate startDate, LocalDate endDate);

  default List<RawBook> listBook(final int pageNum, final int pageSize) {
    return listBookByStartDateAndEndDate(pageNum, pageSize, LocalDate.ofEpochDay(0),
        LocalDate.now());
  }

  default List<RawBook> listBookByStartDate(final int pageNum, final int pageSize,
      final LocalDate startDate) {
    return listBookByStartDateAndEndDate(pageNum, pageSize, startDate, LocalDate.now());
  }

  default List<RawBook> getBookByEndDate(final int pageNum, final int pageSize,
      final LocalDate endDate) {
    return listBookByStartDateAndEndDate(pageNum, pageSize, LocalDate.ofEpochDay(0), endDate);
  }

  List<RawBook> listBookByStartDateAndEndDate(int pageNum, int pageSize,
      LocalDate startDate, LocalDate endDate);

}

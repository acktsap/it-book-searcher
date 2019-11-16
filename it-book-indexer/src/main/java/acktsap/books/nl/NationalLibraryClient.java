/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.books.nl;

import acktsap.books.model.RawBook;
import java.time.LocalDate;
import java.util.List;

public interface NationalLibraryClient {

  int fetchItemCount(LocalDate startDate, LocalDate endDate);

  default List<RawBook> fetch(final int pageNum, final int pageSize) {
    return fetchByStartDateAndEndDate(pageNum, pageSize, LocalDate.ofEpochDay(0), LocalDate.now());
  }

  default List<RawBook> fetchByStartDate(final int pageNum, final int pageSize,
      final LocalDate startDate) {
    return fetchByStartDateAndEndDate(pageNum, pageSize, startDate, LocalDate.now());
  }

  default List<RawBook> fetchByEndDate(final int pageNum, final int pageSize,
      final LocalDate endDate) {
    return fetchByStartDateAndEndDate(pageNum, pageSize, LocalDate.ofEpochDay(0), endDate);
  }

  List<RawBook> fetchByStartDateAndEndDate(int pageNum, int pageSize,
      LocalDate startDate, LocalDate endDate);

}

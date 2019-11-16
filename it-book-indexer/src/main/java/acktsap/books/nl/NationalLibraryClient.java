/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.books.nl;

import acktsap.books.model.RawBookInformation;
import java.time.LocalDate;
import java.util.List;

public interface NationalLibraryClient {

  int fetchItemCount(LocalDate startDate, LocalDate endDate);

  default List<RawBookInformation> fetch(final int pageNum, final int pageSize) {
    return fetchByStartDateAndEndDate(pageNum, pageSize, LocalDate.ofEpochDay(0), LocalDate.now());
  }

  default List<RawBookInformation> fetchByStartDate(final int pageNum, final int pageSize,
      final LocalDate startDate) {
    return fetchByStartDateAndEndDate(pageNum, pageSize, startDate, LocalDate.now());
  }

  default List<RawBookInformation> fetchByEndDate(final int pageNum, final int pageSize,
      final LocalDate endDate) {
    return fetchByStartDateAndEndDate(pageNum, pageSize, LocalDate.ofEpochDay(0), endDate);
  }

  List<RawBookInformation> fetchByStartDateAndEndDate(int pageNum, int pageSize,
      LocalDate startDate, LocalDate endDate);

}

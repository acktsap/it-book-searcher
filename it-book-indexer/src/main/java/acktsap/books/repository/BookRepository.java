/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.books.repository;

import acktsap.books.EsBook;
import java.util.Collection;

public interface BookRepository {

  Collection<EsBook> save(Collection<EsBook> esbook);

}

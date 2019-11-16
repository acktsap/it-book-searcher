/*
 * @copyright defined in LICENSE.txt
 */

package acktsap.books.nl;

import acktsap.books.nl.internal.NationalLibraryClientImpl;

public class NationalLibraryClientFactory {

  public NationalLibraryClient create(final String endpoint, final String certkey) {
    return new NationalLibraryClientImpl(endpoint, certkey);
  }

}

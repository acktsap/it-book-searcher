package acktsap.books;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import acktsap.books.Common;

public class CommonTest {

  @Test
  public void test() {
    final Common common = new Common(randomUUID().toString());
    assertNotNull(common);
  }

}

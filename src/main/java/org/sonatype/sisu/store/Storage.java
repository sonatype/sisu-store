package org.sonatype.sisu.store;

import java.io.InputStream;
import java.io.OutputStream;

public interface Storage {

  OutputStream getOutputStream();

  InputStream getInputStream();

}

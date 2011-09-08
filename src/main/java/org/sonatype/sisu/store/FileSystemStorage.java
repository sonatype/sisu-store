package org.sonatype.sisu.store;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class FileSystemStorage implements Storage {

  private final String path;

  public FileSystemStorage(final String path) {
    this.path = path == null || path.trim().length() == 0 ? null : path;
  }

  public OutputStream getOutputStream() {
    if (path == null) {
      return null;
    }
    try {
      final URL resource = this.getClass().getClassLoader().getResource(path);
      if (resource != null) {
        return null;
      }

      final File storage = new File(path);
      if (!storage.getParentFile().exists()) {
        storage.getParentFile().mkdirs();
      }

      return new BufferedOutputStream(new FileOutputStream(storage));
    } catch (final FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public InputStream getInputStream() {
    if (path == null) {
      return null;
    }
    try {
      final InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(path);
      if (resourceAsStream != null) {
        return resourceAsStream;
      }

      final File file = new File(path);
      if (file.exists()) {
        return new BufferedInputStream(new FileInputStream(file));
      }
    } catch (final FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    return null;
  }
}

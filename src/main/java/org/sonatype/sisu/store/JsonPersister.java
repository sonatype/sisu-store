package org.sonatype.sisu.store;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

public class JsonPersister<T> implements Persister<T> {

  private final Store<T> store;

  private final Class<T> itemsClass;

  private final Storage storage;

  private boolean saveListenerAdded;

  public JsonPersister(final Store<T> store, final Class<T> itemsClass, final Storage storage) {
    this.store = store;
    this.itemsClass = itemsClass;
    this.storage = storage;
  }

  public Persister<T> save() {
    final ObjectMapper mapper = new ObjectMapper();
    OutputStream out = null;
    try {
      out = storage.getOutputStream();
      if (out != null) {
        final Collection<T> items = store.get();
        mapper.writeValue(out, items);
      }
    } catch (final Exception e) {
      throw new RuntimeException(e);
    } finally {
      close(out);
    }
    return this;
  }

  @SuppressWarnings("unchecked")
  public Persister<T> load() {
    final ObjectMapper mapper = new ObjectMapper();
    InputStream in = null;
    try {
      in = storage.getInputStream();
      if (in != null) {
        final Collection<T> readItems = mapper.readValue(in, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, itemsClass));
        if (readItems != null && readItems != null) {
          for (final T item : readItems) {
            store.add(item);
          }
        }
      }
    } catch (final Exception e) {
      throw new RuntimeException(e);
    } finally {
      close(in);
    }
    return this;
  }

  public Persister<T> autoSave() {
    if (!saveListenerAdded) {
      store.addListener(new SaveListener(), false);
      save();
      saveListenerAdded = true;
    }
    return this;
  }

  private static void close(final Closeable stream) {
    if (stream != null) {
      try {
        stream.close();
      } catch (final IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private class SaveListener implements Store.Listener<T> {

    public void added(final Store<T> store, final T item) {
      save();
    }

    public void removed(final Store<T> store, final T item) {
      save();
    }

    public void updated(final Store<T> store, final T oldItem, final T newitem) {
      save();
    }

  }

}

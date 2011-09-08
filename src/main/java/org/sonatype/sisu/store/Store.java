package org.sonatype.sisu.store;

import java.util.Collection;

public interface Store<T> extends Iterable<T> {

  Store<T> add(T... item);
  boolean remove(T... item);
  Collection<T> get();
  T get(Object key);
  Store<T> addListener(Listener<T> listener, boolean notifyExisting);
  Store<T> removeListener(Listener<T> listener, boolean notifyExisting);

  public interface Listener<T> {
    void added(Store<T> store, T item);
    void removed(Store<T> store, T item);
    void updated(Store<T> store, T oldItem, T newitem);
  }
}

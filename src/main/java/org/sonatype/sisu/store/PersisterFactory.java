package org.sonatype.sisu.store;

public interface PersisterFactory {
  <T> Persister<T> createFor(final Store<T> store, Class<T> itemsClass, Storage storage);
}

package org.sonatype.sisu.store;

public class JsonPersisterFactory implements PersisterFactory {
  public <T> Persister<T> createFor(final Store<T> store, final Class<T> itemsClass, final Storage storage) {
    return new JsonPersister<T>(store, itemsClass, storage);
  }

}

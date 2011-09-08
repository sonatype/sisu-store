package org.sonatype.sisu.store;

public interface Persister<T> {

  Persister<T> save();

  Persister<T> load();

  Persister<T> autoSave();

}

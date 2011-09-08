package org.sonatype.sisu.store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class StoreNotifier<T> implements Store<T> {

  private final Collection<Listener<T>> listeners;

  private final ReentrantReadWriteLock listenersLock;

  public StoreNotifier() {
    listeners = new ArrayList<Listener<T>>();
    listenersLock = new ReentrantReadWriteLock();
  }

  public Store<T> addListener(Listener<T> listener, boolean notifyExisting) {
    try {
      listenersLock.writeLock().lock();

      listeners.add(listener);
      if (notifyExisting) {
        Collection<T> items = snapshot();
        for (T item : items) {
          listener.added(this, item);
        }
      }
      return this;
    } finally {
      listenersLock.writeLock().unlock();
    }
  }

  public Store<T> removeListener(Listener<T> listener, boolean notifyExisting) {
    try {
      listenersLock.writeLock().lock();

      if (listeners.remove(listener) && notifyExisting) {
        Collection<T> items = snapshot();
        for (T item : items) {
          listener.removed(this, item);
        }
      }
      return this;
    } finally {
      listenersLock.writeLock().unlock();
    }
  }

  protected void notifyAdded(T item) {
    try {
      listenersLock.readLock().lock();

      RuntimeException error = null;

      for (Listener<T> listener : listeners) {
        try {
          listener.added(this, item);
        } catch (RuntimeException e) {
          if (error == null) {
            error = e;
          }
        }
      }

      if (error != null) {
        throw error;
      }
    } finally {
      listenersLock.readLock().unlock();
    }
  }

  protected void notifyRemoved(T item) {
    try {
      listenersLock.readLock().lock();

      RuntimeException error = null;

      for (Listener<T> listener : listeners) {
        try {
          listener.removed(this, item);
        } catch (RuntimeException e) {
          if (error == null) {
            error = e;
          }
        }
      }

      if (error != null) {
        throw error;
      }
    } finally {
      listenersLock.readLock().unlock();
    }
  }

  protected void notifyUpdated(T oldItem, T newitem) {
    try {
      listenersLock.readLock().lock();

      RuntimeException error = null;

      for (Listener<T> listener : listeners) {
        try {
          listener.updated(this, oldItem, newitem);
        } catch (RuntimeException e) {
          if (error == null) {
            error = e;
          }
        }
      }

      if (error != null) {
        throw error;
      }
    } finally {
      listenersLock.readLock().unlock();
    }
  }

  protected abstract Collection<T> snapshot();

}

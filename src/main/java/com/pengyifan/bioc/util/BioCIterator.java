package com.pengyifan.bioc.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public interface BioCIterator<T> extends Iterator<T> {
  /**
   * Returns the current element in the iteration. This method can be called only after
   * {@link #hasNext()} is called.
   *
   * @return the current element in the iteration
   * @throws IllegalStateException if the {@link #hasNext()} has not yet been called
   */
  T current() throws IllegalStateException ;
}

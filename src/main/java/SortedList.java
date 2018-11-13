public interface SortedList<T extends Comparable<T>> extends Iterable<T> {
    void insert(T element);
    boolean removeFirst(T element);  // returns if element was removed or not
    void removeAll(T element);
    void clear();
}


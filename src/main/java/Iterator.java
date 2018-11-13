public interface Iterator<T> {
    boolean valid();
    T current();
    T next();
}


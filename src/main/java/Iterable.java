import java.lang.reflect.InvocationTargetException;

public interface Iterable<T> {
    Iterator<T> iterator();
    Iterator<T> snapshotIterator();
}


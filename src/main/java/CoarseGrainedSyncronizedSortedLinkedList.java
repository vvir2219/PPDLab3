import java.lang.reflect.InvocationTargetException;

public class CoarseGrainedSyncronizedSortedLinkedList extends SortedLinkedList{

    @Override
    public synchronized void insert(Comparable element) {
        super.insert(element);
    }

    @Override
    public synchronized boolean removeFirst(Comparable element) {
        return super.removeFirst(element);
    }

    @Override
    public void removeAll(Comparable element) {
        super.removeAll(element);
    }

    @Override
    public synchronized void clear() {
        super.clear();
    }

    @Override
    public Iterator iterator() {
        return super.iterator();
    }

    @Override
    public Iterator snapshotIterator() {
        return super.snapshotIterator();
    }
}

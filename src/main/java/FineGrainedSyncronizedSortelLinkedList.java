import com.rits.cloning.Cloner;

import java.util.List;

public class FineGrainedSyncronizedSortelLinkedList<T extends Comparable<T>> implements SortedList<T> {
    private Node<T> root = null;
    private final Cloner cloner = new Cloner();

    public FineGrainedSyncronizedSortelLinkedList() {
    }

    public FineGrainedSyncronizedSortelLinkedList(List<T> list) {
        for (T el : list) {
            this.insert(cloner.deepClone(el));
        }
    }

    public FineGrainedSyncronizedSortelLinkedList(SortedList<T> sortedList) {
        Iterator<T> iterator = sortedList.iterator();
        for (; iterator.valid(); iterator.next()) {
            insert(cloner.deepClone(iterator.current()));
        }
    }

    @Override
    public void insert(T element) {
        Node<T> current;
        Node<T> next;

        synchronized (this) {
            if (root == null) {
                root = new Node<>(element);
                return;
            }

            current = root;
            synchronized (current) {
                if (element.compareTo(current.getValue()) <= 0) {
                    Node<T> node = new Node<>(element);
                    node.setNext(current);
                    root = node;
                    return;
                }
            }
        }

        while (true) {
            synchronized (current) {
                next = current.getNext();
                if (next != null)
                    synchronized (next) {
                        if (element.compareTo(next.getValue()) <= 0){
                            // current va fi intotdeauna pe nodul dupa care tre sa inserez elementu
                            // si nu va fi null

                            Node<T> node = new Node<>(element);
                            node.setNext(next);
                            current.setNext(node);
                            return;
                        }
                        current = next;
                    }
                else {
                    Node<T> node = new Node<>(element);
                    current.setNext(node);
                    return;
                }
            }
        }
    }

    @Override
    public boolean removeFirst(T element) {
        Node<T> current;
        Node<T> next;

        synchronized (this) {
            if (root == null)
                return false;

            current = root;
            synchronized (current) {
                if (element.compareTo(current.getValue()) == 0) {
                    root = current.getNext();
                    return true;
                }
            }
        }

        while (true) {
            synchronized (current) {
                next = current.getNext();
                if (next != null)
                    synchronized (next) {
                        if (element.compareTo(next.getValue()) == 0){
                            current.setNext(next.getNext());
                            return true;
                        }
                        current = next;
                    }
                else
                    break;
            }
        }

        return false;
    }

    @Override
    public void removeAll(T element) {
        while (removeFirst(element)) ;
    }

    @Override
    public synchronized void clear() {
        root = null;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator<>(root);
    }

    @Override
    public synchronized Iterator<T> snapshotIterator() {
        SortedLinkedList<T> snapshot = new SortedLinkedList<>(this);
        return snapshot.iterator();
    }
}



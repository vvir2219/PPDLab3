import com.rits.cloning.Cloner;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class SortedLinkedList<T extends Comparable<T>> implements SortedList<T>{
    private Node<T> root = null;
    private final Cloner cloner=new Cloner();

    public SortedLinkedList(){}
    public SortedLinkedList(List<T> list) {
        for (T el : list) {
            this.insert(cloner.deepClone(el));
        }
    }

    public SortedLinkedList(SortedList<T> sortedList) {
        Iterator<T> iterator = sortedList.iterator();
        for(; iterator.valid(); iterator.next()){
            insert(cloner.deepClone(iterator.current()));
        }
    }

    @Override
    public void insert(T element) {
        if (root == null){
            root = new Node<>(element);
            return;
        }
        if (element.compareTo(root.getValue()) <= 0) {
            Node<T> node = new Node<>(element);
            node.setNext(root);
            root = node;
            return;
        }

        Node<T> current = root;
        Node<T> next = root.getNext();
        while(next != null && element.compareTo(next.getValue()) > 0){
            current = next;
            next = next.getNext();
        }

        // current va fi intotdeauna pe nodul dupa care tre sa inserez elementu
        // si nu va fi null

        Node<T> node = new Node<>(element);
        node.setNext(current.getNext());
        current.setNext(node);
    }

    @Override
    public boolean removeFirst(T element) {
        if (root == null)
            return false;
        if (element.compareTo(root.getValue()) == 0) {
            root = root.getNext();
            return true;
        }

        Node<T> current = root;
        Node<T> next = root.getNext();
        while(next != null && element.compareTo(next.getValue()) != 0){
            current = next;
            next = next.getNext();
        }

        if (next != null){
            current.setNext(next.getNext());
            return true;
        }
        return false;
    }

    @Override
    public void removeAll(T element) {
        while (removeFirst(element));
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator<>(root);
    }

    @Override
    public Iterator<T> snapshotIterator() {
        SortedLinkedList<T> snapshot = new SortedLinkedList<>(this);
        return new LinkedListIterator<>(snapshot.root);
    }
}



public class LinkedListIterator<T> implements Iterator<T> {
    private Node<T> current;

    public LinkedListIterator(Node<T> node) {
        this.current = node;
    }

    @Override
    public boolean valid() {
        return current != null;
    }

    @Override
    public T current() {
        if (valid())
            return current.getValue();
        return null;
    }

    @Override
    public T next() {
        if (valid()){
            current = current.getNext();
            if (valid())
                return current.getValue();
        }
        return null;
    }
}

import lombok.Data;

@Data
public class Node<T> {
    T value;
    Node<T> next = null;

    Node(T value) {
        this.value = value;
    }
}

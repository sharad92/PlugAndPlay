import java.util.List;
import java.util.NoSuchElementException;

public class CustomLinkedList<T> {

    private Node<T> firstNode;
    private Node<T> lastNode;
    private int sizeOfLinkedList = 0;

    public void add(T value) {
        if (value == null)
            throw new IllegalArgumentException();

        Node<T> node = new Node<>(value);
        if (isEmpty()) {
            firstNode = lastNode = node;
            sizeOfLinkedList++;
            return;
        }

        Node<T> current = firstNode;
        while (current.next != null) {
            current = current.next;
        }

        current.next = lastNode = node;
        sizeOfLinkedList++;
    }

    public void addAll(List<T> listOfValues) {
        for (T value : listOfValues) {
            add(value);
        }
    }

    public T removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();

        assert firstNode != null;
        T value = firstNode.value;
        if (isSingleElement()) {
            firstNode = lastNode = null;
            sizeOfLinkedList--;
            return value;
        }

        Node<T> newFirstNode = firstNode.next;
        firstNode.next = null;
        firstNode = newFirstNode;
        sizeOfLinkedList--;
        return value;
    }

    public T removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();

        assert firstNode != null;
        T value = lastNode.value;
        if (isSingleElement()) {
            firstNode = lastNode = null;
            sizeOfLinkedList--;
            return value;
        }

        Node<T> currentNode = firstNode;
        while (currentNode.next.next != null) {
            currentNode = currentNode.next;
        }

        currentNode.next = null;
        lastNode = currentNode;
        sizeOfLinkedList--;
        return value;
    }

    public void removeFirstOccurrence(T value) {
        if (isEmpty())
            throw new NoSuchElementException();

        if (isSingleElement()) {
            if (firstNode.value.equals(value)) {
                removeFirst();
                return;
            }

            throw new NoSuchElementException();
        }

        assert firstNode != null;
        if (firstNode.value.equals(value)) {
            removeFirst();
            return;
        }

        Node<T> currentNode = firstNode;
        while (currentNode.next != null) {
            if (currentNode.next.value.equals(value)) {
                Node<T> tempNode = currentNode.next.next;
                currentNode.next.next = null;
                currentNode.next = tempNode;
                sizeOfLinkedList--;
                return;
            }
            currentNode = currentNode.next;
        }

        throw new NoSuchElementException();
    }

    public void removeLastOccurrence(T value) {
        if (isEmpty())
            throw new NoSuchElementException();

        if (isSingleElement()) {
            if (lastNode.value.equals(value)) {
                removeLast();
                return;
            }

            throw new NoSuchElementException();
        }

        unlinkNode(false, -1, value);
    }

    public void removeKthOccurrence(int ocr, T value) {
        if (isEmpty())
            throw new NoSuchElementException();

        if (ocr == 0)
            throw new IllegalArgumentException();

        if (isSingleElement()) {
            if (ocr == 1 && firstNode.value.equals(value)) {
                removeFirst();
                return;
            } else if (ocr > 1) {
                throw new IllegalArgumentException();
            }

            throw new NoSuchElementException();
        }

        unlinkNode(true, ocr, value);
    }

    private void unlinkNode(boolean kthRemoval, int ocr, T value) {

        Node<T> nodeBeforeOccurrence = null;
        if (firstNode.value.equals(value)) {
            nodeBeforeOccurrence = firstNode;
            ocr--;
        }
        Node<T> currentNode = firstNode;
        while (currentNode.next != null) {
            if (ocr == 0)
                break;

            if (currentNode.next.value.equals(value)) {
                nodeBeforeOccurrence = currentNode;
                ocr--;
            }

            currentNode = currentNode.next;
        }

        if (kthRemoval && ocr > 0)
            throw new IllegalArgumentException();

        if (nodeBeforeOccurrence == firstNode &&
                nodeBeforeOccurrence.value.equals(value)) {
            removeFirst();
            return;
        }

        if (nodeBeforeOccurrence != null) {
            assert nodeBeforeOccurrence.next != null;
            Node<T> tempNode = nodeBeforeOccurrence.next.next;
            nodeBeforeOccurrence.next.next = null;
            nodeBeforeOccurrence.next = tempNode;
            sizeOfLinkedList--;
            return;
        }

        throw new NoSuchElementException();
    }

    public int size() {
        return this.sizeOfLinkedList;
    }

    private boolean isEmpty() {
        return (firstNode == null && lastNode == null);
    }

    private boolean isSingleElement() {
        return firstNode == lastNode;
    }

    public void print() {
        Node<T> current = firstNode;
        while (current != null) {
            System.out.println(current.value);
            current = current.next;
        }
    }

    private static class Node<T> {
        private final T value;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }
}

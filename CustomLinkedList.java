import java.util.*;

public class CustomLinkedList<T> {

    private Node<T> firstNode;
    private Node<T> lastNode;
    private int sizeOfLinkedList = 0;

    /**
     * Creates a new node with the provided value and adds it to the end of the linked list structure.
     *
     * @param value : value of the new node.
     */
    public void add(T value) {
        if (value == null)
            throw new IllegalArgumentException();

        Node<T> node = new Node<>(value);
        if (isEmpty()) {
            firstNode = lastNode = node;
            sizeOfLinkedList++;
            return;
        }

        Node<T> currentNode = firstNode;
        while (currentNode.next != null) {
            currentNode = currentNode.next;
        }

        currentNode.next = lastNode = node;
        sizeOfLinkedList++;
    }

    /**
     * Creates a new node with the provided value and adds it to the beginning of the linked list structure.
     *
     * @param value : value of the new node.
     */
    public void addFirst(T value) {
        if (value == null)
            throw new IllegalArgumentException();

        Node<T> node = new Node<>(value);
        if (isEmpty()) {
            firstNode = lastNode = node;
            sizeOfLinkedList++;
            return;
        }

        Node<T> nextNode = firstNode;
        firstNode = node;
        firstNode.next = nextNode;
        sizeOfLinkedList++;
    }

    /**
     * Creates new nodes with all values in the provided list and adds them to the end of the linked list structure.
     *
     * @param listOfValues : list of node values.
     */
    public void addAll(List<T> listOfValues) {
        for (T value : listOfValues) {
            add(value);
        }
    }

    /**
     * Removes and returns the top node from the structure and updates the top node.
     *
     * @return returns the removed value.
     */
    public T removeFirst() {
        if (isEmpty())
            throw new IllegalStateException();

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

    /**
     * Removes and returns the last node from the structure and updates the last node.
     *
     * @return returns the removed value.
     */
    public T removeLast() {
        if (isEmpty())
            throw new IllegalStateException();

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

    /**
     * Removes the first occurrence of the value passed to the function.
     *
     * @param value : value of the node to be removed.
     */
    public void removeFirstOccurrence(T value) {
        if (isEmpty())
            throw new IllegalStateException();

        if (isSingleElement()) {
            if (firstNode.value.equals(value)) {
                removeFirst();
                return;
            }

            throw new NoSuchElementException();
        }

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

    /**
     * Removes the last occurrence of the value passed to the function.
     *
     * @param value : value of the node to be removed.
     */
    public void removeLastOccurrence(T value) {
        if (isEmpty())
            throw new IllegalStateException();

        if (isSingleElement()) {
            if (lastNode.value.equals(value)) {
                removeLast();
                return;
            }

            throw new NoSuchElementException();
        }

        unlinkNode(false, -1, value);
    }

    /**
     * Removes the Kth occurrence of the value passed to the function.
     *
     * @param ocr   : value of K.
     * @param value : value of the node to be removed.
     */
    public void removeKthOccurrence(int ocr, T value) {
        if (isEmpty())
            throw new IllegalStateException();

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

    /**
     * Returns the Kth value from the last node.
     *
     * @param k : index of the node to be retrieved.
     * @return returns the Kth value from the last node.
     */
    public T getKthElementFromLast(int k) {
        if (isEmpty())
            throw new IllegalStateException();

        if (isSingleElement()) {
            if (k != 0)
                throw new IllegalArgumentException();
            else {
                return firstNode.value;
            }
        }

        int boundary = 0;
        Node<T> kthNode = firstNode;
        Node<T> currentNode = firstNode;
        while (currentNode != null) {
            kthNode = kthNode.next;
            currentNode = currentNode.next;

            if (boundary++ == k) {
                kthNode = firstNode;
            }
        }

        return kthNode != null ? kthNode.value : null;
    }

    /**
     * Removes all duplicate nodes from the structure.
     */
    public void deduplicateLinkedList() {
        if (isEmpty())
            throw new IllegalStateException();

        List<T> tList = new ArrayList<>();
        Node<T> currentNode = firstNode.next;
        Node<T> previousNode = firstNode;
        tList.add(firstNode.value);
        while (currentNode != null) {
            if (!tList.contains(currentNode.value)) {
                tList.add(currentNode.value);
            } else {
                Node<T> temp = currentNode.next;
                currentNode.next = null;
                previousNode.next = temp;

                currentNode = temp;
                sizeOfLinkedList--;
                continue;
            }

            previousNode = previousNode.next;
            currentNode = currentNode.next;
        }
    }

    /**
     * Removes duplicate nodes from the structure whilst allowing N duplicates.
     */
    public void deDuplicateLinkedListWithNDuplicatesAllowed(int N) {
        if (isEmpty())
            throw new IllegalStateException();

        if (N < 0)
            throw new IllegalArgumentException();

        Map<T, Integer> duplicateCounterMap = new HashMap<>();
        Node<T> currentNode = firstNode.next;
        Node<T> previousNode = firstNode;
        duplicateCounterMap.putIfAbsent(firstNode.value, 0);
        while (currentNode != null) {
            if (duplicateCounterMap.containsKey(currentNode.value)) {
                duplicateCounterMap.replace(currentNode.value, duplicateCounterMap.get(currentNode.value) + 1);
            } else {
                duplicateCounterMap.putIfAbsent(currentNode.value, 0);
            }

            if (duplicateCounterMap.get(currentNode.value) > N) {
                Node<T> temp = currentNode.next;
                currentNode.next = null;
                previousNode.next = temp;

                currentNode = temp;
                sizeOfLinkedList--;
                continue;
            }

            previousNode = previousNode.next;
            currentNode = currentNode.next;
        }
    }

    /**
     * Removes duplicate characters from a provided string on a per word basis.
     */
    @SuppressWarnings("unchecked")
    public String removeDuplicatesFromString(String str) {
        String[] wordsArr = str.split(" ");

        StringBuilder parentBuilder = new StringBuilder();
        for (String word : wordsArr) {
            delete();

            for (Object ch : word.split("")) {
                add((T) ch);
            }

            deduplicateLinkedList();
            StringBuilder childBuilder = new StringBuilder();
            Node<T> currentNode = firstNode;
            while (currentNode != null) {
                childBuilder.append(currentNode.value);
                currentNode = currentNode.next;
            }

            parentBuilder.append(childBuilder.toString()).append(" ");
        }

        return parentBuilder.toString().trim();
    }

    /**
     * Reverses the linked list structure.
     */
    public void reverse() {
        if (isEmpty())
            throw new IllegalStateException();

        if (isSingleElement())
            return;

        Node<T> newFirstNode = new Node<>(firstNode.value);
        Node<T> previousNode;

        Node<T> currentNode = firstNode;
        while (currentNode.next != null) {
            previousNode = newFirstNode;
            currentNode = currentNode.next;
            newFirstNode = new Node<>(currentNode.value);
            newFirstNode.next = previousNode;
        }

        lastNode = firstNode;
        firstNode = newFirstNode;
    }

    /**
     * Provides the functionality of a stack and adds element to the top of the structure.
     *
     * @param value : value of the new node.
     */
    public void push(T value) {
        addFirst(value);
    }

    /**
     * Provides the functionality of a stack and removes and returns the top element of the structure.
     *
     * @return Returns the top element from the structure.
     */
    public T pop() {
        return removeFirst();
    }

    /**
     * Returns the linked list as an array.
     */
    public Object[] toArray() {
        Object[] array = new Object[sizeOfLinkedList];
        int counter = 0;
        Node<T> currentNode = firstNode;
        while (currentNode != null) {
            array[counter++] = currentNode.value;
            currentNode = currentNode.next;
        }

        return array;
    }

    /**
     * Returns the size of the linked list.
     */
    public int size() {
        return this.sizeOfLinkedList;
    }

    private boolean isEmpty() {
        return firstNode == null;
    }

    private boolean isSingleElement() {
        return firstNode == lastNode;
    }

    private void delete() {
        if (isEmpty())
            return;

        firstNode = lastNode = null;
        sizeOfLinkedList = 0;
    }

    /**
     * Prints the structure in a graphical representation on the console.
     */
    public void print() {
        if (firstNode == null)
            return;

        Node<T> currentNode = firstNode;
        while (currentNode != null) {
            if (currentNode.next != null)
                System.out.print(currentNode.value.toString().concat(" -> "));
            else
                System.out.println(currentNode.value);
            currentNode = currentNode.next;
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

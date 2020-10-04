public class CustomDoublyLinkedList<T> {

    private Node<T> firstNode;
    private Node<T> lastNode;
    private Node<T> middleNode;
    private int size;
    private boolean isStack;
    private boolean isQueue;

    /**
     * Parametrized constructor for CustomDoublyLinkedList.
     *
     * @param stackOrQueue : The implementation type Stack or Queue.
     */
    public CustomDoublyLinkedList(String stackOrQueue) {
        if (stackOrQueue == null)
            throw new IllegalArgumentException();

        isStack = isQueue = false;
        if (stackOrQueue.toLowerCase().equalsIgnoreCase("stack"))
            isStack = true;
        else if (stackOrQueue.toLowerCase().equalsIgnoreCase("queue"))
            isQueue = true;
        else
            throw new IllegalArgumentException();

    }

    /**
     * Creates a new node with the provided value and adds it to the start of the linked list structure.
     * O(1) operation.
     *
     * @param value : Value of the new node.
     */
    public void add(T value) {
        if (value == null)
            throw new IllegalArgumentException();

        Node<T> node = new Node<>(value);
        if (empty()) {
            firstNode = lastNode = middleNode = node;
            size++;
            return;
        }

        Node<T> nextNode = firstNode;
        firstNode = node;
        firstNode.next = nextNode;
        firstNode.next.previous = firstNode;
        size++;

        if ((size() % 2) != 0) {
            middleNode = middleNode.previous;
        }
    }

    /**
     * Named implementation for an add operation on a stack implementation of linked list.
     * O(1) operation.
     *
     * @param value : Value of the new node.
     * @throws WrongMethodException : Custom exception implementation.
     */
    public void push(T value) throws WrongMethodException {
        if (isQueue)
            throw new WrongMethodException("Linked list is defined as queue, " +
                    "please use appropriately named add() function.");

        add(value);
    }

    /**
     * Creates a new node with the provided value and adds it to the start of the linked list structure.
     *
     * @param value : value of the new node.
     */
    public void addFirst(T value) {
        add(value);
    }

    private void addLast(T value) throws WrongMethodException {
        if (isStack)
            throw new WrongMethodException("Linked list is defined as stack, " +
                    "method cannot be executed.");
    }

    /**
     * Named operation which removes and returns the top element from a stack implementation of linked list.
     * O(1) operation.
     *
     * @return Top element in the list.
     * @throws WrongMethodException : Custom exception implementation.
     */
    public T pop() throws WrongMethodException {
        if (isQueue)
            throw new WrongMethodException("Linked list is defined as a queue, " +
                    "please use appropriately named remove() function.");

        return removeFirst();
    }

    /**
     * Named implementation which removes and returns the last element from a queue implementation of linked list.
     * O(1) operation.
     *
     * @return Top element in the list.
     * @throws WrongMethodException : Custom exception implementation.
     */
    public T remove() throws WrongMethodException {
        if (isStack)
            throw new WrongMethodException("Linked list is defined as a stack, " +
                    "please use appropriately named pop function.");

        return removeLast();
    }

    private T removeFirst() {
        if (empty())
            throw new NullPointerException();

        T value = firstNode.value;
        if (singleElementList()) {
            firstNode = lastNode = middleNode = null;
            size--;
            return value;
        }

        Node<T> nFirstNode = firstNode.next;
        firstNode.next = null;
        firstNode = nFirstNode;
        firstNode.previous = null;
        size--;

        if ((size % 2) == 0)
            middleNode = middleNode.next;

        return value;
    }

    private T removeLast() {
        if (empty())
            throw new NullPointerException();

        T value = lastNode.value;
        if (singleElementList()) {
            firstNode = lastNode = middleNode = null;
            size--;
            return value;
        }

        Node<T> nLastNode = lastNode.previous;
        lastNode.previous = null;
        lastNode = nLastNode;
        lastNode.next = null;
        size--;

        if ((size % 2) != 0)
            middleNode = middleNode.previous;

        return value;
    }

    /**
     * This function return the value of the middle node.
     *
     * @return value of middle node.
     */
    public T middle() {
        return middleNode.value;
    }

    /**
     * Inserts a new node to the middle of the list.
     * O(1) operation.
     *
     * @param value : Value of the node.
     */
    public void insertMiddle(T value) {
        if (value == null)
            throw new NullPointerException();

        Node<T> node = new Node<>(value);
        if ((size % 2) == 0) {
            //New middle element becomes current middle - 1.
            node.previous = middleNode.previous;
            node.next = middleNode;
            middleNode.previous.next = node;
            middleNode.previous = node;
        } else {
            //New middle element becomes current middle + 1.
            node.previous = middleNode;
            node.next = middleNode.next;
            middleNode.next.previous = node;
            middleNode.next = node;
        }

        middleNode = node;
        size++;
    }

    /**
     * Removes and updates the middle element from the list.
     * O(1) operation.
     *
     * @return Value of the deleted middle node.
     */
    public T removeMiddle() {
        if (empty())
            return null;

        T value = middleNode.value;
        Node<T> nMiddle = middleNode.next;
        Node<T> nPrevious = middleNode.previous;
        middleNode.next = middleNode.previous = null;
        nMiddle.previous = nPrevious;
        nPrevious.next = nMiddle;
        size--;

        if ((size % 2) == 0) {
            //New middle element becomes current middle + 1.
            middleNode = nMiddle;
        } else {
            //New middle element becomes current middle - 1.
            middleNode = nPrevious;
        }

        return value;
    }

    /**
     * Returns true if the list is a single element.
     */
    private boolean singleElementList() {
        return firstNode == lastNode;
    }

    /**
     * This returns the corresponding boolean on whether list is null.
     */
    public boolean empty() {
        return firstNode == null;
    }

    /**
     * Returns the size of the linked list.
     */
    public int size() {
        return this.size;
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

    static class Node<T> {
        private final T value;
        private Node<T> next;
        private Node<T> previous;

        public Node(T value) {
            this.value = value;
        }
    }

    static class WrongMethodException extends Exception {

        public WrongMethodException(String message) {
            super(message);
        }
    }
}

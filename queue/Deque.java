/*
public class Deque<Item> implements Iterable<Item> {
    public Deque()                           // construct an empty deque
    public boolean isEmpty()                 // is the deque empty?
    public int size()                        // return the number of items on the deque
    public void addFirst(Item item)          // insert the item at the front
    public void addLast(Item item)           // insert the item at the end
    public Item removeFirst()                // delete and return the item at the front
    public Item removeLast()                 // delete and return the item at the end
    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    public static void main(String[] args)   // unit testing
}
*/
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int N;
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node next;
        private Node prev;

        private Node(Item item) {
            this.item = item;
        }
    }

    public Deque() {
        N = 0;
        first = null;
        last = null;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException();

        Node newNode = new Node(item);
        if (isEmpty()) {
            first = newNode;
            last = newNode;
        }
        else {
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        }
        N++;

    }

    public void addLast(Item item) {
        if (item == null)
            throw new NullPointerException();
        
        Node newNode = new Node(item);
        if (isEmpty()) {
            first = newNode;
            last = newNode;
        }
        else {
            newNode.prev = last;
            last.next = newNode;
            last = newNode;
        }
        N++;
    }

    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();

        Item item = first.item;
        if (N == 1) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.prev = null;
        }
        N--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        
        Item item = last.item;
        if (N == 1) {
            first = null;
            last = null;
        } else {
            last = last.prev;
            last.next = null;
        }
        N--;
        return item;     
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext() {
            return current != null;
        }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<String> s = new Deque<String>();
        assert (s.size() == 0);
        int count = 0;
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            s.addLast(item);
            count++;
            assert (s.size() == count);
        }
        StdOut.println("size:" + s.size());
        for (String str : s) {
            StdOut.println(str);
        }

    }
}



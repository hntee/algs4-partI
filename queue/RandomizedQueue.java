/*
public class RandomizedQueue<Item> implements Iterable<Item> {
   public RandomizedQueue()                 // construct an empty randomized queue
   public boolean isEmpty()                 // is the queue empty?
   public int size()                        // return the number of items on the queue
   public void enqueue(Item item)           // add the item
   public Item dequeue()                    // delete and return a random item
   public Item sample()                     // return (but do not delete) a random item
   public Iterator<Item> iterator()         // return an independent iterator over items in random order
   public static void main(String[] args)   // unit testing
}

*/
import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int firstIndex;
    private int lastIndex;

    public RandomizedQueue() {
        firstIndex = 0;
        lastIndex = 0;
        a = (Item[]) new Object[2];
    }

    private void resize(int capacity) {
        assert capacity > size();
        Item[] temp = (Item[]) new Object[capacity];

        // copy a[firstIndex] to a[lastIndex - 1] to a total new array
        // and update two indices
        int j = 0;
        for (int i = firstIndex; i < lastIndex; i++) {
            temp[j++] = a[i];
        }
        a = temp;
        firstIndex = 0;
        lastIndex = j;
    }

    private void swap(Item[] a, int i, int j) {
        Item item = a[i];
        a[i] = a[j];
        a[j] = item;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return lastIndex - firstIndex;
    }

    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException();

        if (a.length == lastIndex) {
            // there is a case that firstIndex == lastIndex == a.length and size == 0
            // the following code can prevent the size from being 0 (at least 2)
            resize(2 * Math.max(1, size()));
        }

        a[lastIndex++] = item;
    }

    public Item dequeue() {
        if (size() == 0) throw new NoSuchElementException();

        if (size() == a.length / 4) 
            resize(Math.max(a.length / 2, 2));
        
        // choose a random item
        int randomIndex = StdRandom.uniform(firstIndex, lastIndex);

        Item item = a[randomIndex];

        // swap the first index and random index, 
        // make the index pointer null,
        // then increment firstIndex
        swap(a, firstIndex, randomIndex);
        a[firstIndex++] = null;
        return item;
    }

    public Item sample() {
        if (size() == 0) throw new NoSuchElementException();
        
        int randomIndex = StdRandom.uniform(firstIndex, lastIndex);
        return a[randomIndex];
    }
    
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int itFirst;
        private int itLast;
        private Item[] arr;

        public RandomizedQueueIterator() {
            if (size() > 0) {
                arr = (Item[]) new Object[size()];

                // copy a[firstIndex..lastIndex - 1] to arr
                int j = 0;
                for (int i = firstIndex; i < lastIndex; i++) {
                    arr[j++] = a[i];
                }
                itFirst = 0;
                itLast = j;

                // shuffle the array
                shuffle();
            }
        }

        // Knuth shuffle algorithm
        private void shuffle() {
            int n = arr.length;
            for (int i = 0; i < n; i++) {
                int r = StdRandom.uniform(i + 1);
                swap(arr, i, r);
            }
        }
        
        public boolean hasNext() {
            return itLast - itFirst != 0;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = arr[itFirst++];
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> rs = new RandomizedQueue();

        int count = 0;

        StdOut.print(Math.max(1, 4));
        RandomizedQueue<String> qq = new RandomizedQueue();

        for (int i = 0; i < 200; i++) {
            int rand = StdRandom.uniform(0, 10);
            if (rand < 7) {           
                qq.enqueue(new String("" + i));
            } else {
                qq.dequeue();
            }
        }
    }
}
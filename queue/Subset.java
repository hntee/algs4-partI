public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        if (k == 0) return;

        RandomizedQueue<String> randQueue = new RandomizedQueue<String>();

        int count = 0;
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();

            // put the first k items into the queue
            if (count < k) {
                randQueue.enqueue(item);
            } else { // reservoir sampling
                int r = StdRandom.uniform(0, count + 1);
                if (r < k) {
                    randQueue.dequeue();
                    randQueue.enqueue(item);
                }
            }
            count++;
        }

        for (String str : randQueue) {
            StdOut.println(str);
        }
    }
}
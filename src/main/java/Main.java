import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Main {
    public static void main(String[] args) {
        Object outputLock = new Object();

        SortedList<Integer> list = new FineGrainedSyncronizedSortelLinkedList<>();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 100; ++i) {
                list.insert(0);
                synchronized (outputLock) {
                    System.out.println("Thread 1 added 0 at " + Instant.now().getNano());
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 50; ++i) {
                list.insert(1);
                synchronized (outputLock) {
                    System.out.println("Thread 2 added 1 at " + Instant.now().getNano());
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 50; ++i) {
                list.removeFirst(i % 2);
                synchronized (outputLock) {
                    System.out.println("Thread 3 removed " + i % 2 + " at " + Instant.now().getNano());
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        Boolean bullshit = true;
        Thread t4 = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    break;
                }
                synchronized (outputLock) {
                    Iterator<Integer> it = list.snapshotIterator();
                    System.out.println("Snapshot at " + Instant.now().getNano());
                    for (; it.valid(); it.next())
                        System.out.print(it.current() + " ");
                    System.out.println();
                }
            }
        });
        ConcurrentLinkedDeque deque = new ConcurrentLinkedDeque();

        Instant now = Instant.now();
        t4.start();
        t1.start();
        t2.start();
        t3.start();
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Instant now1 = Instant.now();
        System.out.println("Total time: "+Duration.between(now, now1).toNanos());
    }
}

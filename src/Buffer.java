import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Buffer {

    // remember to initialize the semaphores in the constructor
    private Semaphore empty;
    private Semaphore full;
    private Semaphore mutex;
    //more private fields such as a container object for holding the buffer items
    private Object[] intArray;
    public static CyclicBarrier gate;
    //this cyclicBarrier will allow me to start all threads concurrently, however it is limited in the case where threre are more threads in the program than the CPU has

    public Buffer(String args1, String args2, String args3) throws InterruptedException {
        intArray = new Object[Constants.BUFFER_SIZE];
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = null;
        }
        empty = new Semaphore(Integer.parseInt(args3));
        full = new Semaphore(Integer.parseInt(args3));
        mutex = new Semaphore(1);
        empty.acquire(Integer.parseInt(args3));
        gate = new CyclicBarrier(Integer.parseInt(args1) + Integer.parseInt(args2) + 1);
    }

    public int insert_item(Object item) throws InterruptedException {

        //acquire semaphores
        try {
            mutex.acquire();
            if (full.availablePermits() > 0) {
                //critical section
                //insert the item
                for (int i = 0; i < intArray.length; i++) {
                    if (intArray[i] == null) {
                        intArray[i] = item;
                        break;
                    }
                }
                full.acquire();
                empty.release();

            } else {
                System.out.println("Array is full");
                mutex.release();
                return 0;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Mutex unavailable");
            mutex.release();
            return 0;
        }
        //release semaphores
        mutex.release();
        for (int i = 0; i < intArray.length; i++) {
            System.out.print("Buffer at index " + i + " holds " + intArray[i] + "  ");
        }
        System.out.println();
        return 1;
    }

    public void remove_item() {

        //acquire semaphores
        try {
            mutex.acquire();
            if (empty.availablePermits() > 0) {
                //critical section
                //insert the item
                for (int i = 0; i < intArray.length; i++) {
                    if (intArray[i] != null) {
                        intArray[i] = null;
                    }
                }
                full.release();
                empty.acquire();
            } else {
                System.out.println("Array is empty");
                mutex.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Mutex unavailable");
        } finally {
            for (int i = 0; i < intArray.length; i++) {
                System.out.print("Buffer at index " + i + " holds " + intArray[i] + "  ");
            }
            System.out.println();
            //release semaphores
            mutex.release();
        }
    }
}
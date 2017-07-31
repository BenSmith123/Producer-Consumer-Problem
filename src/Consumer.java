import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

public class Consumer implements Runnable {

    private Buffer buffer;

    public Consumer(Buffer _buffer) {

        this.buffer = _buffer;
    }

    public void run() {
        try {
            Buffer.gate.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        
        Random rng = new Random();
        
        try {
            Thread.sleep((long)rng.nextInt(10000));
            System.out.println("Consumer #"+Thread.currentThread().getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        buffer.remove_item();
        System.out.println();
    }
}

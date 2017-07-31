import java.util.concurrent.BrokenBarrierException;

public class Main 
{
    public static void main(String[] args) throws InterruptedException {

        // multiple producer/consumer threads need to be created based on input parameters
        Constants myConstant = new Constants(Integer.parseInt(args[2]));
        Buffer MyBuffer = new Buffer(args[0], args[1], args[2]);
        Producer p1 = new Producer(MyBuffer);
        Consumer c1 = new Consumer(MyBuffer);

        for(int i = 0; i < Integer.parseInt(args[0]); i++) {
            Thread t1 = new Thread(p1);;
            t1.start();
        }
        
        for(int i = 0; i < Integer.parseInt(args[1]); i++) {
            Thread t1 = new Thread(c1);
            t1.start();
        }
        
        try {
            Buffer.gate.await();
            System.out.println("Starting all Threads concurrently");
            
        } catch (InterruptedException e) {
            e.printStackTrace();
            
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }


    }
}
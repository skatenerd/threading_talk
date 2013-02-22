import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/11/13
 * Time: 8:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class IntegerHolderTest {
    public void attemptSleep(long miliseconds){
        try{Thread.sleep(miliseconds);}catch (Exception e){};
    }
    @Test
    public void testIncrement(){
        IntegerHolder holder = new IntegerHolder(0);
        holder.incrementPayload();
        assertTrue(holder.payload == 1);
    }







    @Test
    public void parallelIncrementTwoThreadsNoLatch(){
        int nthreads = 2;
        final int iterations = 1000;

        final IntegerHolder holder = new IntegerHolder(0);

        for(int i = 0; i < nthreads; i++){
            new Thread(){
                public void run(){
                    for(int incrementIteration = 0;
                        incrementIteration < iterations;
                        incrementIteration++){
                        holder.incrementPayload();
                    }
                }
            }.start();
        }
        attemptSleep(100);
        System.out.println(holder.payload);
        assertTrue(iterations * nthreads > holder.payload);
    }








    @Test
    public void clientLocking(){
        final IntegerHolder holder = new IntegerHolder(0);
        int nthreads = 2;
        final int iterations = 1000;
        for(int i = 0; i < nthreads; i++){
            new Thread(){
                public void run(){
                    for(int i = 0; i < iterations; i++){
                        synchronized (holder){
                            holder.incrementPayload();
                        }
                    }
                }
            }.start();
        }
        attemptSleep(100);
        assertEquals(2000, holder.payload);
        System.out.println(holder.payload);
    }




    @Test
    public void parallelIncrementLatchTwoThreads(){
        int nthreads = 2;
        final int iterations = 1000;

        final IntegerHolder holder = new IntegerHolder(0);
        final CountDownLatch latch = new CountDownLatch(nthreads);
        final CountDownLatch allDoneLatch = new CountDownLatch(nthreads);

        for(int i = 0; i < nthreads; i++){
            new Thread(){
                public void run(){
                    try{
                        latch.countDown();
                        latch.await();
                        for(int incrementIteration = 0; incrementIteration < iterations; incrementIteration++){
                            holder.incrementPayload();
                        }
                        allDoneLatch.countDown();

                    }catch(Exception e){}
                }
            }.start();
        }
        try{
            allDoneLatch.await();
        }catch(Exception e){}
        System.out.println(holder.payload);
        assertTrue(iterations * nthreads > holder.payload);
    }

    @Test
    public void parallelIncrementFewThreads(){
        int nthreads = 10;
        final int iterations = 1000;

        final IntegerHolder holder = new IntegerHolder(0);
        final CountDownLatch latch = new CountDownLatch(nthreads);
        final CountDownLatch allDoneLatch = new CountDownLatch(nthreads);

        for(int i = 0; i < nthreads; i++){
            new Thread(){
                public void run(){
                    try{
                        latch.countDown();
                        latch.await();
                        for(int incrementIteration = 0; incrementIteration < iterations; incrementIteration++){
                            holder.incrementPayload();
                        }
                        allDoneLatch.countDown();

                    }catch(Exception e){}
                }
            }.start();
        }
        try{
            allDoneLatch.await();
        }catch(Exception e){}
        System.out.println(iterations * nthreads);
        System.out.println(holder.payload);
        assertTrue(iterations * nthreads > holder.payload);
    }
}

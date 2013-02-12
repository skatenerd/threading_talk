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
    @Test
    public void testIncrement(){
        IntegerHolder holder = new IntegerHolder(0);
        holder.incrementPayload();
        assertTrue(holder.payload == 1);
    }

    @Test
    public void parallelIncrement(){
        int nthreads = 100;
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
        assertTrue(iterations * nthreads > holder.payload);
    }
    @Test
    public void parallelIncrementFewThreads(){
        int nthreads = 3;
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
        assertTrue(iterations * nthreads > holder.payload);
    }


}

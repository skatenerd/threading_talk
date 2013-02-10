import org.junit.Test;

import javax.sound.midi.SysexMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;


/**
 * Created with IntelliJ IDEA.
 * User: 8thlight
 * Date: 1/24/13
 * Time: 11:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class TransposerTest {
    private class MockTransposable implements Transposable {
        boolean flipped = false;
        public void transpose(){
            flipped = true;
        }
        public boolean isFlipped(){
            return flipped;
        }

    }

    @Test
    public void sequentialTranpose(){
        MockTransposable mockFlip = new MockTransposable();
        List<Transposable> toTranspose = new LinkedList<Transposable>();
        toTranspose.add(mockFlip);
        Transposer transposer= new Transposer(toTranspose);
        transposer.sequentialTransposeAll();
        assertTrue(mockFlip.isFlipped());
    }

    @Test
    public void parallelTranspose(){
        MockTransposable mockFlip = new MockTransposable();
        List<Transposable> toTranspose = new LinkedList<Transposable>();
        toTranspose.add(mockFlip);
        Transposer transposer= new Transposer(toTranspose);
        transposer.asyncTransposeAll();
        assertTrue(mockFlip.isFlipped());
    }

    @Test
    public void bigMatrixFewMatrices(){
        final List<Transposable> toTranspose  = Matrix.randomlyPopulateManyFloats(200, 20);

        long asyncTime = Timer.time(new Runnable(){
            public void run(){
                new Transposer(toTranspose).asyncTransposeAll();
            }
        });
        long sequentialTime = Timer.time(new Runnable(){
            public void run(){
                new Transposer(toTranspose).sequentialTransposeAll();
            }
        });
        long poolTime = Timer.time(new Runnable() {
            public void run() {
                new Transposer(toTranspose).poolTranspose();
            }
        });

//        System.out.println(asyncTime);
//        System.out.println(sequentialTime);
//        System.out.println(poolTime);


//        assertTrue(poolTime <= asyncTime);
//        assertTrue(asyncTime <= sequentialTime);
    }

    @Test
    public void pool(){
        MockTransposable mockFlip = new MockTransposable();
        List<Transposable> toTranspose = new LinkedList<Transposable>();
        toTranspose.add(mockFlip);


        Transposer doer = new Transposer(toTranspose);
        doer.poolTranspose();

        assertTrue(mockFlip.isFlipped());

    }
    @Test
    public void conflicts() throws InterruptedException{
        final CoordinateMatrix foo = CoordinateMatrix.SelfDescribing(100);
        int nthreads = 50;
        final CountDownLatch latch = new CountDownLatch(nthreads);
        final CountDownLatch allDoneLatch = new CountDownLatch(nthreads);
        for(int i=0; i< nthreads; i++)
            new Thread(){
                public void run(){
                    try{
                        latch.countDown();
                        latch.await();
                        foo.transpose();
                        allDoneLatch.countDown();
                    }
                    catch(InterruptedException e){
                        fail();
                    }
                }
            }.start();
        allDoneLatch.await();
        for (Integer[] whack:foo.nonSelfDescribingCoordinates()){

            System.out.println(Arrays.toString(whack));
            System.out.println(Arrays.toString(foo.dataAt(whack[0],whack[1])));
            if (Arrays.equals(whack, foo.dataAt(whack[0],whack[1]))){
                System.out.println("SAME");
            }
            System.out.println("----------");
        }
        System.out.println(foo.nonSelfDescribingCoordinates().size());

    }
}

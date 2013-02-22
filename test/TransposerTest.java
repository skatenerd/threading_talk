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
    @Test
    public void manySmallMatricesNaive(){
        final List<Transposable> toTranspose  = Matrix.randomlyPopulateManyFloats(10, 5000);

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

        System.out.println("Sequential time: " + sequentialTime);
        System.out.println("Thread per matrix: " + asyncTime);

        assertTrue(sequentialTime <= asyncTime);
    }







    @Test
    public void manySmallMatricesThreadPool(){
        final List<Transposable> toTranspose  = Matrix.randomlyPopulateManyFloats(10, 5000);

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

        System.out.println("Sequential time: " + sequentialTime);
        System.out.println("Thread per matrix: " + asyncTime);
        System.out.println("Thread pool: " + poolTime);

        assertTrue(sequentialTime <= poolTime);
        assertTrue(sequentialTime <= asyncTime);
    }

























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
    public void poolTranspose(){
        MockTransposable mockFlip = new MockTransposable();
        List<Transposable> toTranspose = new LinkedList<Transposable>();
        toTranspose.add(mockFlip);


        Transposer doer = new Transposer(toTranspose);
        doer.poolTranspose();

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
            public void run(){new Transposer(toTranspose).sequentialTransposeAll();
            }
        });
        long poolTime = Timer.time(new Runnable() {
            public void run() {
                new Transposer(toTranspose).poolTranspose();
            }
        });

        System.out.println("Sequential time: " + sequentialTime);
        System.out.println("Thread per matrix: " + asyncTime);
        System.out.println("Thread pool: " + poolTime);

        assertTrue(poolTime <= asyncTime);
        assertTrue(asyncTime <= sequentialTime);
    }

    @Test
    public void mediumMatrixManyMatrices(){
        final List<Transposable> toTranspose  = Matrix.randomlyPopulateManyFloats(100, 200);

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

        System.out.println("Sequential time: " + sequentialTime);
        System.out.println("Thread per matrix: " + asyncTime);
        System.out.println("Thread pool: " + poolTime);

        assertTrue(poolTime <= sequentialTime);
        assertTrue(asyncTime >= sequentialTime);
    }




    @Test
    public void conflictsFun() throws InterruptedException{
        final CoordinateMatrix matrix = CoordinateMatrix.SelfDescribing(100);
        int nthreads = 50;
        final CountDownLatch latch = new CountDownLatch(nthreads);
        final CountDownLatch allDoneLatch = new CountDownLatch(nthreads);
        for(int i=0; i< nthreads; i++)
            new Thread(){
                public void run(){
                    try{
                        latch.countDown();
                        latch.await();
                        matrix.transpose();
                        allDoneLatch.countDown();
                    }
                    catch(InterruptedException e){
                        fail();
                    }
                }
            }.start();
        allDoneLatch.await();






        for (Integer[] violationPoint:matrix.nonSelfDescribingCoordinates()){
            Integer [] dataAtViolationPoint = matrix.dataAt(violationPoint);
//            System.out.println("Point in bad state:     " + Arrays.toString(violationPoint));
//            System.out.println("Data at bad-state point:" + Arrays.toString(dataAtViolationPoint));
////            if (!matrix.dataUnexpected(violationPoint)){
////                System.out.println("SAME!!");
////            }
//
//            System.out.println("--------------");
        }
        System.out.println("Number of non-self-describing coordinates is:" + matrix.nonSelfDescribingCoordinates().size());
        //System.out.println(matrix.nonSelfDescribingCoordinates().size());

    }
}

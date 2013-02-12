import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/11/13
 * Time: 10:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleMathTest {
    @Test
    public void incrementsZero(){
        SimpleMath math = new SimpleMath();
        assertEquals(1, math.doubleAndIncrement(0));
    }
    @Test
    public void doublesAndIncrementsOne(){
        SimpleMath math = new SimpleMath();
        assertEquals(3, math.doubleAndIncrement(1));
    }
    @Test
    public void superThreadSafe(){
        final SimpleMath math = new SimpleMath();
        final Map<Integer, Integer> results = new ConcurrentHashMap<Integer, Integer>();
        final CountDownLatch latch = new CountDownLatch(1000);
        final CountDownLatch allDone = new CountDownLatch(1000);
        for(int i = 0; i < 1000; i++){
            final int copy = i;
            new Thread(){
                public void run(){
                    try{
                        latch.countDown();
                        latch.await();
                        int result = math.doubleAndIncrement(copy);
                        results.put(copy, result);
                        allDone.countDown();
                    }catch(Exception e){}
                }
            }.start();
        }

        try{
            allDone.await();
        }catch (Exception e){}

        assertEquals(new Integer(45), results.get(22));

    }

}

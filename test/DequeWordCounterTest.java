import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/21/13
 * Time: 8:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class DequeWordCounterTest {


    @Test
    public void IncrementalTimes(){
        final AtomicInteger threadCount = new AtomicInteger();
        for(threadCount.set(1); threadCount.get() < 5; threadCount.incrementAndGet()){
            long time = new Timer().time(new Runnable() {
                @Override
                public void run() {
                    final BlockingDeque<String> deque = new LinkedBlockingDeque<String>();
                    Thread reader = dequeueFiller(deque);
                    reader.start();
                    DequeWordCounter counter = new DequeWordCounter(deque, threadCount.get());
                    counter.countOccurrences();
                    try{counter.join();}catch (InterruptedException e){};
                    Map<String, AtomicInteger> counts = counter.counts;
                    assertEquals(4, counts.get("contabescence").get());
                    assertEquals(1, counts.get("protopope").get());
                }
            });

            System.out.println("Time with "+ threadCount + " threads: " + time);
        }
    }

    @Test
    public void TimeSequential() throws InterruptedException{
        long time = new Timer().time(new Runnable(){
            public void run(){
                final BlockingDeque<String> deque = new LinkedBlockingDeque<String>();
                Thread reader = dequeueFiller(deque);
                reader.start();
                DequeWordCounter counter = new DequeWordCounter(deque, -1);
                Map<String, Integer> counts = counter.countSequential();

                assertEquals((Integer)4, counts.get("contabescence"));
                assertEquals((Integer)1, counts.get("protopope"));
            }
        });

        System.out.println("Sequential:  " + time);

    }






    private Thread dequeueFiller(final BlockingDeque<String> deque){
        Thread reader = new Thread(){
            public void run (){
                try{
                    BufferedReader reader = new BufferedReader(new FileReader("./test/WordsFixtures/long_words.txt"));
                    String line;
                    while((line=reader.readLine())!=null){
                        deque.putLast(line);
                    }

                } catch (Exception e){};
            }
        };
        return reader;
    }

}

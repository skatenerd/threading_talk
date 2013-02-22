import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/21/13
 * Time: 7:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class DequeWordCounter {

    private final BlockingDeque<String> deque;
    private List<Thread> threads;
    public final ConcurrentHashMap<String, AtomicInteger> counts;
    private int concurrency;
    public DequeWordCounter(BlockingDeque<String> deque, int concurrency){
        this.deque = deque;
        threads = new ArrayList<Thread>();
        this.counts =new ConcurrentHashMap<String, AtomicInteger>();
        this.concurrency = concurrency;
    }

    public void join() throws InterruptedException{
        for(Thread t:threads){
            t.join();
        }
    }


    public void countOccurrences(){
        for(int thread=0; thread < concurrency; thread++){
            Thread t=new Thread(){
                public void run(){
                    while(true){
                        try{
                            String word = deque.pollFirst(10, TimeUnit.MILLISECONDS);
                            if(word!=null){
                                counts.putIfAbsent(word, new AtomicInteger());
                                counts.get(word).getAndIncrement();
                            } else {
                                return;
                            }
                        }catch(InterruptedException e){
                            return;
                        }
                    }
                }
            };
            t.start();
            threads.add(t);
        }
        return;
    }





    public Map<String, Integer> countSequential(){
        Map<String, Integer> counts = new HashMap<String, Integer>();
        try{
        String word;
        while((word = deque.pollFirst(10, TimeUnit.MILLISECONDS))!=null){
            if (counts.get(word) == null){
                counts.put(word, 1);
            }else{
                counts.put(word, counts.get(word) + 1);
            }
        }
        return counts;
        }catch(InterruptedException e){return null;}
    }





































//    public Map<String, Integer> countOccurrencesSequentially(){
//        final Map<String, Integer> counts = new HashMap<String, Integer>();
//        for(final BufferedReader reader : readers){
//            incrementSequentialForFile(counts, reader);
//        }
//        return counts;
//    }
//
//
//    private Boolean incrementSequentialForFile(Map<String, Integer> counts, BufferedReader reader){
//        try{
//            String line;
//            while((line=reader.readLine())!=null){
//                Integer oldCount = counts.get(line);
//                if(oldCount == null){
//                    oldCount = 0;
//                }
//                counts.put(line, oldCount + 1);
//            }
//            return true;
//        }catch(IOException e){return false;}
//    }


}

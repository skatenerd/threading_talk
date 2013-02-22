import com.sun.corba.se.spi.orbutil.threadpool.ThreadPool;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/20/13
 * Time: 10:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class WordCounter {
    private final List<BufferedReader> readers;
    public WordCounter(List<BufferedReader> readers){
        this.readers = readers;
    }


    public Map<String, AtomicInteger> countOccurrences(){
        try{
            final ConcurrentHashMap<String, AtomicInteger> counts = new ConcurrentHashMap<String, AtomicInteger>();

            ExecutorService pool = Executors.newFixedThreadPool(4);

            Collection<Callable<Boolean>>callables = new ArrayList<Callable<Boolean>>();

            for(final BufferedReader reader : readers){
                callables.add(new Callable<Boolean>(){
                    public Boolean call(){
                        return incrementForFile(counts, reader);
                    }
                });
            }

            List<Future<Boolean>> futures = pool.invokeAll(callables);

            //check for failure
            for(Future<Boolean> future : futures){
                if (future.get() == false){
                    return null;
                }
            }

            return counts;

        }catch(Exception e){return null;}
    }

    private Boolean incrementForFile(ConcurrentHashMap<String, AtomicInteger> counts, BufferedReader reader){
        try{
        String line;
        while((line=reader.readLine())!=null){
            counts.putIfAbsent(line, new AtomicInteger());
            counts.get(line).getAndIncrement();
        }
        return true;
        }catch(IOException e){return false;}
    }






































    public Map<String, Integer> countOccurrencesSequentially(){
        final Map<String, Integer> counts = new HashMap<String, Integer>();
        for(final BufferedReader reader : readers){
            incrementSequentialForFile(counts, reader);
        }
        return counts;
    }


    private Boolean incrementSequentialForFile(Map<String, Integer> counts, BufferedReader reader){
        try{
            String line;
            while((line=reader.readLine())!=null){
                Integer oldCount = counts.get(line);
                if(oldCount == null){
                    oldCount = 0;
                }
                counts.put(line, oldCount + 1);
            }
            return true;
        }catch(IOException e){return false;}
    }


}

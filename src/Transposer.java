import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: 8thlight
 * Date: 1/24/13
 * Time: 11:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Transposer{
    List<Transposable> toTranspose;
    public Transposer(List<Transposable> toTranspose){
        this.toTranspose = toTranspose;
    }


    public void sequentialTransposeAll(){
        for(Transposable t: toTranspose){
            t.transpose();
        }
    }


    public void asyncTransposeAll(){
        List<Thread> threads = new LinkedList<Thread>();

        for(final Transposable t: toTranspose){
            threads.add(new Thread(new Runnable(){ public void run(){
                t.transpose();
        }}));}

        for(Thread t:threads){
            t.start();
        }

        joinThreads(threads);
    }





























    public void poolTranspose(){
        try{
            ExecutorService foo = Executors.newCachedThreadPool();
            Collection<Callable<Transposable>> callables = new ArrayList<Callable<Transposable>>();
            for(final Transposable t: toTranspose){
                callables.add(new Callable(){
                   public Transposable call(){
                       t.transpose();
                       return t;
                   }
                });
            }
            List futures = foo.invokeAll(callables);


        }catch(Exception e){};

    }






















    private void joinThreads(List<Thread> threads){
        try{
        for(Thread t:threads){
            t.join();
        }
        }catch(InterruptedException e){
            System.out.println("NOOOO");
        }
    }
}

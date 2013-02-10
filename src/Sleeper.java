import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: 8thlight
 * Date: 1/25/13
 * Time: 5:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class Sleeper{
    public static void sleepAsynchronous(final int miliseconds, int times){
        try{
            List<Thread> threads = new ArrayList<Thread>();
            for(int i = 0; i < times; i++){
                Thread t = new Thread(new Runnable(){
                   public void run(){
                       try{
                           Thread.sleep(miliseconds);
                       }catch(Exception e){}
                   }
                });
                t.start();
                threads.add(t);
            }
            for(Thread t:threads){
                t.join();
            }
        }catch(InterruptedException e){}
    }
}

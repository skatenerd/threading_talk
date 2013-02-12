import org.junit.Test;
import static org.junit.Assert.*;
import sun.tools.tree.NewArrayExpression;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/11/13
 * Time: 11:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class VisibilityTest {
    @Test
    public void visibilityHurts(){
        final boolean  [] run = new boolean[1];
        run[0] = true;
        Runnable forever = new Runnable(){
            public void run(){
                while(run[0]){
                    System.out.println("running" + Thread.currentThread().getId());
                }

            }
        };


//        for(int i=0; i < 10; i++){
//            run[0] = true;
//            Thread foreverThread = new Thread(forever);
//            foreverThread.start();
//            try{Thread.sleep(1);}catch(Exception e){}
//            run[0] = false;
//            System.out.println("STOPPED");
//            try{foreverThread.join();}catch(Exception e){}
//        }

        run[0] = true;
        List<Thread> threads = new LinkedList<Thread>();
        for(int i=0; i < 1000; i++){
            Thread t = new Thread(forever);
            t.start();
            threads.add(t);
        }
        try{Thread.sleep(1);} catch (Exception e){}
        run[0] = false;
        for(Thread t:threads){
            try{t.join();} catch(Exception e){}
        }


    }


}

import org.junit.Test;
import static org.junit.Assert.*;
import sun.tools.tree.NewArrayExpression;

import java.io.IOException;
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
    public void explode(){
        NuclearCountdown countdown = new NuclearCountdown(10, 1);
        countdown.initiate();
    }

















    @Test
    public void takeANuclearRisk() throws InterruptedException{
        final NuclearCountdown countdown = new NuclearCountdown(10, 1);
        Thread t = new Thread(){
            public void run(){countdown.initiate();}
        };
        t.start();
        Thread.sleep(9);
        countdown.cancelled = true;
        t.join();
    }











    @Test
    public void nuclearStressTest() throws InterruptedException{
        for(int i=0; i < 500; i++){
            final NuclearCountdown countdown = new NuclearCountdown(10, 1);
            Thread t = new Thread(){
                public void run(){countdown.initiate();}
            };
            t.start();
            Thread.sleep(8);
            countdown.cancelled = true;
            t.join();
        }
    }


    @Test
    public void cancelInNewThread() throws InterruptedException{
        final NuclearCountdown countdown = new NuclearCountdown(10, 1);
        Thread t = new Thread(){
            public void run(){countdown.initiate();}
        };
        t.start();
        Thread.sleep(9);
        new Thread(){
            public void run(){
                countdown.cancelled = true;
            }
        }.start();
        t.join();
    }
}

import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: 8thlight
 * Date: 1/25/13
 * Time: 5:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class SleeperTest {
    @Test
    public void sequentialSleep(){
        long time = Timer.time(new Runnable(){
            public void run(){
                Sleeper.sleepAsynchronous(101, 2);
            }
        });
        assertTrue(time < 200);
    }
}

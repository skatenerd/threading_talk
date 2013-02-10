/**
 * Created with IntelliJ IDEA.
 * User: 8thlight
 * Date: 1/25/13
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class Timer {
    public static long time(Runnable task){
        long start = System.currentTimeMillis();
        task.run();
        long end = System.currentTimeMillis();
        return end - start;
    }

}

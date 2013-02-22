/**
 * Created with IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/19/13
 * Time: 5:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class NuclearCountdown {
    public int count;
    public boolean cancelled;
    public long tickTime;

    public NuclearCountdown(int initialValue,
                            long tickTime)
    {
        this.tickTime = tickTime;
        this.count = initialValue;
        this.cancelled = false;
    }

    public void initiate(){
        try{
            while(!cancelled){
                Thread.sleep(tickTime);
                System.out.println("TICKS LEFT " + count);
                count --;
                if(count == 0){
                    launchMissiles();
                }

            }
        }catch(InterruptedException e){
            cancelled = true;
        }
    }

//    private synchronized boolean isCancelled(){
//        return cancelled;
//    }
//
//    public synchronized void cancel(){
//        cancelled = true;
//    }

    private void launchMissiles(){
        throw new IllegalStateException(
                "WORLD OVER, SORRY.\n" +
                "NO MORE COMPUTER MEANS\n" +
                "NO MORE JVM");
    }
}

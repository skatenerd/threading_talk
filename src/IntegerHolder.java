/**
 * Created with IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/11/13
 * Time: 8:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class IntegerHolder {
    public int payload;

    public IntegerHolder(int payload){
        this.payload = payload;
    }

    public void incrementPayload(){
        this.payload = this.payload + 1;
        //HOW THIS SHOULD LOOK TO YOU:
        //int newPayload = this.payload + 1;
        //try{Thread.sleep((int)Math.random()*100);}catch(Exception e){};
        //this.payload = newPayload;
    }

//    1.  Use AtomicInteger
//    2.  Use Locking
//    3.  Use Client-Side locking














    public int incrementNumberOnStack(int toIncrement){
        int incremented = toIncrement;
        incremented += 1;
        return incremented;
    }

}


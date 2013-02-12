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
    }
}
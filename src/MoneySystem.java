import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/19/13
 * Time: 10:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class MoneySystem {
    //INVARIANT:
    //CONSTANT AMOUNT OF MONEY IN THE SYSTEM

    private Map<String, Integer> values;

    public MoneySystem(String[] IDs, Integer startingAmount){
        this.values = new HashMap<String, Integer>();
        for(String id:IDs){
            values.put(id, startingAmount);
        }
    }
    public void transferMoney(String fromID, String toID, Integer amount){
        synchronized (values){
            values.put(fromID, values.get(fromID) - amount);
            values.put(toID, values.get(toID) + amount);
        }
    }

    public Integer allMoney(){
        Integer total = 0;
        synchronized (values){
            for(Integer money : values.values()){
                total += money;
            }
        }
        return total;
    }

    public Integer lookupMoney(String ID){
        return values.get(ID);
    }
}

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/22/13
 * Time: 12:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class MoneySystemTest {
    @Test
    public void updates(){
        final String[] names = {"Joey", "Susan", "Micah", "Steve", "Kevin", "Balto"};
        final MoneySystem system = new MoneySystem(names, 1000);

        Thread chaosMoney = new Thread(){
            public void run(){
                while(!interrupted()){
                    String from = names[(int)(Math.random() * 6)];
                    String to = names[(int)(Math.random() * 6)];
                    system.transferMoney(from, to, (int)(Math.random() * 50));
                }
            }
        };

        chaosMoney.start();
        for(int attempt = 0; attempt < 1000; attempt ++){
            assertEquals((Integer) 6000, system.allMoney());
        }
        chaosMoney.interrupt();

    }
}

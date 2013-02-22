/**
 * Created with IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/22/13
 * Time: 1:25 AM
 * To change this template use File | Settings | File Templates.
 */
import org.junit.Test;
import static org.junit.Assert.*;
public class EmployeeTest {
    @Test
    public void visibility() throws InterruptedException{
        final Employee employee = new Employee(
                1000,
                "WORKER",
                "HOWARD"
        );

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                employee.name = "FIRED";
                employee.title = "FIRED";
                employee.salary = -1;
            }
        });
        t.start();
        t.join();
        if(employee.salary == 1000){
            assertEquals(employee.title, "WORKER");
            assertEquals(employee.name, "HOWARD");
        }else{
            assertEquals(employee.name, "FIRED");
            assertEquals(employee.title, "FIRED");
        }


    }
}

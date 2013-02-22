import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/21/13
 * Time: 5:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class MergeSortTest {
    @Test
    public void ParallelSortTest() throws Exception{
        Integer[] numbers = {2,1,2,3,4,4,5,6,6,8,7,9,10};
        ArrayList<Integer> numberList = new ArrayList(Arrays.asList(numbers));
        Collections.shuffle(numberList);

        MergeSort sorter = new MergeSort(numberList, 1);
        List<Integer> sorted = sorter.sorted();

        for(int i=0;i < sorted.size()-1; i++){
            assertTrue(sorted.get(i) <= sorted.get(i+1));
        }
    }

    @Test
    public void BigTest() throws  Exception{
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        int size = 100000;
        for(int i=0; i < size; i++){
            numbers.add((int)(Math.random() * size));
        }


        MergeSort sorter = new MergeSort(numbers, 4);
        List<Integer> sorted = sorter.sorted();
        for(int i=0; i < size-1; i++){
            assertTrue(sorted.get(i) <= sorted.get(i+1));
        }
    }








    @Test
    public void Speed() throws Exception{
        final AtomicInteger parallelism = new AtomicInteger();
        for(int i=0; i < 5; i++){
            try{
                buildAndShuffleList(10000, 1);} catch(Exception e){};
        }

        final int listSize = 100000;

        for(parallelism.set(1); parallelism.get() < 10; parallelism.incrementAndGet()){
            long t = Timer.time(new Runnable() {
                @Override
                public void run() {
                try{buildAndShuffleList(listSize, parallelism.get());} catch(Exception e){};
                }
            });
            System.out.println("PARALLELISM LEVEL:" + parallelism.get() + " TIME TO SORT: " + t);
        }
    }















    private void buildAndShuffleList(int size, int parallelism)throws Exception{
        MergeSort sorter = new MergeSort(randomList(size), parallelism);
        sorter.sorted();
    }

    private ArrayList<Integer> randomList(int size){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i=0;i<size;i++){
            list.add((int) (Math.random() * size));
        }
        return list;
    }
}

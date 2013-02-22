import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created with IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/21/13
 * Time: 5:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class MergeSort {
    private final ExecutorService executorService;
    private final ArrayList<Integer> toSort;
    private ArrayList<Integer> sorted;
    private final Integer parallelism;

    public MergeSort(ArrayList<Integer> toSort, Integer parallelism){
        this.toSort = toSort;
        this.executorService = Executors.newCachedThreadPool();
        this.parallelism = parallelism;
    }

    public ArrayList<Integer> sorted() throws InterruptedException, ExecutionException{
        this.sorted = sort(toSort);
        return sorted;
    }




    public ArrayList<Integer> sort(final ArrayList<Integer> toSort) throws InterruptedException, ExecutionException {
        if (toSort.size() == 1){
            return toSort;
        }
        if(shouldParallelizeComputation(toSort)){
            Future<ArrayList<Integer>> lower = executorService.submit(new Callable<ArrayList<Integer>>() {
                @Override
                public ArrayList<Integer> call() throws Exception {
                    return sort(firstHalf(toSort));
                }
            });
            Future<ArrayList<Integer>> upper = executorService.submit(new Callable<ArrayList<Integer>>() {
                @Override
                public ArrayList<Integer> call() throws Exception {
                    return sort(secondHalf(toSort));
                }
            });
            return merge(lower.get(), upper.get());

        }else{
            ArrayList<Integer> sortedLower = sort(firstHalf(toSort));
            ArrayList<Integer> sortedUpper = sort(secondHalf(toSort));
            return merge(sortedLower, sortedUpper);
        }

    }












    private boolean shouldParallelizeComputation(List<Integer> subList){
//        return true;
        Integer threshold = toSort.size()/parallelism;
        return subList.size() > threshold;
    }


    private ArrayList<Integer> firstHalf(ArrayList<Integer> toChop){
        return new ArrayList<Integer>(toChop.subList(0, toChop.size()/2));
    }

    private ArrayList<Integer> secondHalf(ArrayList<Integer> toChop){
        return new ArrayList<Integer>(toChop.subList(toChop.size()/2, toChop.size()));
    }

    private ArrayList<Integer> merge(ArrayList<Integer> first, ArrayList<Integer> second){
        ArrayList<Integer> merged = new ArrayList<Integer>();
        while((!first.isEmpty()) && (!second.isEmpty())){
            merged.add(listWithSmallerStart(first, second).remove(0));
        }
        merged.addAll(first);
        merged.addAll(second);
        return merged;
    }

    private ArrayList<Integer> listWithSmallerStart(ArrayList<Integer> first, ArrayList<Integer> second){
        Integer first_first = first.get(0);
        Integer second_first = second.get(0);
        return (first_first < second_first) ? first : second;
    }



}

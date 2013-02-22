import java.util.ArrayList;
import java.util.List;


public class ListAndSum {
    //INVARIANT:
    //SUM = SUM OF ELEMENTS IN LIST

    private List<Integer> numbers;
    private Integer sum;

    public ListAndSum(){
        numbers = new ArrayList<Integer>();
        sum = 0;
    }

    public synchronized void add(Integer toAdd){
        numbers.add(toAdd);
        sum += toAdd;
    }

    public List<Integer> getNumbers(){
        return numbers;
    }

    public Integer getSum(){
        return sum;
    }




    private class ImmutableListView<T> {
        private int foo;
        private List<T> backingList;

        public ImmutableListView(List<T> backingList){
            this.backingList = backingList;
        }

        public T get(int index){
            return backingList.get(index);
        }

    }
}

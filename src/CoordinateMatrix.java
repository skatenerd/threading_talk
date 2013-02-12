import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 8thlight
 * Date: 2/9/13
 * Time: 11:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class CoordinateMatrix extends Matrix<Integer[]>{

    public CoordinateMatrix(Integer[][][] coordinates){
        super(coordinates);
    }

    public Integer[] dataAt(Integer[] coordinates){
        return dataAt(coordinates[0], coordinates[1]);
    }
    public static CoordinateMatrix SelfDescribing(int size){
        Integer[][][] coordinates = new Integer[size][size][2];
        for(int row=0; row < size; row++){
            for(int col=0; col< size; col++){
                coordinates[row][col] = selfDescribingData(row, col);
            }
        }
        return new CoordinateMatrix(coordinates);
    }

    public List<Integer[]> nonSelfDescribingCoordinates(){
        List<Integer[]> rtn = new LinkedList<Integer[]>();
        int max = data.length - 1;
        for(int i=0; i < max; i++){
            for(int j=0; j< max; j++){
                if (dataUnexpected(i,j)){
                    rtn.add(dataAt(i,j));
                }
            }
        }
        return rtn;
    }
    private boolean dataUnexpected(int row, int col){
        Integer [] expected = selfDescribingData(row, col);
        Integer [] actual = dataAt(row,col);

        return !(Arrays.equals(actual, expected));
    }

    public boolean dataUnexpected(Integer [] coordinates){
        return dataUnexpected(coordinates[0], coordinates[1]);
    }

    private static Integer [] selfDescribingData(int row, int col){
        return new Integer[]{row,col};
    }
}

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
//        int[][][] rtn = new int[size][size][2];
//        for(int row=0; row < size; row ++){
//            for(int col=0; col < size; col++){
//                int[] describer = {row, col};
//                rtn[row][col] = describer;
//            }
//        }

    }
    public static CoordinateMatrix SelfDescribing(int size){
        Integer[][][] coordinates = new Integer[size][size][2];
        for(int i=0; i < size; i++){
            for(int j=0; j< size; j++){
                coordinates[i][j] = new Integer[]{i,j};
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
        Integer [] expected = new Integer[]{row,col};
        Integer [] actual = dataAt(row,col);
//        if(!(Arrays.equals(actual, expected))){
//            System.out.println(Arrays.toString(actual));
//            System.out.println(Arrays.toString(expected));
//            System.out.println("++++++++++++++++++++++");
//        }
        return !(Arrays.equals(actual, expected));

    }
}

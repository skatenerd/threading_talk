import sun.tools.tree.NewArrayExpression;

import java.util.*;
/**
 * Created with IntelliJ IDEA.
 * User: 8thlight
 * Date: 1/22/13
     * Time: 11:52 PM
     * To change this template use File | Settings | File Templates.
     */
    public class Matrix<T> implements Transposable {
        protected final T[][] data;
        public Matrix(T[][] nestedArray){
            data = nestedArray;
        }

    public T dataAt(int row, int col){
        return data[row][col];
    }

    public synchronized void transpose(){
        int max = data.length - 1;
        for(int i=0; i <= max; i++){
            int maxcol = i / 2;
            for(int j=0; j<= maxcol; j++){
              T tmp = data[i][j];
              data[i][j] = data[j][i];
              data[j][i] = tmp;

            }
        }

    }

    public static List<Transposable> randomlyPopulateManyFloats(int size, int quantity){
        List<Transposable> rtn = new ArrayList<Transposable>();
        for(int i = 0; i < quantity; i++){
            rtn.add(randoFloat(size));
        }
        return rtn;
    }

    private static Transposable randoFloat(int size){
        Float[][] rtn = new Float[size][size];
        for(int row=0; row < size; row ++){
            for(int col=0; col < size; col++){
                rtn[row][col] = ((Double) Math.random()).floatValue();
            }
        }
        return new Matrix<Float>(rtn);
    }
}

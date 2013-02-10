import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: 8thlight
 * Date: 1/22/13
 * Time: 11:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class MatrixTest extends TestCase {

    @Test
    public void testTransposeSizeTwo(){
        Double[][] foo = {{1.0, 2.0},
                          {1.0, 2.0}};
        Matrix<Double> m = new Matrix(foo);
        m.transpose();
        assertEquals(m.dataAt(0,0), 1.0, 0.01);
        assertEquals(m.dataAt(1,0), 2.0, 0.01);
        assertEquals(m.dataAt(0,1), 1.0, 0.01);
        assertEquals(m.dataAt(1,1), 2.0, 0.01);
    }

    @Test
    public void testTranspositionSizeThree(){
        //assertEquals(true, true);
        //float[] first_row = {1.0, 2.0};
        Double[][] foo = {{0.0, 0.1, 0.2},
                          {1.0, 1.1, 1.2},
                          {2.0, 2.1, 2.2}};
        Matrix<Double> m = new Matrix(foo);
        m.transpose();
        assertEquals(m.dataAt(1,1), 1.1, 0.01);
        assertEquals(m.dataAt(1,0), 0.1, 0.01);
        assertEquals(m.dataAt(0,1), 1.0, 0.01);
    }
    @Test
    public void testRandomstuff(){
        List<Transposable> randoms = Matrix.randomlyPopulateManyFloats(10, 10);
        assertEquals(randoms.size(), 10);
    }

    @Test
    public void testSelfDescribingGeneration(){
        CoordinateMatrix selfDescribing = CoordinateMatrix.SelfDescribing(2);
        Integer [] upperLeft = {0,0};
        Integer [] bottomRight = {1,1};
        assertArrayEquals(selfDescribing.dataAt(0,0), upperLeft);
        assertArrayEquals(selfDescribing.dataAt(1,1), bottomRight);
    }

}
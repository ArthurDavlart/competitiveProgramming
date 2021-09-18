import org.junit.jupiter.api.Test;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavaUtilsTests {
    @Test
    public void treeSetFillTests(){
        TreeSet<Integer> treeSet = new TreeSet<Integer>();
        treeSet.add(8);
        treeSet.add(2);

        assertEquals(2, treeSet.first());
    }
}

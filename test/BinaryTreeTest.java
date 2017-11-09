import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BinaryTreeTest {
    private BinaryTree<Integer> binaryTree = new BinaryTree<>();

    @Before
    public void terms() {
        binaryTree.add(10);
        binaryTree.add(6);
        binaryTree.add(21);
        binaryTree.add(4);
        binaryTree.add(3);
        binaryTree.add(5);
        binaryTree.add(8);
        binaryTree.add(7);
        binaryTree.add(9);
        binaryTree.add(16);
        binaryTree.add(26);
        binaryTree.add(14);
        binaryTree.add(18);
        binaryTree.add(24);
        binaryTree.add(22);
        binaryTree.add(25);
        binaryTree.add(28);
        binaryTree.add(27);
        binaryTree.add(31);
    }

    @Test
    public void remove() {

        //Узел с двумя детьми
        binaryTree.remove(6);
        assertEquals(false, binaryTree.contains(6));
        assertEquals(true, binaryTree.checkInvariant());

        //Узел без детей
        binaryTree.remove(31);
        assertEquals(false, binaryTree.contains(31));
        assertEquals(true, binaryTree.checkInvariant());

        //Узел с одним ребенком
        binaryTree.remove(28);
        assertEquals(false, binaryTree.contains(28));
        assertEquals(true, binaryTree.checkInvariant());

        //Корень
        binaryTree.remove(10);
        assertEquals(false, binaryTree.contains(10));
        assertEquals(true, binaryTree.checkInvariant());

        //Несуществующий узел
        binaryTree.remove(156);
        assertEquals(false, binaryTree.contains(156));
        assertEquals(true, binaryTree.checkInvariant());
    }
}
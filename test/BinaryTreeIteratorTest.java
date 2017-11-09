import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class BinaryTreeIteratorTest {
    private BinaryTree<Integer> binaryTree = new BinaryTree<>();

    @Before
    public void terms() {
        binaryTree.add(8);
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
        binaryTree.add(30);
        binaryTree.add(32);
    }

    @Test()
    public void findNext() {
        Iterator<Integer> iterator = binaryTree.iterator();
        int treeSize = 0;
        Integer pastNode, curNode;


        treeSize++;
        curNode = iterator.next();
        while (iterator.hasNext()) {
            treeSize++;
            pastNode = curNode;
            curNode = iterator.next();
            assertEquals(true, curNode.compareTo(pastNode) > 0);
        }
        assertEquals(true, treeSize == binaryTree.size());
    }
}
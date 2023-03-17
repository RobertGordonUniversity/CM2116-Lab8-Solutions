package uk.ac.rgu.cm2116;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TestBinarySearchTree {
    
    @Test
    public void testPut(){

        BinarySearchTree<Integer, Dummy> tree = new BinarySearchTree<>();

        Dummy dummy1 = new Dummy(1);
        Dummy dummy2 = new Dummy(2);

        boolean result1 = tree.put(1, dummy1);
        boolean result2 = tree.put(2, dummy1);
        boolean result3 = tree.put(1, dummy2);

        assertTrue(result1);
        assertTrue(result2);
        assertFalse(result3);

        ArrayList<Node<Integer, Dummy>> traversal = tree.traverse();

        Node<Integer, Dummy> temp1 = new Node<>(1, dummy1);
        Node<Integer, Dummy> temp2 = new Node<>(2, dummy1);
        Node<Integer, Dummy> temp3 = new Node<>(1, dummy2);

        assertTrue(traversal.contains(temp1));
        assertTrue(traversal.contains(temp2));
        assertFalse(traversal.contains(temp3));

    }

    @Test
    public void testRemove(){
        BinarySearchTree<Integer, Dummy> tree = new BinarySearchTree<>();

        Dummy dummy1 = new Dummy(1);
        Dummy dummy2 = new Dummy(2);

        boolean result1 = tree.put(1, dummy1);
        boolean result2 = tree.put(2, dummy1);

        tree.remove(1);

        ArrayList<Node<Integer, Dummy>> traversal = tree.traverse();

        Node<Integer, Dummy> temp1 = new Node<>(1, dummy1);
        Node<Integer, Dummy> temp2 = new Node<>(2, dummy1);

        assertFalse(traversal.contains(temp1));
        assertTrue(traversal.contains(temp2));
    }

    @Test
    public void testBalanceRotateAlreadyBalanced(){
        BinarySearchTree<Integer, Object> tree = this.getTree(12, 2, 18, 1,3);

        assertTrue(tree.isBalanced());
        assertEquals(1, tree.getBalanceFactor());

        tree.balanceRotate();

        assertTrue(tree.isBalanced());
    }

    @Test
    public void testBalanceRotateSimple(){
        BinarySearchTree<Integer, Object> tree = this.getTree(12, 3, 18, 2, 1, 4);

        assertFalse(tree.isBalanced());
        assertEquals(2, tree.getBalanceFactor());

        tree.balanceRotate();

        assertTrue(tree.isBalanced());
    }

    @Test
    public void testBalanceRotateComplex(){
        BinarySearchTree<Integer, Object> tree = this.getTree(12, 2, 18, 1, 3, 4, 5);

        assertFalse(tree.isBalanced());
        assertEquals(3, tree.getBalanceFactor());

        tree.balanceRotate();

        assertTrue(tree.isBalanced());
    }

    @Test
    public void testTraverseRotate(){
        BinarySearchTree<Integer, Object> tree = this.getTree(12, 2, 18, 1, 3, 4, 5);

        assertFalse(tree.isBalanced());
        assertEquals(3, tree.getBalanceFactor());

        tree.balanceTraverse();

        assertTrue(tree.isBalanced());
    }

    private <T> BinarySearchTree<Integer, Object> getTree(int... keys){

        BinarySearchTree<Integer, Object> tree = new BinarySearchTree<>();

        for(int k : keys){
            tree.put(k, new Object());
        }

        return tree;
    }


    class Dummy{

        private int value;

        public Dummy(int value){
            this.value = value;
        }

        public Dummy(){
            this.value = 0;
        }

        @Override
        public boolean equals(Object o){
            if(o.getClass().equals(this.getClass())){
                return (this.value == ((Dummy)o).value);
            }
            return false;
        }
    }


}

package uk.ac.rgu.cm2116;

public class Node<K extends Comparable<K>, V> {

    private K key;
    private V value;

    private Node<K, V> left;
    private Node<K, V> right;

    public Node(K key, V value){
        this.key = key;
        this.value = value;

        this.left = null;
        this.right = null;
    }

    /**
     * Returns the in-order successor to this node
     * That is, the leftmost node of this node's right,
     * or null if this node has no right
     * @return
     */
    public Node<K, V> getSuccessor(){
        if(this.right == null){
            return null;
        }

        Node<K, V> temp = this.right;

        while(temp.left != null){
            temp = temp.left;
        }

        return temp;
    }

    /**
     * Method to get the balance factor the sub-tree rooted at this node
     * Returns a positive int if left-heavy; a negative int if right-heavy; 
     * 0 if balanced
     * @return
     */
    public int getBalanceFactor(){
        return (this.getLeftDepth() - this.getRightDepth());
    }

    /**
     * Gets the maximum depth of the sub-tree with this node at its root
     * Returns the greater of the left or right depths
     * @return
     */
    public int getDepth(){
        int lDepth = this.getLeftDepth();
        int rDepth = this.getRightDepth();

        if(lDepth > rDepth){
            return lDepth;
        }else{
            return rDepth;
        }
    }

    /**
     * Helper method to get the depth of the left sub-tree with this node at its root
     * @return
     */
    private int getLeftDepth(){
        if(this.left != null){
            return 1 + this.left.getDepth();
        }else{
            return 0;
        }
    }

    /**
     * Helper method to get the depth of the right sub-tree with this node at its root
     * @return
     */
    private int getRightDepth(){
        if(this.right != null){
            return 1 + this.right.getDepth();
        }else{
            return 0;
        }
    }

    /* Setters and getters */

    public void setLeft(Node<K, V> left){
        this.left = left;
    }

    public void setRight(Node<K, V> right){
        this.right = right;
    }

    public Node<K, V> getLeft(){
        return this.left;
    }

    public Node<K, V> getRight(){
        return this.right;
    }

    public K getKey(){
        return this.key;
    }

    public V getValue(){
        return this.value;
    }


    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o){
        if(o.getClass().equals(this.getClass())){
            Node<K, V> otherNode = (Node<K,V>)o;

            return (otherNode.key.equals(this.key) && otherNode.value.equals(this.value));
        }
        return false;
    }

}
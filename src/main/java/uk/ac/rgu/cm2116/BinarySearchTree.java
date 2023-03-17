package uk.ac.rgu.cm2116;

import java.util.ArrayList;

public class BinarySearchTree<K extends Comparable<K>, V> {

    private Node<K, V> root;
    private int size;

    private ArrayList<Node<K, V>> traversal; //holds the in-order traversal

    public BinarySearchTree(){
        this.root = null;
        this.size = 0;
        this.traversal = new ArrayList<>();
    }

    /**
     * Method to perform an in-order traversal of this BST
     * @return
     */
    public ArrayList<Node<K, V>> traverse(){
        this.traversal = new ArrayList<>();
        this.traverse(this.root);
        return this.traversal;
    }

    /**
     * Method to recursively traverse the (sub-)tree rooted at the given node
     * @param node
     */
    private void traverse(Node<K, V> node){
        if(node != null){
            this.traverse(node.getLeft());
            this.traversal.add(node);
            this.traverse(node.getRight());
        }
    }

    /**
     * Method to put the given value at the given key
     * Wrapper for the recursive put() method, passing the root
     * Returns true on success, or false if they key already exists
     * @param key
     * @param value
     * @return
     */
    public boolean put(K key, V value){
        if(this.root == null){
            this.root = new Node<>(key, value);
            return true;
        }
        return this.put(key, value, this.root);
    }

    /**
     * Recursively try to put the given key=value into the (sub-)tree rooted at the given node
     * @param key
     * @param value
     * @param node
     * @return
     */
    private boolean put(K key, V value, Node<K, V> node){

        if(node != null && key != null){
            if(key.equals(node.getKey())){
                return false;
            }

            if(key.compareTo(node.getKey()) < 0){
                if(node.getLeft() == null){
                    node.setLeft(new Node<>(key, value));
                    return true;
                }else{
                    return this.put(key, value, node.getLeft());
                }
            }else{
                if(node.getRight() == null){
                    node.setRight(new Node<>(key, value));
                    return true;
                }else{
                    return this.put(key, value, node.getRight());
                }
            }
        }
        return false;
    }

    /**
     * Method to remove the value at the given key
     * @param key
     * @return
     */
    public void remove(K key){
        if(this.root != null && key != null){
            this.root = this.remove(key, this.root);
        }
    }

    /**
     * Recursively tries to remove the given key from this tree,
     * or returns null if the key does not exist
     * @param node
     * @return
     */
    private Node<K, V> remove(K key, Node<K, V> node){
        if(node != null && key != null){
            if(node.getKey().equals(key)){
                if(node.getLeft() == null && node.getRight() == null){
                    return null;
                }else if(node.getLeft() != null && node.getRight() == null){
                    return node.getLeft();
                }else if(node.getLeft() == null && node.getRight() != null){
                    return node.getRight();
                }else{
                    Node<K, V> temp = node.getSuccessor();
        
                    this.remove(temp.getKey(), node);

                    temp.setLeft(node.getLeft());
                    temp.setRight(node.getRight());

                    return temp;
                }
            }else if(key.compareTo(node.getKey()) < 0 && node.getLeft() != null){
                node.setLeft(this.remove(key, node.getLeft()));
            }else if(key.compareTo(node.getKey()) > 0 && node.getRight() != null){
                node.setRight(this.remove(key, node.getRight()));
            }
        }
        return node;
    }

    /**
     * Get the value at the given key, or null if the key isn't in this tree
     * @param key
     * @return
     */
    public V get(K key){
        
        Node<K,V> temp = this.root;

        while(temp != null){
            if(temp.getKey().equals(key)){
                return temp.getValue();
            }

            if(key.compareTo(temp.getKey()) < 0){
                temp = temp.getLeft();
            }else{
                temp = temp.getRight();
            }
        }
        return null;
    }

    /**
     * Contains is a wrapper for get that returns true if we don't get null back
     * @param key
     * @return
     */
    public boolean contains(K key){
        return (this.get(key) != null);
    }

    /**
     * Returns the balance factor for this tree
     * @return
     */
    public int getBalanceFactor(){
        return (this.root != null) ? this.root.getBalanceFactor() : 0;
    }

    /**
     * Returns true if this tree is balanced
     * @return
     */
    public boolean isBalanced(){
        return (this.getBalanceFactor() <= 1 && this.getBalanceFactor() >= -1);
    }
       
    /* Rotation-based balancing methods below  */

    /**
     * Method to balance this BST through rotation
     */
    public void balanceRotate(){
        this.root = this.balanceRotate(this.root);
    }

    /**
     * Helper method to balance the (sub-)tree with the given node at its root, through rotation
     * @param node
     * @return
     */
    private Node<K, V> balanceRotate(Node<K, V> node){
        if(node.getBalanceFactor() > 1){
            return this.rotateRight(node);
        }else if(node.getBalanceFactor() < -1){
            return this.rotateLeft(node);
        }

        return node; //if we get this far, we don't need to do anything
    }

    /**
     * Helper method to rotate left about the given node
     * @param node
     * @return
     */
    private Node<K, V> rotateLeft(Node<K, V> node){
        if (node != null) {

            /* First, make sure our right is generally overall balanced */
            node.setRight(this.balanceRotate(node.getRight()));

            /*
             * Now check if we need to do a right-left rotation - if our right is left heavy
             */
            if (node.getRight() != null && node.getRight().getBalanceFactor() > 0) {
                node.setRight(this.rotateRight(node.getRight()));

                /*
                 * We can check here if our balance factor is fine; if it is, just return the
                 * node
                 */
                if (node.getBalanceFactor() >= -1 && node.getBalanceFactor() <= 1) {
                    return node;
                }
            }

            Node<K, V> temp = new Node<>(node.getKey(), node.getValue());

            temp.setLeft(node.getLeft());
            temp.setRight(node.getRight().getLeft());

            node.getRight().setLeft(temp);

            return node.getRight();
        }

        return node;
    }

    /**
     * Helper method to rotate right about the given node
     * @param node
     * @return
     */
    private Node<K, V> rotateRight(Node<K, V> node){
        if (node != null) {

            /* First, make sure our left is generally overall balanced */
            node.setLeft(this.balanceRotate(node.getLeft()));

            /*
             * Now check if we need to do a left-right rotation - if our left is right heavy
             */
            if (node.getLeft() != null && node.getLeft().getBalanceFactor() < 0) {
                node.setLeft(this.rotateLeft(node.getLeft()));

                /*
                 * We can check here if our balance factor is fine; if it is, just return the
                 * node
                 */
                if (node.getBalanceFactor() >= -1 && node.getBalanceFactor() <= 1) {
                    return node;
                }
            }

            Node<K, V> temp = new Node<>(node.getKey(), node.getValue());

            temp.setRight(node.getRight());
            temp.setLeft(node.getLeft().getRight());

            node.getLeft().setRight(temp);

            return node.getLeft();
        }

        return node;
    }

    /* Traverse-then-divide balancing methods below */

    /**
     * Method to balance this BST using traverse-then-divide
     * The initial call to the recursive methods accepts the full range of indices in
     * the traversal ArrayList, i.e. 0 to size() - 1
     */
    public void balanceTraverse(){
        this.traverse();
        this.root = this.balanceTraverse(0, this.traversal.size() - 1);
    }

    /**
     * Recursive method to balance this BST through dividing an in-order traversal
     * low and high represent the bounds of the (sub-)ArrayList that will be considered
     * at each call of the method
     * @param low
     * @param high
     * @return
     */
    private Node<K, V> balanceTraverse(int low, int high){
        if(low <= high){
            int mid = (int)Math.floor((low + high) / 2);

            Node<K, V> temp = this.traversal.get(mid);

            temp.setLeft(this.balanceTraverse(low, mid - 1));
            temp.setRight(this.balanceTraverse(mid + 1, high));

            return temp;
        }
        return null;
    }

    /**
     * Get the size of this BST
     * @return
     */
    public int getSize(){
        return this.size;
    }
    
}

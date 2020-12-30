/*
 * Implementation de la classe AvlTree
 * file AvlTree.java
 * authors Alexis Foulon et Florence Cloutier
 *
 * Ce programme contient les methodes qui ont pour but la construction de la classe
 * AvlTree et l'implementation des methodes de cette derniere.
 */

package tp3;

import java.lang.reflect.AnnotatedArrayType;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;

public class AvlTree<ValueType extends Comparable<? super ValueType> > {

    private BinaryNode<ValueType> root;

    public AvlTree() { }

    /**
     * Adds value to the tree and keeps it as a balanced AVL Tree
     * @param value value to add to the tree
     */
    public void insert(ValueType value) {
        if (root == null) {
            root = new BinaryNode<ValueType>(value, null);
        } else {
            insert(value, root);
        }
    }

    /**
     * Removes value from the tree and keeps it as a balanced AVL Tree
     * @param value value to add to the tree
     */
    public void remove(ValueType value){
        remove(value, root);
    }

    /**
     * Verifies if the tree contains value
     * @param value value to verify
     * @return if value already exists in the tree
     */
    public boolean contains(ValueType value) {
        return contains(value, root);
    }

    /**
     * Returns the max level contained in our root tree
     * @return Max level contained in our root tree
     */
    public int getHeight() {
        return BinaryNode.getHeight(root);
    }

    /**
     * Returns the minimal value contained in our root tree
     * @return Minimal value contained in our root tree
     */
    public ValueType findMin() {
        BinaryNode<ValueType> minimalNode = findMin(root);
        if (minimalNode == null) return null;
        return minimalNode.value;
    }

    /**
     * Returns all values contained in the root tree in ascending order
     * @return Values contained in the root tree in ascending order
     */
    public List<ValueType> infixOrder() {
        List<ValueType> items = new LinkedList<ValueType>();
        infixOrder(root, items);
        return items;
    }

    /**
     * Returns all values contained in the root tree in level order from top to bottom
     * @return Values contained in the root tree in level order from top to bottom
     */
    public List<ValueType> levelOrder(){
        List<ValueType> items = new LinkedList<ValueType>();

        ArrayDeque<BinaryNode<ValueType>> nodesToCheck = new ArrayDeque<BinaryNode<ValueType>>();

        if (root != null) {
            nodesToCheck.push(root);
            levelOrder(nodesToCheck, items);
        }

        return items;
    }

    /** TODO Worst case : O ( log n )
     *
     * Adds value to the tree and keeps it as a balanced AVL Tree
     * Should call balance only if insertion succeeds
     * AVL Trees do not contain duplicates
     *
     * @param value value to add to the tree
     * @param currentNode Node currently being accessed in this recursive method
     */
    private void insert (ValueType value, BinaryNode<ValueType> currentNode){
        if (value.compareTo(currentNode.value) > 0) {
            if (currentNode.right == null)
                currentNode.right = new BinaryNode<ValueType>(value, currentNode);
            else
                insert(value, currentNode.right);
            balance(currentNode);
        }
        else if (value.compareTo(currentNode.value) < 0) {
            if (currentNode.left == null)
                currentNode.left = new BinaryNode<ValueType>(value, currentNode);
            else
                insert(value, currentNode.left);
            balance(currentNode);
        }
    }

    /** TODO Worst case : O ( log n )
     *
     * Removes value from the tree and keeps it as a balanced AVL Tree
     * Should call balance only if removal succeeds
     *
     * @param value value to remove from the tree
     * @param currentNode Node currently being accessed in this recursive method
     */
    private void remove(ValueType value, BinaryNode<ValueType> currentNode) {
        if (currentNode == null) // The value is not there
            return;

        // Looking for the value
        if (value.compareTo(currentNode.value) > 0)
            remove(value, currentNode.right);
        else if (value.compareTo(currentNode.value) < 0)
            remove(value, currentNode.left);

        // Node to remove has 2 children
        else if (currentNode.left != null && currentNode.right != null) {
            currentNode.value = findMin(currentNode).value;
            remove(currentNode.value, currentNode.right);
            balance(currentNode);
        }
        // Node to remove has 1 child
        else if (currentNode.left != null || currentNode.right != null) {
            BinaryNode<ValueType> parent = currentNode.parent;
            currentNode = currentNode.left == null ? currentNode.right : currentNode.left;
            currentNode.parent = parent;
            balance(currentNode);
        }
        // Node to remove has no children
        else {
            BinaryNode<ValueType> parent = currentNode.parent;
            if (currentNode == root)
                root = null;
            else if (parent.left == currentNode)
                parent.left = null;
            else
                parent.right = null;
            balance(parent);
        }
    }

    /** TODO Worst case : O( log n )
     *
     * Recursively balances the whole tree
     * @param subTree SubTree currently being accessed to verify if it respects the AVL balancing rule
     */
    private void balance(BinaryNode<ValueType> subTree) {
        if (subTree == null)
            return;

        if (BinaryNode.getHeight(subTree.left) - BinaryNode.getHeight(subTree.right) > 1) {
            if (BinaryNode.getHeight(subTree.left.left) >= BinaryNode.getHeight(subTree.left.right))
                rotateLeft(subTree);
            else
                doubleRotateOnLeftChild(subTree);
        }
        else if (BinaryNode.getHeight(subTree.right) - BinaryNode.getHeight(subTree.left) > 1) {
            if (BinaryNode.getHeight(subTree.right.right) >= BinaryNode.getHeight(subTree.right.left))
                rotateRight(subTree);
            else
                doubleRotateOnRightChild(subTree);
        }

        subTree.height = Math.max(BinaryNode.getHeight(subTree.left), BinaryNode.getHeight(subTree.right)) + 1;

        balance(subTree.parent);
    }

    /** TODO Worst case : O ( 1 )
     *
     * Single rotation to the left child, AVR Algorithm
     * @param node1 Node to become child of its left child
     */
    private void rotateLeft(BinaryNode<ValueType> node1) {
        
        BinaryNode<ValueType> node2 = node1.left;

        // Insert node2 in the tree at the position of node 1
        node2.parent = node1.parent;
        if (node2.parent == null)
            root = node2;
        else if (node2.value.compareTo(node2.parent.value) > 0)
            node2.parent.right = node2;
        else
            node2.parent.left = node2;


        // Rotation
        node1.left = node2.right;
        if (node1.left != null)
            node1.left.parent = node1;

        node2.right = node1;
        node1.parent = node2;


        // New Height
        node1.height = Math.max(BinaryNode.getHeight(node1.left), BinaryNode.getHeight(node1.right)) + 1;
        node2.height = Math.max(BinaryNode.getHeight(node2.left), BinaryNode.getHeight(node2.right)) + 1;
    }

    /** TODO Worst case : O ( 1 )
     *
     * Single rotation to the right, AVR Algorithm
     * @param node1 Node to become child of its right child
     */
    private void rotateRight(BinaryNode<ValueType> node1){
        BinaryNode<ValueType> node2 = node1.right;

        // Insert node2 in the tree at the position of node 1
        node2.parent = node1.parent;
        if(node2.parent == null)
            root = node2;
        else if (node2.value.compareTo(node2.parent.value) > 0)
            node2.parent.right = node2;
        else
            node2.parent.left = node2;

        // Rotation
        node1.right = node2.left;
        if (node1.right != null)
            node1.right.parent= node1;

        node2.left = node1;
        node1.parent = node2;

        // New Height
        node1.height = Math.max(BinaryNode.getHeight(node1.left), BinaryNode.getHeight(node1.right)) + 1;
        node2.height = Math.max(BinaryNode.getHeight(node2.left), BinaryNode.getHeight(node2.right)) + 1;
    }

    /** TODO Worst case : O ( 1 )
     *
     * Double rotation on left child, AVR Algorithm
     * @param node Node to become child of the right child of its left child
     */
    private void doubleRotateOnLeftChild(BinaryNode<ValueType> node){
        rotateRight(node.left);
        rotateLeft(node);
    }

    /** TODO Worst case : O ( 1 )
     *
     * Double rotation on right child, AVR Algorithm
     * @param node Node to become child of the left child of its right child
     */
    private void doubleRotateOnRightChild(BinaryNode<ValueType> node){
        rotateLeft(node.right);
        rotateRight(node);
    }

    /** TODO Worst case : O ( log n )
     *
     * Verifies if the root tree contains value
     * @param value value to verify
     * @param currentNode Node currently being accessed in this recursive method
     * @return if value already exists in the root tree
     */
    private boolean contains(ValueType value, BinaryNode<ValueType> currentNode) {
        if (currentNode == null)
            return false;

        if (value.compareTo(currentNode.value) > 0)
            return contains(value, currentNode.right);
        else if (value.compareTo(currentNode.value) < 0)
            return contains(value, currentNode.left);

        return true;
    }

    /** TODO O( log n )
     *
     * Returns the node which has the minimal value contained in our root tree
     * @return Node which has the minimal value contained in our root tree
     */
    private BinaryNode<ValueType> findMin(BinaryNode<ValueType> currentNode) {
        if (currentNode == null)
            return null;
        else if (currentNode.left == null)
            return currentNode;

        return findMin(currentNode.left);
    }

    /** TODO O( n )
     *
     * Builds items which should contain all values within the root tree in ascending order
     * @param currentNode Node currently being accessed in this recursive method
     * @param items List being modified to contain all values in the root tree in ascending order
     */
    private void infixOrder(BinaryNode<ValueType> currentNode, List<ValueType> items){
        if (currentNode == null)
            return;

        infixOrder(currentNode.left, items);
        items.add(currentNode.value);
        infixOrder(currentNode.right, items);
    }

    /** TODO O( n )
     * Builds items which should contain all values within the root tree in level order from top to bottom
     * @param nodesToCheck Queue for non-recursive algorithm
     * @param items List being modified to contain all values in the root tree in level order from top to bottom
     */
    private void levelOrder(ArrayDeque<BinaryNode<ValueType>> nodesToCheck, List<ValueType> items) {
        while (!nodesToCheck.isEmpty()) {
            BinaryNode<ValueType> currentNode = nodesToCheck.poll(); // Node on top of deque
            items.add(currentNode.value);
            if (currentNode.left != null)
                nodesToCheck.add(currentNode.left);
            if (currentNode.right != null)
                nodesToCheck.add(currentNode.right);
        }
    }
    
    static class BinaryNode<ValueType> {
        ValueType value;

        BinaryNode<ValueType> parent; // Pointer to the node containing this node

        BinaryNode<ValueType> left = null; // Pointer to the node on the left which should contain a value below this.value
        BinaryNode<ValueType> right = null; // Pointer to the node on the right which should contain a value above this.value

        int height = 0;

        // Null-safe height accessor
        // Raw use of parameterized class is justified because we do not care about the values in it, only the height
        static public int getHeight(BinaryNode node) {
            return node != null ? node.height : -1;
        }

        BinaryNode(ValueType value, BinaryNode<ValueType> parent)
        {
            this.value = value;
            this.parent = parent;
        }
    }
}
package assignment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

import javax.swing.text.DefaultStyledDocument.ElementSpec;

public class TreapMap<K extends Comparable<K>, V> implements Treap<K,V>{
    Node<K,V> root;
    Stack<Node<K,V>> stack = new Stack<Node<K,V>>();
    String tString = "";

    public TreapMap(){
        root = null;
    }

    public TreapMap(Node<K,V> newNode){
        root = newNode;
    }

    @Override
    public V lookup(K key) {
        if(key == null){
            System.err.println("Null keys do not exist in this tree and will return a null value -- lookup");
            return null;
        }
        Node<K,V> found = findNode(key, false);

        if(found.getKey().compareTo(key) == 0){
            return found.getValue();
        }

        return null;
    }

    @Override
    public void insert(K key, V value) {
        if(key == null || value == null){
            System.err.println("Null keys and values are not accepted and thus will not be inserted into the tree  -- insert");
            return;
        }
        
        //generate priority value
        int newPriority = generatePriority();

        //create new node with key, val and priority value
        Node<K,V> newNode = new Node<K,V>(newPriority, key, value);

        //finding the parent node if key is not already in the treap
        Node<K,V> parentNode = findNode(key, false);

        if(parentNode == null){
            root = newNode;
            return;
        }


        //dealing with duplicates
        if(parentNode.getKey().compareTo(key) == 0){
            parentNode.setValue(value);
            return;
        }

        //adding in newNode as a leaf based on bst property
        if(key.compareTo(parentNode.getKey()) < 0){
            parentNode.setLeft(newNode);
        }
        else{
            parentNode.setRight(newNode);
        }
        
        newNode.setParent(parentNode);
        //need to fix heap property
        while((newNode.getParent() != null) && (newNode.getPriorityValue() > newNode.getParent().getPriorityValue())){
            if(newNode.getKey().compareTo(stack.peek().getKey()) < 0){
                rotateRight(newNode);
            }
            else{
                rotateLeft(newNode);
            }
        }
        
    }

    @Override
    public V remove(K key) {
        if(key == null){
            System.err.println("Null keys are not accepted and will return a null value -- remove");
            return null;
        }
        boolean childLeft = true;
        Node<K, V> current;
        Node<K, V> parentNode;
        int leftPriVal;
        int rightPriVal;

        do{
            leftPriVal = -1;
            rightPriVal = -1;
            current = findNode(key, false);

            if(key.compareTo(current.getKey()) != 0){
                return null;
            }

            if(current.getLeft() != null){
                leftPriVal = current.getLeft().getPriorityValue();
            }

            if(current.getRight() != null){
                rightPriVal = current.getRight().getPriorityValue();
            }

            if(rightPriVal == -1 && leftPriVal == -1){
                break;
            }
        
            else if(rightPriVal >= leftPriVal){
                rotateLeft(current.getRight());
                childLeft = true;
            }

            else{
                rotateRight(current.getLeft());
                childLeft = false;

            }

        }  while((current.getLeft()!= null) || (current.getRight()!= null));

        parentNode = findNode(current.getKey(), true);

        if((rightPriVal == -1) && (leftPriVal == -1)){
            if(current.getKey().compareTo(parentNode.getKey()) < 0){
                parentNode.setLeft(null);
            }

            else{
                parentNode.setRight(null);
            }
            
            return current.getValue();
        }

        if(childLeft == true){
            parentNode.setLeft(null);
        }

        else{
            parentNode.setRight(null);
        }

        return current.getValue();

    } 
        

    @Override
    public Treap<K,V>[] split(K key) {
        if(key == null){
            System.err.println("Null keys are not accepted and will return a null value -- split");
            return null;
        }
        V value = root.getValue();

        Node<K,V> checkNode = findNode(key, false);

        if(checkNode.getKey().compareTo(key) == 0){
            remove(checkNode.getKey());
        }

        else{
            checkNode = null;
        }


        fixMaxPriorities(root);

        insert(key, value, MAX_PRIORITY);

        Node<K,V> rootNode = findNode(key, false);
        Node<K,V> newLeftRoot = rootNode.getLeft();
        newLeftRoot.setParent(null);
        Node<K,V> newRightRoot = rootNode.getRight();
        newRightRoot.setParent(null);

        
        if(checkNode != null){
            rootNode.setValue(checkNode.getValue());
            newRightRoot = rootNode;
        }
        else{
            rootNode.setRight(null);
        }

        rootNode.setLeft(null);
        
        TreapMap[] treapArray = new TreapMap[2];
        
        TreapMap leftTreap = new TreapMap<>(newLeftRoot);
        treapArray[0] = leftTreap;

        TreapMap rightTreap = new TreapMap<>(newRightRoot);
        
        treapArray[1] = rightTreap;


        return treapArray;
    }

    @Override
    public void join(Treap<K, V> t) {
        if(t == null){
            System.err.println("the treap object passed is null and thus cannot be combined with the current treap");
            return;
        }
        Node<K,V> maxNode;

        if(!(t instanceof TreapMap<?, ?>)){
            System.err.println("the treap object passed is not a treapmap.");
            return;
        }

        //creates arbitrary new root
        Node<K,V> newRoot = new Node<K,V>();
        newRoot.setPriorityValue(MAX_PRIORITY);
        
        Node<K,V> troot = ((TreapMap<K,V>) t).root;
        
        if(root.getKey().compareTo(troot.getKey()) > 0){
            maxNode = ((TreapMap<K,V>) t).getMax();
            ((TreapMap<K,V>) t).remove(maxNode.getKey());

            newRoot.setLeft(troot);
            newRoot.setRight(root);

        }
        else{
            maxNode = getMax();
            remove(maxNode.getKey());

            newRoot.setLeft(root);
            newRoot.setRight(troot);
        }

        newRoot.setKey(maxNode.getKey());
        newRoot.setValue(maxNode.getValue());

        root = newRoot;

     
    }

    public class TreapMapIterator implements Iterator<K>{
        Stack<Node<K,V>> iterStack;
        Node<K,V> current;

        
        public TreapMapIterator(TreapMap<K,V> treapy){
            iterStack = new Stack<Node<K,V>>();
            current = treapy.root;
        }

        @Override
        public boolean hasNext() {
            if(current != null || !(iterStack.isEmpty())){
                return true;
            }
            return false;
        }

        @Override
        public K next() {
            while(hasNext()){
                while(current != null){
                    iterStack.push(current);
                    current = current.getLeft();
                }

                current = stack.pop();
                
                K returnKey = current.getKey();

                current = current.getRight();

                return returnKey;
            }
            return null;
        }
        
    }

    @Override
    public void meld(Treap t) throws UnsupportedOperationException {
        
    }

    @Override
    public void difference(Treap t) throws UnsupportedOperationException {        
    }

    @Override
    public Iterator<K> iterator() {
       Iterator<K> iter = new TreapMapIterator(this);
       return iter;
    }

    @Override
    public double balanceFactor() throws UnsupportedOperationException {
        return 0;
    }

    @Override
    public String toString(){
        tString = "";
        int spaceCount = 0;

        preOrder(root, spaceCount);
        
        return tString;
    }

    //overloaded insert
    public void insert(K key, V value, int pri) {
        
        //generate priority value
        int newPriority = pri;

        //create new node with key, val and priority value
        Node<K,V> newNode = new Node<K,V>(newPriority, key, value);

        //assuming no duplicates at the moment --- becomes problem when there are duplicates for split
        Node<K,V> parentNode = findNode(key, true);

        if(parentNode == null){
            root = newNode;
            return;
        }

        //adding in newNode as a leaf based on bst property
        if(key.compareTo(parentNode.getKey()) < 0){
            parentNode.setLeft(newNode);
        }
        else{
            parentNode.setRight(newNode);
        }
        
        newNode.setParent(parentNode);
        //fixing heap property
        while((newNode.getParent() != null) && (newNode.getPriorityValue() > newNode.getParent().getPriorityValue())){
            if(newNode.getKey().compareTo(newNode.getParent().getKey()) < 0){
                rotateRight(newNode);
            }
            else{
                rotateLeft(newNode);
            }
        }
        
    }


    public void printTreeInOrder(Node<K,V> r){

        if(r == null){
            return;
        }

        printTreeInOrder(r.getLeft());

        r.printNode();

        printTreeInOrder(r.getRight());
    }

    //returns node with same key or parent of node of with same key
    //if key is not in tree, returns the parent node (regardless of parent being true or false)
    public Node<K,V> findNode(K key, boolean parent){

        if(key == null)
        
        if(root == null){
            return null;
        }

        Node<K,V> current = root;
        Node<K,V> parentNode = null;

        stack.clear();

        stack.push(root);


        while(current != null){

            //if same key is found, the node is returned
            if(key.compareTo(current.getKey()) == 0){
                //System.out.println("current key found");
                if(parent == true){
                    if(current.equals(root)){
                        return null;
                    }

                    return parentNode;
                } 
                    
                return current;
            }

            //only add current if it is not the root
            if(!current.equals(root)){
                stack.push(current);
            }

            //if key is less than current key
            if((key.compareTo(current.getKey()) < 0)){
               
                if(current.getLeft() == null){
                    return current;
                }
                parentNode = current;
                current = current.getLeft();
            }

            //if key is greater than current key
            else{

                if(current.getRight() == null){
                    return current;
                }
                parentNode = current;
                current = current.getRight();
            }

        }        
        
        return null;

    }

    //randomly create priority value
    public int generatePriority(){
        Random rand = new Random();
        int intRandom = rand.nextInt(MAX_PRIORITY) + 1;
        
        return intRandom;
    }

    // public void rotateLeft(Node<K,V> center){

    //     Node<K,V> parent = stack.pop();
        
    //     //move the center node's left child, replacing itself as its parent's right child
    //     if(center.getLeft()!= null){
    //         parent.setRight(center.getLeft());
    //     }

    //     //if the center does not have a left, still replacing the parents right child
    //     else{
    //         parent.setRight(null);
    //     }

    //     center.setLeft(parent);

    //     //setting the replacig parent/grandparent/grandparent node's left with center
    //     if(!stack.isEmpty()){
    //         if((stack.peek().getLeft() != null) && (stack.peek().getLeft().getKey().compareTo(parent.getKey()) == 0)){
    //             stack.peek().setLeft(center);
    //         }
    
    //         else{
    //             stack.peek().setRight(center);
    //         }
            
    //     }

    //     //if the stack is empty and we are adding a node, it must be the root
    //     else{
    //         root = center;
    //     }
    // }

    public void rotateLeft(Node<K,V> center){

        Node<K,V> parent = center.getParent();
        
        //move the center node's left child, replacing itself as its parent's right child
        if(center.getLeft()!= null){
            parent.setRight(center.getLeft());
            center.getLeft().setParent(parent);
        }

        //if the center does not have a left, still replacing the parents right child
        else{
            parent.setRight(null);
        }

        center.setLeft(parent);
        Node<K,V> grandparent = parent.getParent();

        //setting the replacig parent/grandparent/grandparent node's left with center
        if(grandparent != null){
            if((grandparent.getLeft() != null) && (grandparent.getLeft().getKey().compareTo(parent.getKey()) == 0)){
                grandparent.setLeft(center);
            }
    
            else{
                grandparent.setRight(center);
            }
            
        }

        //if the stack is empty and we are adding a node, it must be the root
        else{
            root = center;
        }

        center.setParent(grandparent);
        parent.setParent(center);
    }
        
    // public void rotateRight(Node<K,V> center){
    //     Node<K,V> parent = stack.pop();
        
    //     if(center.getRight()!= null){
    //         parent.setLeft(center.getRight());
    //     }

    //     else{
    //         parent.setLeft(null);
    //     }
        
    //     center.setRight(parent);
        
    //     // && (stack.peek().getRight() != null)
    //     if((!stack.isEmpty())){
    //         if((stack.peek().getLeft() != null) && (stack.peek().getLeft().getKey().compareTo(parent.getKey()) == 0)){
    //             stack.peek().setLeft(center);
    //         }
    
    //         else{
    //             stack.peek().setRight(center);
    //         }
    //     }

    //     else{
    //         root = center;
    //     }
    // }

    public  void rotateRight(Node<K,V> center){
        Node<K,V> parent = center.getParent();

        if(center.getRight()!= null){
            parent.setLeft(center.getRight());
            center.getRight().setParent(parent);
        }

        else{
            parent.setLeft(null);
        }
        
        center.setRight(parent);

        Node<K,V> grandparent = parent.getParent();
        if(grandparent != null){
            if((grandparent.getLeft() != null) && (grandparent.getLeft().getKey().compareTo(parent.getKey()) == 0)){
                grandparent.setLeft(center);
                
            }

            else{
                grandparent.setRight(center);
            }

        }

        else{
            root = center;
        }

        center.setParent(grandparent);
        parent.setParent(center);

    }

    public void preOrder(Node<K,V> r, int numOfSpaces){      
    
        if(r == null){
            numOfSpaces--;
            return;
        }

        for(int i = 0; i < numOfSpaces; i++){
            tString = tString + "\t";
        }  

        tString = tString + "[" + r.getPriorityValue() + "] <" + r.getKey() + "," + r.getValue() + "> \n";
        //System.out.println("[" + r.getPriorityValue() + "] <" + r.getKey() + "," + r.getValue() + "> \n");
        numOfSpaces++;

        preOrder(r.getLeft(), numOfSpaces);

        preOrder(r.getRight(), numOfSpaces);
    }

    public Node<K,V> getMax(){
        Node<K,V> current = root;
        while(current.getRight()!= null){
            current = current.getRight();
        }

        return current;
    }

    public void fixMaxPriorities(Node<K,V> r){
        if(r == null){
            return;
        }

        fixMaxPriorities(r.getLeft());

        if(r.getPriorityValue() == MAX_PRIORITY){
            r.setPriorityValue(MAX_PRIORITY -1);
        }

        fixMaxPriorities(r.getRight());
        
    }



}

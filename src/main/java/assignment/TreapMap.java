package assignment;

import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

public class TreapMap<K extends Comparable<K>, V> implements Treap<K,V>{
    Node<K,V> root;
    Stack<Node<K,V>> stack = new Stack<Node<K,V>>();

    public TreapMap(){
        root = null;
    }

    public TreapMap(Node<K,V> newNode){
        root = newNode;
    }

    @Override
    public V lookup(K key) {
        Node<K,V> found = findNode(key);

        if(found.getKey().compareTo(key) == 0){
            return found.getValue();
        }

        return null;
    }

    @Override
    public void insert(K key, V value) {
        
        //generate priority value
        int newPriority = generatePriority();

        //create new node with key, val and priority value
        Node<K,V> newNode = new Node<K,V>(newPriority, key, value);

        //finding the parent node
        Node<K,V> parentNode = findNode(key);


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
        

        //need to fix heap property
        while((!stack.isEmpty()) && (newNode.getPriorityValue() > stack.peek().getPriorityValue())){
            if(newNode.getKey().compareTo(stack.peek().getKey()) < 0){
                rotateRight(newNode);
            }
            else{
                rotateLeft(newNode);
            }
        }
        
    }

    public void insert(K key, V value, int pri) {
        
        //generate priority value
        int newPriority = pri;

        //create new node with key, val and priority value
        Node<K,V> newNode = new Node<K,V>(newPriority, key, value);

        //assuming no duplicates at the moment
        Node<K,V> parentNode = findNode(key);

        if(parentNode == null){
            root = newNode;
            return;
        }

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
        
        //need to fix heap property
        while((!stack.isEmpty()) && (newNode.getPriorityValue() > stack.peek().getPriorityValue())){
            if(newNode.getKey().compareTo(stack.peek().getKey()) < 0){
                rotateRight(newNode);
            }
            else{
                rotateLeft(newNode);
            }
        }
        
    }

    @Override
    public Object remove(Comparable key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Treap[] split(Comparable key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void join(Treap t) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void meld(Treap t) throws UnsupportedOperationException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void difference(Treap t) throws UnsupportedOperationException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Iterator iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double balanceFactor() throws UnsupportedOperationException {
        // TODO Auto-generated method stub
        return 0;
    }

    public void printTreeInOrder(Node<K,V> r){
        if(r == null){
            return;
        }

        printTreeInOrder(r.getLeft());

        r.printNode();

        printTreeInOrder(r.getRight());
    }

    //returns node with same key 
    //if key is not in tree, returns the parent node
    public Node<K,V> findNode(K key){
        
        if(root == null){
            return null;
        }

        Node<K,V> current = root;
        stack.clear();

        stack.push(root);


        while(true){

            //if same key is found, the node is returned
            if(key.compareTo(current.getKey()) == 0){
                System.out.println("current key found");
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
                current = current.getLeft();
            }

            //if key is greater than current key
            else{

                if(current.getRight() == null){
                    return current;
                }
                current = current.getRight();
            }
        }        
    }

    //randomly create priority value
    public int generatePriority(){
        Random rand = new Random();
        int intRandom = rand.nextInt(MAX_PRIORITY) + 1;
        
        return intRandom;
    }

    public void rotateLeft(Node<K,V> center){
        Node<K,V> parent = stack.pop();
        
        //move the center node's left child, replacing itself as its parent's right child
        if(center.getLeft()!= null){
            parent.setRight(center.getLeft());
        }

        //if the center does not have a left, still replacing the parents right child
        else{
            parent.setRight(null);
        }

        center.setLeft(parent);

        //setting the replacig parent/grandparent/grandparent node's left with center
        if(!stack.isEmpty()){
            if((stack.peek().getLeft() != null) && (stack.peek().getLeft().getKey().compareTo(parent.getKey()) == 0)){
                stack.peek().setLeft(center);
            }
    
            else{
                stack.peek().setRight(center);
            }
            
        }

        //if the stack is empty and we are adding a node, it must be the root
        else{
            root = center;
        }
        

    }
        
    
    public void rotateRight(Node<K,V> center){
        Node<K,V> parent = stack.pop();
        
        if(center.getRight()!= null){
            parent.setLeft(center.getRight());
        }

        else{
            parent.setLeft(null);
        }
        
        center.setRight(parent);
        
        if((!stack.isEmpty()) && (stack.peek().getRight() != null)){
            if((stack.peek().getLeft() != null) && (stack.peek().getLeft().getKey().compareTo(parent.getKey()) == 0)){
                stack.peek().setLeft(center);
            }
    
            else{
                stack.peek().setRight(center);
            }

        }

        else{
            root = center;
        }

        

    }
}

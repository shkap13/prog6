package assignment;

import java.util.Iterator;
import java.util.Random;

public class TreapMap<K extends Comparable<K>, V> implements Treap<K,V>{
    Node<K,V> root;

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

        //assuming no duplicates at the moment
        Node<K,V> parentNode = findNode(key);

        if(key.compareTo(parentNode.getKey()) < 0){

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

    public void printTreeInOrder(Node<K,V> root){
        if(root.getLeft() != null){
            printTreeInOrder(root.getLeft());
        }

        System.out.println("priority value: " + root.getPriorityValue() + ", key is: " + root.getKey() + ", value is: " + root.getValue());

        if(root.getRight() != null){
            printTreeInOrder(root.getRight());
        }
    }

    //returns node with same key 
    //if key is not in tree, returns the parent node
    public Node<K,V> findNode(K key){
        Node<K,V> current = root;

        while(true){

            //if same key is found, the node is returned
            if(key.compareTo(current.getKey()) == 0){
                return current;
            }

            //if key is less than current key
            else if((key.compareTo(current.getKey()) < 0)){
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

    public int generatePriority(){
        Random rand = new Random();
        int intRandom = rand.nextInt(MAX_PRIORITY);
        
        return intRandom;
    }
    
}

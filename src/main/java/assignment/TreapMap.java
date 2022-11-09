package assignment;

import java.lang.reflect.Array;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

import javax.swing.text.DefaultStyledDocument.ElementSpec;

public class TreapMap<K extends Comparable<K>, V> implements Treap<K,V>{
    Node<K,V> root;

    //string updated for toString
    String tString = "";

    //boolean that determines whether concurrent modification should be thrown for iterator
    int modified;

    public TreapMap(){
        root = null;
    }

    public TreapMap(Node<K,V> newNode){
        root = newNode;
    }

    @Override
    public V lookup(K key) {

        //input validation
        if(key == null){
            System.err.println("Null keys do not exist in this tree and will return a null value -- lookup");
            return null;
        }

        //when parent false, findNode returns either the node with that key OR
        //if key does not exist, returns the node that would be the parent if the key existed 
        Node<K,V> found = findNode(key, false);

        //makes sure that key does exist and then returns it
        if(found.getKey().compareTo(key) == 0){
            return found.getValue();
        }

        //if key does not exist
        return null;
    }

    @Override
    public void insert(K key, V value) {

        //input validation
        if(key == null || value == null){
            System.err.println("Null keys and values are not accepted and thus will not be inserted into the tree  -- insert");
            return;
        }
        
        modified++;
        
        int newPriority = generatePriority();

        //create new node with key, val and priority value
        Node<K,V> newNode = new Node<K,V>(newPriority, key, value);

        //finding the parent node (but if key is already in the treap, will return existing key node)
        Node<K,V> parentNode = findNode(key, false);

        //if parentNode is null, newNode is the first node inserted and now the root
        if(parentNode == null){
            root = newNode;
            return;
        }

        //checking if the key exists and if it does, updating the value 
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
        
        //reheapifying by rotating
        while((newNode.getParent() != null) && (newNode.getPriorityValue() > newNode.getParent().getPriorityValue())){
            if(newNode.getKey().compareTo(newNode.getParent().getKey()) < 0){
                rotateRight(newNode);
            }
            else{
                rotateLeft(newNode);
            }
        }
    }

    //need to check the remove -- failling with null pointer exceptions
    @Override
    public V remove(K key) {

        //input validation
        if(key == null){
            return null;
        }

        modified++;
        boolean childLeft = true;
        Node<K, V> current;
        int leftPriVal;
        int rightPriVal;

        do{
            //default priority values -- priority values if there are no children
            leftPriVal = -1;
            rightPriVal = -1;

            current = findNode(key, false);

            //in case key to remove does not exist in the treap
            if(key.compareTo(current.getKey()) != 0){
                return null;
            }

            //determining children priority values
            if(current.getLeft() != null){
                leftPriVal = current.getLeft().getPriorityValue();
            }

            if(current.getRight() != null){
                rightPriVal = current.getRight().getPriorityValue();
            }

            //if leaf node to begin with
            if(rightPriVal == -1 && leftPriVal == -1){

                //in case current is equal to the root
                if(current.getKey().compareTo(root.getKey()) == 0){
                    root = null;
                    return current.getValue();
                }
                //if left child
                else if(current.getKey().compareTo(current.getParent().getKey()) < 0){
                    childLeft = true;
                }
                //if right child
                else{
                    childLeft = false;
                }

                break;
            }
            //if right child has higher priority
            else if(rightPriVal >= leftPriVal){
                rotateLeft(current.getRight());
                childLeft = true;
            }
            //if left child has higher priority
            else{
                rotateRight(current.getLeft());
                childLeft = false;
            }

            //condition that forces rotations until node that needs to be removed has become a leaf
        }  while((current.getLeft()!= null) || (current.getRight()!= null));

        //if current is a left child
        if(childLeft == true){
            current.getParent().setLeft(null);
        }
        //if current is a right child
        else{
            current.getParent().setRight(null);
        }

        return current.getValue();
    } 
        

    //null pointer exception -- check for that
    @Override
    public Treap<K,V>[] split(K key) {

        //creates array for new treaps (array intended for generics)
        Treap<K,V>[] treapArray = (Treap<K,V>[]) Array.newInstance(this.getClass(), 2);

        //input validation
        if(key == null){
            TreapMap<K,V> empty = new TreapMap<K,V>();
            treapArray[0] = empty;
            treapArray[1] = empty;

            return treapArray;
        }

        modified++;
        V value = root.getValue();

        //finds node if key to split on already exists
        Node<K,V> checkNode = findNode(key, false);

        //if it does exist, removing that node
        if(checkNode.getKey().compareTo(key) == 0){
            remove(checkNode.getKey());
        }
        else{
            checkNode = null;
        }

        //if there are any nodes in the the tree with max priority, making them 1 less than max priority 
        //to ensure that node being inserted has highest priority
        fixMaxPriorities(root);

        insert(key, value, MAX_PRIORITY);

        //finding root of tree before split
        Node<K,V> rootNode = findNode(key, false);

        //find root of left subtree and splitting it off
        Node<K,V> newLeftRoot = rootNode.getLeft();
        if(newLeftRoot != null){
            newLeftRoot.setParent(null);
        }

        //finding root of right subtree and splitting it off
        Node<K,V> newRightRoot = rootNode.getRight();
        if(newRightRoot != null){
            newRightRoot.setParent(null);
        }

        //if the key of the inserted node exists
        if(checkNode != null){
            //changing the value of the rootNode 
            rootNode.setValue(checkNode.getValue());
            //and making it the root of the right subtree
            newRightRoot = rootNode;
        }
        else{
            rootNode.setRight(null);
        }

        rootNode.setLeft(null);
        
        //makes treaps with roots and assigns them into array
        if(newLeftRoot != null){
            TreapMap<K,V> leftTreap = new TreapMap<>(newLeftRoot);
            treapArray[0] = leftTreap;
        }
        else{
            TreapMap<K,V> leftTreap = new TreapMap<>();
            treapArray[0] = leftTreap;
        }

        if(newRightRoot != null){
            TreapMap<K,V> rightTreap = new TreapMap<>(newRightRoot);
            treapArray[1] = rightTreap;
    
        }
        else{
            TreapMap<K,V> rightTreap = new TreapMap<>();
            treapArray[1] = rightTreap;
        }
       
        return treapArray;
    }

    @Override
    public void join(Treap<K, V> t) {
        //input validation
        if(t == null){
            System.err.println("the treap object passed is null and thus cannot be combined with the current treap");
            return;
        }

        if(!(t instanceof TreapMap<?, ?>)){
            System.err.println("the treap object passed is not a treapmap.");
            return;
        }

        modified++;
        Node<K,V> maxNode;

        //creates arbitrary new root
        Node<K,V> newRoot = new Node<K,V>();
        newRoot.setPriorityValue(MAX_PRIORITY);
        
        //root of treap t that was passed in
        Node<K,V> troot = ((TreapMap<K,V>) t).root;
        
        //if this treapMap's keys are greater than treap t's
        if(root.getKey().compareTo(troot.getKey()) > 0){
            //takes maximum node of left subtree and removes it
            maxNode = ((TreapMap<K,V>) t).getMax();
            ((TreapMap<K,V>) t).remove(maxNode.getKey());

            //links this treapMap and treap t to newRoot
            newRoot.setLeft(troot);
            newRoot.setRight(root);
        }
        else{
            maxNode = getMax();
            remove(maxNode.getKey());

            newRoot.setLeft(root);
            newRoot.setRight(troot);
        }

        //assigns root to have keys and values of the previously removed maxNode
        newRoot.setKey(maxNode.getKey());
        newRoot.setValue(maxNode.getValue());

        //updates this treapMap's root so the joined treapMap is now this treapMap
        root = newRoot;
    }

    public class TreapMapIterator implements Iterator<K>{
        int mapmodified;
        Stack<Node<K,V>> iterStack;
        Node<K,V> current;

        
        public TreapMapIterator(TreapMap<K,V> treapy){
            iterStack = new Stack<Node<K,V>>();
            current = treapy.root;
            mapmodified = modified;
        }

        @Override
        public boolean hasNext() {
            //checks whether treapMap has been modified since creation of this iterator
            if(modified != mapmodified){
                throw new ConcurrentModificationException();
            }

            if(current != null || !(iterStack.isEmpty())){
                return true;
            }

            return false;
        }

        //in order traversal of treapMap that returns a single node at a time
        @Override
        public K next() {
            //checks whether treapMap has been modified since creation of this iterator
            if(modified != mapmodified){
                throw new ConcurrentModificationException();
            }

            while(current != null || !(iterStack.isEmpty())){
                while(current != null){
                    iterStack.push(current);
                    current = current.getLeft();
                }

                current = iterStack.pop();

                K returnKey = current.getKey();

                current = current.getRight();

                return returnKey;
            }
            return null;
        }
        
    }

    @Override
    public void meld(Treap<K,V> t) throws UnsupportedOperationException {
        
    }

    @Override
    public void difference(Treap<K,V> t) throws UnsupportedOperationException {        
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
        
        //creates fresh tString
        tString = "";
        int spaceCount = 0;

        preOrder(root, spaceCount);
        
        return tString;
    }

    //overloaded insert -- otherwise same as normal insert
    public void insert(K key, V value, int pri) {
        int newPriority = pri;
        Node<K,V> newNode = new Node<K,V>(newPriority, key, value);
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

    //returns node with same key or parent of node of with same key
    //if key is not in tree, returns the parent node (regardless of parent being true or false)
    public Node<K,V> findNode(K key, boolean parent){
        
        if(root == null){
            return null;
        }

        Node<K,V> current = root;
        Node<K,V> parentNode = null;

        while(current != null){

            //if same key is found, the node is returned
            if(key.compareTo(current.getKey()) == 0){
                if(parent == true){
                    if(current.equals(root)){
                        return null;
                    }
                    return parentNode;
                }  
                return current;
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

    public void rotateLeft(Node<K,V> center){
        Node<K,V> parent = center.getParent();
        
        //move the center node's left child, replacing itself as its parent's right child
        if(center.getLeft()!= null){
            parent.setRight(center.getLeft());
            center.getLeft().setParent(parent);
        }
        //if the center does not have a left, still replace the parents right child
        else{
            parent.setRight(null);
        }

        center.setLeft(parent);
        Node<K,V> grandparent = parent.getParent();

        //accordingly replacing grandparent node's child with center
        if(grandparent != null){
            if((grandparent.getLeft() != null) && (grandparent.getLeft().getKey().compareTo(parent.getKey()) == 0)){
                grandparent.setLeft(center);
            }
            else{
                grandparent.setRight(center);
            }
        }
        //if grandparent is null and we are adding a node, it must be the root
        else{
            root = center;
        }

        //updating center and parent's parent pointers
        center.setParent(grandparent);
        parent.setParent(center);
    }

    public  void rotateRight(Node<K,V> center){
        Node<K,V> parent = center.getParent();

        //move the center node's right child, replacing itself as its parent's left child
        if(center.getRight()!= null){
            parent.setLeft(center.getRight());
            center.getRight().setParent(parent);
        }
        //if the center does not have a left, still replace the parents left child
        else{
            parent.setLeft(null);
        }
        
        center.setRight(parent);
        Node<K,V> grandparent = parent.getParent();

        //accordingly replacing grandparent node's child with center
        if(grandparent != null){
            if((grandparent.getLeft() != null) && (grandparent.getLeft().getKey().compareTo(parent.getKey()) == 0)){
                grandparent.setLeft(center);
            }
            else{
                grandparent.setRight(center);
            }
        }
        //if grandparent is null and we are adding a node, it must be the root
        else{
            root = center;
        }

        center.setParent(grandparent);
        parent.setParent(center);
    }

    //helper method that recursively does a pre Order treversal through treapMap
    public void preOrder(Node<K,V> r, int numOfSpaces){ 
        
        //base case     
        if(r == null){
            numOfSpaces--;
            return;
        }

        //adds appropriate number of tabs per line (technically node)
        for(int i = 0; i < numOfSpaces; i++){
            tString = tString + "\t";
        }  

        tString = tString + "[" + r.getPriorityValue() + "] <" + r.getKey() + "," + r.getValue() + "> \n";
        numOfSpaces++;

        preOrder(r.getLeft(), numOfSpaces);

        preOrder(r.getRight(), numOfSpaces);
    }

    //helper method that finds the node of highest value in treapMap -- used for join
    public Node<K,V> getMax(){
        Node<K,V> current = root;
        while(current.getRight()!= null){
            current = current.getRight();
        }

        return current;
    }

    //changes nodes with max priority such that their new priorities are max priority -1 
    //ensures that node inserted during split becomes the root
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

    public void setRoot(Node<K,V> r){
        root = r;
    }

    //helper method to debug that does recursive in order traversal and prints treapMap
    public void printTreeInOrder(Node<K,V> r){
        if(r == null){
            return;
        }

        printTreeInOrder(r.getLeft());

        r.printNode();

        printTreeInOrder(r.getRight());
    }

}

package assignment;

//node -- module that stores key/value pairs and pointers to other nodes
public class Node<K,V> {
    private int priorityValue;
    private K key;
    private V value;
    Node<K,V> left;
    Node<K,V> right;
    Node<K, V> parent;

    //3 different constructors
    public Node(){
        priorityValue = 0;
        key = null;
        value = null;
        left = null;
        right = null;
        parent = null;
    }

    public Node(int pri, K k, V v){
        priorityValue = pri;
        key = k;
        value = v;
        left = null;
        right = null;
        parent = null;
    }

    public Node(int pri, K k, V v, Node<K,V> l, Node<K,V> r, Node<K,V> par){
        priorityValue = pri;
        key = k;
        value = v;
        left = l;
        right = r;
        parent = null;
    }

    //setters
    public void setPriorityValue(int input){
        priorityValue = input;
    }

    public void setKey(K k){
        key = k;
    }

    public void setValue(V v){
        value = v;
    }

    public void setLeft(Node<K,V> a){
        left = a;
    }

    public void setRight(Node<K,V> a){
        right = a;
    }

    public void setParent(Node<K,V> a){
        parent = a;
    }
    
    //getters
    public int getPriorityValue(){
        return priorityValue;
    }

    public K getKey(){
        return key;
    }

    public V getValue(){
        return value;
    }

    public Node<K,V> getLeft(){
        return left;
    }

    public Node<K,V> getRight(){
        return right;
    }

    public Node<K,V> getParent(){
        return parent;
    }

    //method used to print node -- useful for debugging
    public void printNode(){
        K l;
        K r;
        
        System.out.println("priority value: " + getPriorityValue() + ", key is: " + getKey() + ", value is: " + getValue());

        if(left != null){
            l = left.getKey();
        }
        else{
            l = null;
        }

        if(right != null){
            r = right.getKey();
        }
        else{
            r = null;
        }

        System.out.println("left is: " + l + ", right is: " + r);
    }
}

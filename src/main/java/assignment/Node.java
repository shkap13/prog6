package assignment;

public class Node<K,V> {
    private int priorityValue;
    private K key;
    private V value;
    private Node<K,V> left;
    private Node<K,V> right;

    public Node(){
        priorityValue = 0;
        key = null;
        value = null;
        left = null;
        right = null;
    }

    public Node(int pri, K k, V v, Node<K,V> l, Node<K,V> r){
        priorityValue = pri;
        key = k;
        value = v;
        left = l;
        right = r;
    }

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
}

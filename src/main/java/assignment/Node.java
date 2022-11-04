package assignment;

public class Node<K,V> {
    private int priorityValue;
    private K key;
    private V value;
     Node<K,V> left;
     Node<K,V> right;

    public Node(){
        priorityValue = 0;
        key = null;
        value = null;
        left = null;
        right = null;
    }

    public Node(int pri, K k, V v){
        priorityValue = pri;
        key = k;
        value = v;
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

package assignment;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import assignment.TreapMap.TreapMapIterator;

public class TestingFile {

    //for insert add 2 methods 1) recursively ensure that the bst propery is held 
    //and 2) the recursively ensure the heap property is held
    @Test
    public void testInsertGeneral(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        treap.insert(3, 5);
        treap.insert(1, 6);
        treap.insert(0, 19);
        treap.insert(4, 8);

        System.out.println(treap.toString());
    }

    @Test
    public void testInsertKeyChange(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        treap.insert(3, 5);
        treap.insert(1, 6);
        treap.insert(0, 19);
        treap.insert(4, 8);
        //make sure is changed
        treap.insert(4, 10);

        System.out.println(treap.toString());
    }

    @Test
    public void testLookUp(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        treap.insert(3, 5);
        treap.insert(1, 6);
        treap.insert(0, 19);
        treap.insert(4, 8);
      
        assertAll(
          () -> assertEquals(5, treap.lookup(3)),
          () -> assertEquals(6, treap.lookup(1)),
          () ->  assertEquals(19, treap.lookup(0)),
          () ->  assertEquals(8, treap.lookup(4)),

          //make sure to return null if there are no values
          () ->  assertEquals(null, treap.lookup(7)),
          () ->  assertEquals(null, treap.lookup(10)),
          () ->   assertEquals(null, treap.lookup(566)

        ));
    }

    @Test
    public void testSplit(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        treap.insert(3, 5, 10);
        treap.insert(1, 6, 20);
        treap.insert(0, 19, 9);
        treap.insert(4, 8, 13);
        treap.insert(12, 8, treap.MAX_PRIORITY);
        treap.insert(100, 34, 1000);
        treap.insert(2, 23, 898);

        System.out.println(treap.toString());

        Treap<Integer, Integer>[] arr;
        arr = treap.split(1);
        //arr = treap.split(null);
        
        System.out.println("******");
        System.out.println(arr[0].toString());

        System.out.println("******");
        System.out.println(arr[1].toString());

    }

    @Test
    public void testJoin(){
        TreapMap<Integer,Integer> treap1 = new TreapMap<Integer, Integer>();
        treap1.insert(14, 12);
        treap1.insert(13, 12);
        treap1.insert(10, 12);
        treap1.insert(8, 12);

        System.out.println(treap1.toString());
        System.out.println("**********");

        TreapMap<Integer,Integer> treap2 = new TreapMap<Integer, Integer>();
        treap2.insert(20, 12);
        treap2.insert(30, 12);
        treap2.insert(48, 12);
        treap2.insert(65, 12);

        System.out.println(treap2.toString());
        System.out.println("**********");

        treap1.join(treap2);

        System.out.println(treap1.toString());

    }

    @Test
    public void testJoinNull(){
        TreapMap<Integer,Integer> treap1 = new TreapMap<Integer, Integer>();
        treap1.insert(14, 12);
        treap1.insert(13, 12);
        treap1.insert(10, 12);
        treap1.insert(8, 12);

        treap1.join(null);

        System.out.println(treap1.toString());
    }

    @Test
    public void testToString(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        treap.insert(3, 5, 10);

        treap.insert(1, 6, 20);
        
        treap.insert(0, 19, 9);
       
        treap.insert(4, 8, 13);

        treap.insert(12, 8, 22);
        
        treap.insert(100, 34, 1000);

        treap.insert(2, 23, 898);

        treap.insert(11, 12, 14);

        treap.insert(13, 43, 16);
        
        treap.insert(9, 54, 20);

        System.out.println(treap.toString());
    }

    @Test
    public void testIterator(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        treap.insert(3, 5, 10);
        treap.insert(1, 6, 20);
        treap.insert(0, 19, 9);
        treap.insert(4, 8, 13);
        treap.insert(12, 8, 22);
        treap.insert(100, 34, 1000);
        treap.insert(2, 23, 898);
        treap.insert(11, 12, 14);
        treap.insert(13, 43, 16);
        treap.insert(9, 54, 20);

        System.out.println(treap.toString());
        System.out.println("*****");

        Iterator<Integer> iter= treap.iterator();

        if(iter.hasNext()){
            System.out.println(iter.next());
        }
        if(iter.hasNext()){
            System.out.println(iter.next());
        }
        if(iter.hasNext()){
            System.out.println(iter.next());
        }
        if(iter.hasNext()){
            System.out.println(iter.next());
        }

    }

    @Test
    public void testIteratorModificationError(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        treap.insert(3, 5, 10);
        treap.insert(1, 6, 20);
        treap.insert(0, 19, 9);
        treap.insert(4, 8, 13);
        treap.insert(12, 8, 22);
        treap.insert(100, 34, 1000);
        treap.insert(2, 23, 898);
        treap.insert(11, 12, 14);
        treap.insert(13, 43, 16);
        treap.insert(9, 54, 20);

        System.out.println(treap.toString());
        System.out.println("*****");

        Iterator<Integer> iter= treap.iterator();

        if(iter.hasNext()){
            System.out.println(iter.next());
        }
        if(iter.hasNext()){
            System.out.println(iter.next());
        }
        if(iter.hasNext()){
            System.out.println(iter.next());
        }
        if(iter.hasNext()){
            System.out.println(iter.next());
        }

        treap.insert(3, 10);

        Assertions.assertThrows(ConcurrentModificationException.class ,
        ()-> iter.hasNext());

    }



    //SPECIFIC TO MY IMPLEMENTATION
    @Test
    public void testFindNode(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        treap.insert(3, 5, 10);
        treap.insert(1, 6, 20);
        treap.insert(0, 19, 9);
        treap.insert(4, 8, 13);

        System.out.println(treap.toString());
        
        assertAll(
            () -> assertEquals(1, treap.findNode(1, false).getKey()),
            () -> assertEquals(0, treap.findNode(0, false).getKey()),
            () -> assertEquals(3, treap.findNode(3, false).getKey()),
            () -> assertEquals(4, treap.findNode(4, false).getKey()),
            () -> assertEquals(4, treap.findNode(12, false).getKey()),


            
            () -> assertEquals(null, treap.findNode(1, true)),
            () -> assertEquals(1, treap.findNode(0, true).getKey()),
            () -> assertEquals(1, treap.findNode(4, true).getKey()),
            () -> assertEquals(4, treap.findNode(3, true).getKey()),

            () -> assertEquals(4, treap.findNode(12, true).getKey()),
            () -> assertEquals(0, treap.findNode(-1, true).getKey())


        );

    }

    @Test
    public void testRotateRight(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        treap.insert(3, 5, 10);
        treap.insert(1, 6, 9);
        treap.insert(0, 19, 6);
        treap.insert(4, 8, 13);

        System.out.println(treap.toString());
        treap.rotateRight(treap.findNode(0, false));
        System.out.println(treap.toString());
    }

    @Test
    public void testRotateLeft(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        treap.insert(3, 5, 10);
        treap.insert(1, 6, 20);
        treap.insert(0, 19, 9);
        treap.insert(4, 8, 13);
        treap.insert(5, 3, 1);
        
        System.out.println(treap.toString());

        treap.rotateLeft(treap.findNode(5, false));
        treap.rotateRight(treap.findNode(4, false));
        System.out.println("****");

        System.out.println(treap.toString());

    }

    @Test
    public void testRemoveForcePriority(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        treap.insert(3, 5, 10);
        treap.insert(1, 6, 20);
        treap.insert(0, 19, 9);
        treap.insert(4, 8, 13);
        treap.insert(12, 8, 22);
        treap.insert(100, 34, 1000);
        treap.insert(2, 23, 898);

        System.out.println(treap.toString());
        System.out.println("*****");
        treap.remove(12);
        treap.remove(100);
        treap.remove(2);
        treap.remove(9);

        System.out.println(treap.toString());

    }

    
}

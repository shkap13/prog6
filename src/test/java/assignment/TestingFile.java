package assignment;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.Assert;
import org.junit.Test;

public class TestingFile {

    @Test
    public void testInsert(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        treap.insert(3, 5);
        treap.findNode(3, false).printNode();
        System.out.println("*****");

        treap.insert(1, 6);
        treap.findNode(1, false).printNode();
        System.out.println("*****");


        treap.insert(0, 19);
        treap.findNode(0, false).printNode();
        System.out.println("*****");

        treap.insert(4, 8);
        treap.findNode(4, false).printNode();
        System.out.println("*****");



        treap.insert(6, 10);
        treap.findNode(6, false).printNode();
        System.out.println("*****");


        
        treap.insert(12, 15);
        treap.findNode(12, false).printNode();
        treap.findNode(6, false).printNode();

        System.out.println("*****");



        treap.insert(32, 0);
        treap.findNode(32, false).printNode();
        treap.findNode(6, false).printNode();

        System.out.println("*****");



        treap.insert(2, 2);
        treap.findNode(2, false).printNode();
        treap.findNode(6, false).printNode();

        System.out.println("*****");


        System.out.println("--------------");

        treap.printTreeInOrder(treap.root);

        System.out.println("--------------");

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

          () ->  assertEquals(null, treap.lookup(7)),
          () ->  assertEquals(null, treap.lookup(10)),
          () ->   assertEquals(null, treap.lookup(566)

        ));

    }

    @Test
    public void testFindNode(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        treap.insert(3, 5, 10);

        treap.insert(1, 6, 20);
        
        treap.insert(0, 19, 9);
       
        treap.insert(4, 8, 13);

        treap.printTreeInOrder(treap.root);

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
        // treap.findNode(3, false).printNode();
        // System.out.println("*****");

        treap.insert(1, 6, 9);
        // treap.findNode(1, false).printNode();
        // System.out.println("*****");


        treap.insert(0, 19, 6);
        // treap.findNode(0, false).printNode();
        // System.out.println("*****");

        treap.insert(4, 8, 13);
        // treap.findNode(4, false).printNode();
        // System.out.println("*****");

        // treap.insert(6, 10);
        // // treap.findNode(6, false).printNode();
        // // System.out.println("*****");


        
        // treap.insert(12, 15);
        // // treap.findNode(12, false).printNode();
        // // treap.findNode(6, false).printNode();

        // //System.out.println("*****");



        // treap.insert(32, 0);
        // treap.findNode(32, false).printNode();
        // treap.findNode(6, false).printNode();

        //System.out.println("*****");



        //treap.insert(2, 2);
        // treap.findNode(2, false).printNode();
        // treap.findNode(6, false).printNode();

        treap.printTreeInOrder(treap.root);
        treap.rotateRight(treap.findNode(0, false));
        //treap.rotateLeft(treap.findNode(1, false));


        treap.printTreeInOrder(treap.root);
    }

    @Test
    public void testRotateLeft(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        treap.insert(3, 5, 10);
        treap.insert(1, 6, 20);
        treap.insert(0, 19, 9);
        treap.insert(4, 8, 13);

        treap.printTreeInOrder(treap.root);
        
        treap.rotateLeft(treap.findNode(4, false));
        System.out.println("****");

        treap.printTreeInOrder(treap.root);

        treap.rotateLeft(treap.findNode(3, false));
        System.out.println("****");

        treap.printTreeInOrder(treap.root);


    }

    @Test
    public void testRemove(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        treap.insert(3, 5, 10);

        treap.insert(1, 6, 20);
        
        treap.insert(0, 19, 9);
       
        treap.insert(4, 8, 13);

        treap.insert(12, 8, 22);
        
        treap.insert(100, 34, 1000);

        treap.insert(2, 23, 898);

        treap.printTreeInOrder(treap.root);

        System.out.println("*****");
        //treap.remove(12);
        //treap.remove(100);
        //treap.remove(2);
        treap.remove(9);

        treap.printTreeInOrder(treap.root);

    }

    @Test
    public void testSplit(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        treap.insert(3, 5, 10);

        treap.insert(1, 6, 20);
        
        treap.insert(0, 19, 9);
       
        treap.insert(4, 8, 13);

        treap.insert(12, 8, 22);
        
        treap.insert(100, 34, 1000);

        treap.insert(2, 23, 898);

        System.out.println(treap.toString());

        Treap<Integer, Integer>[] arr;
        arr = treap.split(12);

        System.out.println("******");
        System.out.println(arr[0].toString());

        System.out.println("******");
        System.out.println(arr[1].toString());

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
    
}

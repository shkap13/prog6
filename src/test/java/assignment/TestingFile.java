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
        treap.findNode(3).printNode();
        System.out.println("*****");

        treap.insert(1, 6);
        treap.findNode(1).printNode();
        System.out.println("*****");


        treap.insert(0, 19);
        treap.findNode(0).printNode();
        System.out.println("*****");

        treap.insert(4, 8);
        treap.findNode(4).printNode();
        System.out.println("*****");



        treap.insert(6, 10);
        treap.findNode(6).printNode();
        System.out.println("*****");


        
        treap.insert(12, 15);
        treap.findNode(12).printNode();
        treap.findNode(6).printNode();

        System.out.println("*****");



        treap.insert(32, 0);
        treap.findNode(32).printNode();
        treap.findNode(6).printNode();

        System.out.println("*****");



        treap.insert(2, 2);
        treap.findNode(2).printNode();
        treap.findNode(6).printNode();

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
    
}

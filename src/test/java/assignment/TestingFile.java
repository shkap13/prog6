package assignment;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.kerberos.KerberosPrincipal;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;

//import assignment.TreapMap.TreapMapIterator;

public class TestingFile {


    public class Treapy<K extends Comparable<K>, V> implements Treap<K,V>{

        @Override
        public V lookup(K key) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void insert(K key, V value) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public V remove(K key) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public Treap[] split(K key) {
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

    }
    //for insert add 2 methods 1) recursively ensure that the bst propery is held 
    //and 2) the recursively ensure the heap property is held
    @Test
    public void testToString(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        treap.insert(3, 5);
        treap.insert(1, 6);
        treap.insert(0, 19);
        treap.insert(4, 8);
        treap.insert(12, 8);
        treap.insert(100, 34);
        treap.insert(2, 23);
        treap.insert(235, 8);
        treap.insert(11, 12);
        treap.insert(13, 43);
        treap.insert(9, 54);
        treap.insert(10, 12);
        treap.insert(9, 32);
        treap.insert(132, 33);
       

        System.out.println(treap.toString());
    }

    @Test
    public void testInsertGeneral(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        for(int i = 0; i < 100; i++){
            treap.insert(i,i);
            assertEquals(true, testHeapProperty(changeToStringToArray(treap.toString())));
            assertTrue(testBSTProperty(changeToStringToArray(treap.toString())));
        }
    }

    @Test
    public void testLookUp(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        treap.insert(3, 5);
        treap.insert(1, 6);
        treap.insert(0, 19);
        treap.insert(4, 8);

        System.out.println(treap.toString());
      
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

    @RepeatedTest(10)
    @Test
    public void testInsertKeyChange(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();
        int ran = 0;

        for(int i = 0; i < 10; i++){
            ran = generateRandomKey(20);
            treap.insert(ran , i);
        }
        
        treap.insert(ran, 100);

        assertEquals(100, treap.lookup(ran));
        assertTrue(testBSTProperty(changeToStringToArray(treap.toString())));
        assertTrue(testHeapProperty(changeToStringToArray(treap.toString())));

    }

    @RepeatedTest(10)
    @Test
    public void testInsertRandom(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        for(int i = 0; i < 100; i++){
            treap.insert(generateRandomKey(80),i);
            assertEquals(true, testHeapProperty(changeToStringToArray(treap.toString())));
            assertTrue(testBSTProperty(changeToStringToArray(treap.toString())));
        }

        
    }

    @Test
    public void testInsertNullKey(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        treap.insert(null, 0);
        assertTrue(testHeapProperty(changeToStringToArray(treap.toString())));
        assertTrue(testBSTProperty(changeToStringToArray(treap.toString())));

    }

    @Test
    public void testInsertNullValue(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        treap.insert(10, null);
        assertTrue(testHeapProperty(changeToStringToArray(treap.toString())));
        assertTrue(testBSTProperty(changeToStringToArray(treap.toString())));

    }

    @RepeatedTest(10)
    @Test
    public void testRemoveOneNode(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();
        int ran = 0;
        
        for(int i = 1; i < 3; i++){
            ran = generateRandomKey(10);
            treap.insert(ran, i);
        }
    
        assertEquals(2, treap.remove(ran));
        assertTrue(testHeapProperty(changeToStringToArray(treap.toString())));
        assertTrue(testBSTProperty(changeToStringToArray(treap.toString())));
    }

    @RepeatedTest(10)
    @Test
    public void testRemoveGeneral(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        int ran = 0;
        
        treap.insert(130, 500);
        treap.insert(900, 83);
        treap.insert(1243, 500);

        for(int i = 15; i < 120; i++){
            ran = generateRandomKey(119);
            treap.insert(ran, i);
        }
    
        assertEquals(119, treap.remove(ran));
        assertTrue(testHeapProperty(changeToStringToArray(treap.toString())));
        assertTrue(testBSTProperty(changeToStringToArray(treap.toString())));

        assertEquals(500, treap.remove(130));
        assertTrue(testHeapProperty(changeToStringToArray(treap.toString())));
        assertTrue(testBSTProperty(changeToStringToArray(treap.toString())));

        assertEquals(83, treap.remove(900));
        assertTrue(testHeapProperty(changeToStringToArray(treap.toString())));
        assertTrue(testBSTProperty(changeToStringToArray(treap.toString())));

        assertEquals(500, treap.remove(1243));
        assertTrue(testHeapProperty(changeToStringToArray(treap.toString())));
        assertTrue(testBSTProperty(changeToStringToArray(treap.toString())));
    }

    @RepeatedTest(5)
    @Test
    public void testRemoveNonExistentKey(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();
        int ran = 0;
        for(int i = 15; i < 120; i++){
            ran = generateRandomKey(119);
            treap.insert(ran, i);
        }

        assertEquals(null, treap.remove(150));
        assertTrue(testHeapProperty(changeToStringToArray(treap.toString())));
        assertTrue(testBSTProperty(changeToStringToArray(treap.toString())));
    }

    @Test
    public void testRemoveNull(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        for(int i = 15; i < 120; i++){
            treap.insert(generateRandomKey(10), i);
        }

        assertEquals(null, treap.remove(null));
        assertTrue(testHeapProperty(changeToStringToArray(treap.toString())));
        assertTrue(testBSTProperty(changeToStringToArray(treap.toString())));

    }


    @RepeatedTest(10)
    @Test
    public void testJoin(){
        TreapMap<Integer,Integer> treap1 = new TreapMap<Integer, Integer>();
        
        for(int i = 1; i < 120; i++){
            treap1.insert(generateRandomKey(150), i);
        }

        TreapMap<Integer,Integer> treap2 = new TreapMap<Integer, Integer>();
        for(int i = 1; i < 120; i++){
            treap2.insert(generateRandomKey(150) + 180, i);
        }

        treap1.join(treap2);
        assertTrue(testHeapProperty(changeToStringToArray(treap1.toString())));
        assertTrue(testBSTProperty(changeToStringToArray(treap1.toString())));

    }

    @RepeatedTest(10)
    @Test
    public void testJoinOtherDirection(){
        TreapMap<Integer,Integer> treap1 = new TreapMap<Integer, Integer>();
        
        for(int i = 1; i < 120; i++){
            treap1.insert(generateRandomKey(150), i);
        }

        TreapMap<Integer,Integer> treap2 = new TreapMap<Integer, Integer>();
        for(int i = 1; i < 120; i++){
            treap2.insert(generateRandomKey(150) + 180, i);
        }

        treap2.join(treap1);
        assertTrue(testHeapProperty(changeToStringToArray(treap2.toString())));
        assertTrue(testBSTProperty(changeToStringToArray(treap2.toString())));
    }

    @Test
    public void testJoinNull(){
        TreapMap<Integer,Integer> treap1 = new TreapMap<Integer, Integer>();

    
        for(int i = 1; i < 120; i++){
            treap1.insert(generateRandomKey(150), i);
        }

        ArrayList<int []> ogList= changeToStringToArray(treap1.toString());

        treap1.join(null);

        ArrayList<int []> newList= changeToStringToArray(treap1.toString());

        for(int i = 0; i < ogList.size(); i++){
            for(int j = 0; j < 4; j++){
                assertEquals(ogList.get(i)[j], newList.get(i)[j]);
            }
        }
        assertTrue(testHeapProperty(changeToStringToArray(treap1.toString())));
        assertTrue(testBSTProperty(changeToStringToArray(treap1.toString())));
    }

    @Test
    public void testJoinNotTreapMap(){

        TreapMap<Integer,Integer> treap1 = new TreapMap<Integer, Integer>();
        Treap<Integer, Integer> fakeTreap = new Treapy<Integer, Integer>();
    
        for(int i = 1; i < 120; i++){
            treap1.insert(generateRandomKey(150), i);
        }

        ArrayList<int []> ogList= changeToStringToArray(treap1.toString());

        for(int i = 1; i < 120; i++){
            fakeTreap.insert(generateRandomKey(150) + 180, i);
        }

        treap1.join(fakeTreap);

        ArrayList<int []> newList= changeToStringToArray(treap1.toString());

        for(int i = 0; i < ogList.size(); i++){
            for(int j = 0; j < 4; j++){
                assertEquals(ogList.get(i)[j], newList.get(i)[j]);
            }
        }
        
        assertTrue(testHeapProperty(changeToStringToArray(treap1.toString())));
        assertTrue(testBSTProperty(changeToStringToArray(treap1.toString())));
    }

    @RepeatedTest(10)
    @Test
    public void testSplitOnExistentKey(){
        TreapMap<Integer,Integer> treap = new TreapMap<Integer, Integer>();
        int max0 = -1;
        int min0 = 122;
        int max1 = -1;
        int min1 = 122;
        
        
        treap.insert(1, 43);
        treap.insert(12, 43);
        treap.insert(159, 43);
        treap.insert(450, 43);

        for(int i = 1; i < 120; i++){
            treap.insert(generateRandomKey(150), i);
            treap.insert(generateRandomKey(150) + 300, i);
            
        }

        Treap<Integer, Integer>[] arr;
        arr = treap.split(12);
        
        ArrayList<int []> list0 = changeToStringToArray(arr[0].toString());
        assertTrue(testHeapProperty(list0));
        assertTrue(testBSTProperty(changeToStringToArray(arr[0].toString())));

        for(int i = 0; i < list0.size(); i++){
            if(list0.get(i)[2] > max0){
                max0 = list0.get(i)[2];
            }

            if(list0.get(i)[2] < min0){
                min0 = list0.get(i)[2];
            }
        }

        ArrayList<int []> list1 = changeToStringToArray(arr[1].toString());
        assertTrue(testHeapProperty(list1));
        assertTrue(testBSTProperty(list1));

        for(int i = 0; i < list1.size(); i++){
            if(list1.get(i)[2] > max1){
                max1 = list1.get(i)[2];
            }

            if(list1.get(i)[2] < min1){
                min1 = list1.get(i)[2];
            }
        }

        if((max0 != -1) && (max1 != -1) && (min0 != 122) && (min1 != 122)){
            assertTrue((max0 < min1) || (max1 < min0));
        }

    }

    @Test
    public void testSplitOnMinKey(){
        TreapMap<Integer,Integer> treap = new TreapMap<Integer, Integer>();
        int max0 = -1;
        int min0 = 122;
        int max1 = -1;
        int min1 = 122;
        
        
        treap.insert(1, 43);
        treap.insert(12, 43);
        treap.insert(159, 43);
        treap.insert(450, 43);

        for(int i = 1; i < 120; i++){
            treap.insert(generateRandomKey(150), i);
            treap.insert(generateRandomKey(150) + 300, i);
            
        }

        Treap<Integer, Integer>[] arr;
        arr = treap.split(1);
        
        ArrayList<int []> list0 = changeToStringToArray(arr[0].toString());
        assertTrue(testHeapProperty(list0));
        assertTrue(testBSTProperty(changeToStringToArray(arr[0].toString())));

        for(int i = 0; i < list0.size(); i++){
            if(list0.get(i)[2] > max0){
                max0 = list0.get(i)[2];
            }

            if(list0.get(i)[2] < min0){
                min0 = list0.get(i)[2];
            }
        }

        ArrayList<int []> list1 = changeToStringToArray(arr[1].toString());
        assertTrue(testHeapProperty(list1));
        assertTrue(testBSTProperty(list1));

        for(int i = 0; i < list1.size(); i++){
            if(list1.get(i)[2] > max1){
                max1 = list1.get(i)[2];
            }

            if(list1.get(i)[2] < min1){
                min1 = list1.get(i)[2];
            }
        }

        if((max0 != -1) && (max1 != -1) && (min0 != 122) && (min1 != 122)){
            assertTrue((max0 < min1) || (max1 < min0));
        }

    }

    @Test
    public void testSplitOnMaxKey(){
        TreapMap<Integer,Integer> treap = new TreapMap<Integer, Integer>();
        int max0 = -1;
        int min0 = 122;
        int max1 = -1;
        int min1 = 122;
        
        
        treap.insert(1, 43);
        treap.insert(12, 43);
        treap.insert(159, 43);
        treap.insert(450, 43);

        for(int i = 1; i < 120; i++){
            treap.insert(generateRandomKey(150), i);
            treap.insert(generateRandomKey(150) + 300, i);
            
        }

        Treap<Integer, Integer>[] arr;
        arr = treap.split(450);
        
        ArrayList<int []> list0 = changeToStringToArray(arr[0].toString());
        assertTrue(testHeapProperty(list0));
        assertTrue(testBSTProperty(changeToStringToArray(arr[0].toString())));

        for(int i = 0; i < list0.size(); i++){
            if(list0.get(i)[2] > max0){
                max0 = list0.get(i)[2];
            }

            if(list0.get(i)[2] < min0){
                min0 = list0.get(i)[2];
            }
        }

        ArrayList<int []> list1 = changeToStringToArray(arr[1].toString());
        assertTrue(testHeapProperty(list1));
        assertTrue(testBSTProperty(list1));

        for(int i = 0; i < list1.size(); i++){
            if(list1.get(i)[2] > max1){
                max1 = list1.get(i)[2];
            }

            if(list1.get(i)[2] < min1){
                min1 = list1.get(i)[2];
            }
        }

        if((max0 != -1) && (max1 != -1) && (min0 != 122) && (min1 != 122)){

            assertTrue((max0 < min1) || (max1 < min0));
        }

    }

    @RepeatedTest(10)
    @Test
    public void testSplitOnNonExistentKey(){
        TreapMap<Integer,Integer> treap = new TreapMap<Integer, Integer>();
        int max0 = -1;
        int min0 = 122;
        int max1 = -1;
        int min1 = 122;

        for(int i = 1; i < 120; i++){
            treap.insert(generateRandomKey(150), i);
            treap.insert(generateRandomKey(150) + 300, i);
            
        }

        Treap<Integer, Integer>[] arr;
        arr = treap.split(200);
        
        ArrayList<int []> list0 = changeToStringToArray(arr[0].toString());
        assertTrue(testHeapProperty(list0));
        assertTrue(testBSTProperty(changeToStringToArray(arr[0].toString())));

        for(int i = 0; i < list0.size(); i++){
            if(list0.get(i)[2] > max0){
                max0 = list0.get(i)[2];
            }

            if(list0.get(i)[2] < min0){
                min0 = list0.get(i)[2];
            }
        }

        ArrayList<int []> list1 = changeToStringToArray(arr[1].toString());
        assertTrue(testHeapProperty(list1));
        assertTrue(testBSTProperty(list1));

        for(int i = 0; i < list1.size(); i++){
            if(list1.get(i)[2] > max1){
                max1 = list1.get(i)[2];
            }

            if(list1.get(i)[2] < min1){
                min1 = list1.get(i)[2];
            }
        }

        if((max0 != -1) && (max1 != -1) && (min0 != 122) && (min1 != 122)){
            assertTrue((max0 < min1) || (max1 < min0));
        }
    }

    @Test
    public void testSplitOnNull(){
        TreapMap<Integer,Integer> treap1 = new TreapMap<Integer, Integer>();

        for(int i = 1; i < 120; i++){
            treap1.insert(generateRandomKey(150), i);
        }

        TreapMap<Integer,Integer> empty = new TreapMap<Integer,Integer>();

        Treap<Integer, Integer>[] arr;
        arr = treap1.split(null);

        assertTrue(arr[0].toString().equals(""));
        assertTrue(arr[1].toString().equals(""));



    }

    @RepeatedTest(10)
    @Test
    public void testIterator(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();
        ArrayList<Integer> ints = new ArrayList<Integer>();

        for(int i = 0; i <100; i++){
            int ran = generateRandomKey(120);
            treap.insert(ran, i);
            if(!(ints.contains(ran))){
                ints.add(ran);
            }
        }

        Iterator<Integer> iter= treap.iterator();
        Collections.sort(ints);

        int counter = 0;
        while(iter.hasNext()){
            assertEquals(ints.get(counter), iter.next());
            counter++;
        }

    }

    //somewhat manually check
    @Test
    public void testIteratorModificationError(){
        TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        for(int i = 1; i < 30; i++){
            treap.insert(generateRandomKey(150), i);
        }

        Iterator<Integer> iter= treap.iterator();

        treap.insert(3, 10);

        Assertions.assertThrows(ConcurrentModificationException.class ,
        ()-> iter.hasNext());

        if(iter.hasNext()){
            System.out.println(iter.next() + "should not print out!");
        }


    }

    public ArrayList<int[]> changeToStringToArray(String treapToString){

        //list that will contain arrays that at index 0 will have number of tabs, index 1 will have priority value 
        //and index 2 have keys and at index 3 have values
        ArrayList<int[]> aList= new ArrayList<int[]>();

        //array of every line (aka node) in the treap
        String [] linebyline = treapToString.split("\n");

        // TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

        // treap.insert(3, 5);
        // treap.insert(1, 6);
        // treap.insert(0, 19);
        // treap.insert(4, 8);

        // String [] linebyline = treap.toString().split("\n");

        for(int i = 0; i < linebyline.length; i++){
            aList.add(new int[4]);
            aList.get(i)[0] = countTabs(linebyline[i]);

            Pattern pattern = Pattern.compile("\\[(.*?)\\]");
            Matcher matcher = pattern.matcher(linebyline[i]);
            if (matcher.find())
            {
                aList.get(i)[1] = Integer.parseInt(matcher.group(1));
            }
            
            pattern = Pattern.compile("<(.*?),");
            matcher = pattern.matcher(linebyline[i]);
            if (matcher.find())
            {
                aList.get(i)[2] = Integer.parseInt(matcher.group(1));
            }

            pattern = Pattern.compile(",(.*?)>");
            matcher = pattern.matcher(linebyline[i]);
            if (matcher.find())
            {
                aList.get(i)[3] = Integer.parseInt(matcher.group(1));
            }   

            // for(int j = 0; j < 4; j++){
            //     System.out.println("alist.get(" + i + "}[" + j + "]: " + aList.get(i)[j]);
            // }
        }

        return aList;
    }

    public int countTabs(String line){
        int index = 0;
        char curr;
        int tabs = 0;
        
        for(int i = 0; i < line.length(); i++){
            if(line.charAt(i) == '\t'){
                tabs++;
            }
            else return tabs;
        }

        return 0;
    }

    public int levelup(int j, ArrayList<int []> aList){
        int counter; 

        for(counter = j-1; counter >= 0; counter--){
            if(aList.get(j)[0]> aList.get(counter)[0]){
                return counter;
            }
        }

        return 0;
        
    }

    public boolean testHeapProperty(ArrayList<int []> aList){
    
        for(int j = 1; j < aList.size(); j++){
            int parent = levelup(j,aList);
            //compares number of tabs of parent node
            if( aList.get(parent)[0] < aList.get(j)[0]){
                //compares priority values
                if(!(aList.get(parent)[1] >= aList.get(j)[1])){
                    // System.out.println("i tabs: " + aList.get(i)[0] + ", i pri value: " + aList.get(i)[1] + "j tabs: " + aList.get(j)[0] + ", j pri value: " + aList.get(j)[1]);
                    return false;
                }
            }
        }
        return true;
    }


    public int generateRandomKey(int max){
        Random rand = new Random();
        int intRandom = rand.nextInt(max);
        
        return intRandom;
    }

    public boolean testBSTProperty(ArrayList<int []> aList){

        for(int j = 1; j < aList.size(); j++){
            int parent = j - 1;
            int left = j;
            int right = bstsiblingsearch(aList, j);

            if(right != -1){
                if(!(aList.get(parent)[2] > aList.get(left)[2]) && (aList.get(right)[2] > aList.get(parent)[2])){
                    return false;
                    //System.out.println("parent: " + aList.get(parent)[2] + ", left: " + aList.get(left)[2] + ", right: " + aList.get(right)[2]);
                }
            }
            else{
                if(aList.get(parent)[2] == aList.get(left)[2]){
                    return false;
                    //System.out.println("parent: " + aList.get(parent)[2] + ", left: " + aList.get(left)[2]);
                }
            }

        }

        return true;
    }

    public int bstsiblingsearch(ArrayList<int []> aList, int j){
        for(int i = j+1; i < aList.size(); i++){
            if(aList.get(j)[0] == aList.get(i)[0]){
                return i;
            }
            else if(aList.get(j)[0] > aList.get(i)[0]){
                return -1;
            }
        }

        return -1;
    }
}


//     //SPECIFIC TO MY IMPLEMENTATION
//     @Test
//     public void testFindNode(){
//         TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

//         treap.insert(3, 5, 10);
//         treap.insert(1, 6, 20);
//         treap.insert(0, 19, 9);
//         treap.insert(4, 8, 13);

//         System.out.println(treap.toString());
        
//         assertAll(
//             () -> assertEquals(1, treap.findNode(1, false).getKey()),
//             () -> assertEquals(0, treap.findNode(0, false).getKey()),
//             () -> assertEquals(3, treap.findNode(3, false).getKey()),
//             () -> assertEquals(4, treap.findNode(4, false).getKey()),
//             () -> assertEquals(4, treap.findNode(12, false).getKey()),


            
//             () -> assertEquals(null, treap.findNode(1, true)),
//             () -> assertEquals(1, treap.findNode(0, true).getKey()),
//             () -> assertEquals(1, treap.findNode(4, true).getKey()),
//             () -> assertEquals(4, treap.findNode(3, true).getKey()),

//             () -> assertEquals(4, treap.findNode(12, true).getKey()),
//             () -> assertEquals(0, treap.findNode(-1, true).getKey())


//         );

//     }

//     @Test
//     public void testRotateRight(){
//         TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

//         treap.insert(3, 5, 10);
//         treap.insert(1, 6, 9);
//         treap.insert(0, 19, 6);
//         treap.insert(4, 8, 13);

//         System.out.println(treap.toString());
//         treap.rotateRight(treap.findNode(0, false));
//         System.out.println(treap.toString());
//     }

//     @Test
//     public void testRotateLeft(){
//         TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

//         treap.insert(3, 5, 10);
//         treap.insert(1, 6, 20);
//         treap.insert(0, 19, 9);
//         treap.insert(4, 8, 13);
//         treap.insert(5, 3, 1);
        
//         System.out.println(treap.toString());

//         treap.rotateLeft(treap.findNode(5, false));
//         treap.rotateRight(treap.findNode(4, false));
//         System.out.println("****");

//         System.out.println(treap.toString());

//     }

//     @Test
//     public void testRemoveForcePriority(){
//         TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

//         treap.insert(3, 5, 10);
//         treap.insert(1, 6, 20);
//         treap.insert(0, 19, 9);
//         treap.insert(4, 8, 13);
//         treap.insert(12, 8, 22);
//         treap.insert(100, 34, 1000);
//         treap.insert(2, 23, 898);

//         // treap.insert(3, 5);
//         // treap.insert(1, 6);
//         // treap.insert(0, 19);
//         // treap.insert(4, 8);
//         // treap.insert(12, 8);
//         // treap.insert(100, 34);
//         // treap.insert(2, 23);

//         System.out.println(treap.toString());
//         System.out.println("*****");
//         treap.remove(12);
//         treap.remove(100);
//         treap.remove(2);
//         treap.remove(9);

//         System.out.println(treap.toString());

//     }
// }
    
// }

// @Test
//     public void testSplit(){
//         TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

//         treap.insert(3, 5, 10);
//         treap.insert(1, 6, 20);
//         treap.insert(0, 19, 9);
//         treap.insert(4, 8, 13);
//         treap.insert(12, 8, treap.MAX_PRIORITY);
//         treap.insert(100, 34, 1000);
//         treap.insert(2, 23, 898);

//         System.out.println(treap.toString());

//         Treap<Integer, Integer>[] arr;
//         arr = treap.split(1);
//         //arr = treap.split(null);
        
//         System.out.println("******");
//         System.out.println(arr[0].toString());

//         System.out.println("******");
//         System.out.println(arr[1].toString());

//     }

// @Test
// public void testToStringME(){
//     TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

//     treap.insert(3, 5, 10);

//     treap.insert(1, 6, 20);
    
//     treap.insert(0, 19, 9);
   
//     treap.insert(4, 8, 13);

//     treap.insert(12, 8, 22);
    
//     treap.insert(100, 34, 1000);

//     treap.insert(2, 23, 898);

//     treap.insert(11, 12, 14);

//     treap.insert(13, 43, 16);
    
//     treap.insert(9, 54, 20);

//     System.out.println(treap.toString());
// }
// }
// @Test
// public void testIterator(){
//     TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

//     treap.insert(3, 5, 10);
//     treap.insert(1, 6, 20);
//     treap.insert(0, 19, 9);
//     treap.insert(4, 8, 13);
//     treap.insert(12, 8, 22);
//     treap.insert(100, 34, 1000);
//     treap.insert(2, 23, 898);
//     treap.insert(11, 12, 14);
//     treap.insert(13, 43, 16);
//     treap.insert(9, 54, 20);

//     System.out.println(treap.toString());
//     System.out.println("*****");

//     Iterator<Integer> iter= treap.iterator();

//     if(iter.hasNext()){
//         System.out.println(iter.next());
//     }
//     if(iter.hasNext()){
//         System.out.println(iter.next());
//     }
//     if(iter.hasNext()){
//         System.out.println(iter.next());
//     }
//     if(iter.hasNext()){
//         System.out.println(iter.next());
//     }

// }

// @Test
// public void testIteratorModificationError(){
//     TreapMap<Integer, Integer> treap = new TreapMap<Integer, Integer>();

//     treap.insert(3, 5, 10);
//     treap.insert(1, 6, 20);
//     treap.insert(0, 19, 9);
//     treap.insert(4, 8, 13);
//     treap.insert(12, 8, 22);
//     treap.insert(100, 34, 1000);
//     treap.insert(2, 23, 898);
//     treap.insert(11, 12, 14);
//     treap.insert(13, 43, 16);
//     treap.insert(9, 54, 20);

//     System.out.println(treap.toString());
//     System.out.println("*****");

//     Iterator<Integer> iter= treap.iterator();

//     if(iter.hasNext()){
//         System.out.println(iter.next());
//     }
//     if(iter.hasNext()){
//         System.out.println(iter.next());
//     }
//     if(iter.hasNext()){
//         System.out.println(iter.next());
//     }
//     if(iter.hasNext()){
//         System.out.println(iter.next());
//     }

//     treap.insert(3, 10);

//     Assertions.assertThrows(ConcurrentModificationException.class ,
//     ()-> iter.hasNext());

// }

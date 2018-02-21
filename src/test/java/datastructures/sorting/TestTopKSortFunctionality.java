package datastructures.sorting;

import misc.BaseTest;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import misc.Searcher;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestTopKSortFunctionality extends BaseTest {
    
    protected IList<String> makeBasicList() {
        IList<String> list = new DoubleLinkedList<>();

        list.add("b");
        list.add("d");
        list.add("f");
        list.add("c");
        list.add("e");
        list.add("a");
        
        return list;
    }
    
    protected List<Integer> makeUnsortedList() {
        Integer[] list = new Integer[500];
        for (int i = 0; i < 500; i++) {
           list[i] = (3376 * i) % 6002;
        }
        return Arrays.asList(list);
    }
    
    protected IList<Integer> listtoIList(List<Integer> list) {
        IList<Integer> iList = new DoubleLinkedList<>();
        for (Integer entry: list) {
           iList.add(entry);
        }
        return iList;
    }
    
    @Test(timeout=SECOND)
    public void testSimpleUsage() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        IList<Integer> top = Searcher.topKSort(5, list);
        assertEquals(5, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(15 + i, top.get(i));
        }
    }
    
    @Test(timeout=SECOND)
    public void testLargerK() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        IList<Integer> top = Searcher.topKSort(25, list);
        assertEquals(20, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(i, top.get(i));
        }
    }
    
    @Test(timeout=SECOND)
    public void testUnsorted() {
        IList<String> list = makeBasicList();
        
        IList<String> top = Searcher.topKSort(3, list);
        
        assertEquals("d", top.get(0));
        assertEquals("e", top.get(1));
        assertEquals("f", top.get(2));
    }
    
    @Test(timeout=SECOND)
    public void testVoidK() {
        IList<String> list = makeBasicList();
        
        try {
            IList<String> top = Searcher.topKSort(-1, list);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // Do nothing: this is ok
        }
        IList<String> top2 = Searcher.topKSort(0, list);
        assertTrue(top2.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testListContainsNull() {
        IList<String> list = new DoubleLinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add(null);

        try {
            IList<String> top = Searcher.topKSort(1, list);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // Do nothing: this is ok
        }
        try {
            IList<String> top = Searcher.topKSort(4, list);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // Do nothing: this is ok
        }
    }
    
    
    @Test(timeout=SECOND)
    public void testLargeInputofUnsorted() {
        List<Integer> list = makeUnsortedList();
        IList<Integer> output = Searcher.topKSort(24, listtoIList(list));
        IList<Integer> expected = new DoubleLinkedList<>();
        Collections.sort(list);
        for (int i = 476; i < 500; i++) {
            expected.add(list.get(i));
        }
        for (int i = 0; i < 24; i++) {
        assertEquals(expected.remove(), output.remove()); 
        }
    }
    
}

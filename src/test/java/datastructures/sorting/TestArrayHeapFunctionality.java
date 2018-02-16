package datastructures.sorting;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import misc.BaseTest;
import datastructures.concrete.ArrayHeap;
import datastructures.interfaces.IPriorityQueue;
import org.junit.Test;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestArrayHeapFunctionality extends BaseTest {
    
    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeap<>();
    }

    protected IPriorityQueue<String> makeBasicHeap() {
        IPriorityQueue<String> heap = new ArrayHeap<>();
        
        heap.insert("a");
        heap.insert("b");
        heap.insert("c");
        heap.insert("d");
        heap.insert("e");
        
        return heap;
    }
    
    
    @Test(timeout=SECOND)
    public void testBasicSize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        assertEquals(1, heap.size());
        assertTrue(!heap.isEmpty());
    }
    
    
    
    @Test(timeout=SECOND)
    public void testRemoveFromEmpty() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        assertTrue(heap.isEmpty());
        try {
            heap.removeMin();
            fail("Expected NoSuchElementException");
        } catch (NoSuchElementException ex) {
            // Do nothing: this is ok
        }
    }
    
    @Test(timeout=SECOND)
    public void testPeakFromEmpty() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        assertTrue(heap.isEmpty());
        try {
            heap.peekMin();
            fail("Expected NoSuchElementException");
        } catch (NoSuchElementException ex) {
            // Do nothing: this is ok
        }
    }
    
    @Test(timeout=SECOND)
    public void testRemoveMin() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(5);
        assertEquals(1, heap.size());
        assertEquals(5, heap.removeMin());
        assertEquals(0, heap.size());
        assertTrue(heap.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testPeekMin() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(5);
        assertEquals(1, heap.size());
        assertEquals(5, heap.peekMin());
        assertEquals(1, heap.size());
        assertTrue(!heap.isEmpty());
    }
    
    
    @Test(timeout=SECOND)
    public void testAddandRemoveMany() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 100; i++) {
            heap.insert(i);
            assertEquals(1, heap.size());
            assertEquals(i, heap.removeMin());
            assertTrue(heap.isEmpty());
        }
    }
    
    @Test(timeout=SECOND)
    public void testSortForwardInsert() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 20; i++) {
            heap.insert(i);
        }
        assertEquals(20, heap.size());
        for (int i = 0; i < 20; i++) {
            assertEquals(i, heap.removeMin());
        }
        assertTrue(heap.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testSortBackWardsInsert() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 19; i >= 0; i--) {
            heap.insert(i);
            assertEquals(i, heap.peekMin());
        }
        assertEquals(20, heap.size());
        for (int i = 0; i < 20; i++) {
            assertEquals(i, heap.removeMin());
        }
        assertTrue(heap.isEmpty());
    }
    
    
    @Test(timeout=SECOND)
    public void testResizeForwardInsert() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 100; i++) {
            heap.insert(i);
        }
        assertEquals(100, heap.size());
        for (int i = 0; i < 100; i++) {
            assertEquals(i, heap.removeMin());
        }
        assertTrue(heap.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testResizeBackWardsInsert() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 99; i >= 0; i--) {
            heap.insert(i);
        }
        assertEquals(100, heap.size());
        for (int i = 0; i < 100; i++) {
            assertEquals(i, heap.removeMin());
        }
        assertTrue(heap.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testInsertNullEmpty() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        assertTrue(heap.isEmpty());
        try {
            heap.insert(null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // Do nothing: this is ok
        }
    }
    
    
    @Test(timeout=SECOND)
    public void testInsertNull() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(6);
        assertTrue(!heap.isEmpty());
        try {
            heap.insert(null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // Do nothing: this is ok
        }
    }
    

    @Test(timeout=SECOND)
    public void testBasicInsert() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        IPriorityQueue<String> heap2 = new ArrayHeap<>();
        for (int i =  0; i < 100; i++) {
            heap.insert(i);
            assertEquals(0, heap.peekMin());
        }
        assertEquals(100, heap.size());
        
        heap2.insert("c");
        assertEquals("c", heap2.peekMin());
        
        heap2.insert("d");
        assertEquals("c", heap2.peekMin());
        
        heap2.insert("a");
        assertEquals("a", heap2.peekMin());
        
        assertEquals(3, heap2.size());
    }
    
    @Test(timeout=SECOND)
    public void testBasicRemove() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        IPriorityQueue<String> heap2 = makeBasicHeap();
        for (int i =  0; i < 10; i++) {
            heap.insert(i);
            assertEquals(i + 1, heap.size());
        }
        
        int temp;
        for (int i = 0; i < 10; i++) {
            temp=heap.removeMin();
            assertEquals(i, temp);
            assertEquals(9 - i, heap.size());
        }
        String temp2 = "";
        
        temp2 = heap2.removeMin();
        assertEquals(temp2, "a");
        temp2 = heap2.removeMin();
        assertEquals(temp2, "b");
        temp2 = heap2.removeMin();
        assertEquals(temp2, "c");
        temp2 = heap2.removeMin();
        assertEquals(temp2, "d");
        temp2 = heap2.removeMin();
        assertEquals(temp2, "e");
    }
    
}
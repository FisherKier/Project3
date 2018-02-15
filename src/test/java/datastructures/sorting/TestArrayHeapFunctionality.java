package datastructures.sorting;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

import org.junit.Test;

import datastructures.concrete.ArrayHeap;
import datastructures.interfaces.IPriorityQueue;
import misc.BaseTest;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestArrayHeapFunctionality extends BaseTest {
    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeap<>();
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
        for (int i = 0; i < 10; i++) {
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
    public void testSorted() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 5; i < 25; i++) {
        heap.insert(((59*i) % 199));
        }
        for (int i = 0; i < heap.size(); i++) {
        output.add(heap.removeMin());
        }
        assertTrue(heap.isEmpty());
        assertEquals(output, Collections.sort(output));
    }
    
    
}

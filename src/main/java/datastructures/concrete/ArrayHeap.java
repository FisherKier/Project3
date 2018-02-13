package datastructures.concrete;

import java.util.NoSuchElementException;
import datastructures.interfaces.IPriorityQueue;

/**
 * See IPriorityQueue for details on what each method must do.
 */
public class ArrayHeap<T extends Comparable<T>> implements IPriorityQueue<T> {
    // See spec: you must implement a implement a 4-heap.
    private static final int NUM_CHILDREN = 4;

    // You MUST use this field to store the contents of your heap.
    // You may NOT rename this field: we will be inspecting it within
    // our private tests.
    private T[] heap;
    private int totalSize;
    private int elementSize;

    // Feel free to add more fields and constants.

    public ArrayHeap() {
        elementSize = 0;
        totalSize = 50;
        heap = makeArrayOfT(50);
    }
    
    public ArrayHeap(int size) {
        elementSize = 0;
        totalSize = size;
        heap = makeArrayOfT(size);
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain elements of type T.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private T[] makeArrayOfT(int size) {
        // This helper method is basically the same one we gave you
        // in ArrayDictionary and ChainedHashDictionary.
        //
        // As before, you do not need to understand how this method
        // works, and should not modify it in any way.
        return (T[]) (new Comparable[size]);
    }

    @Override
    public T removeMin() {
        if(elementSize == 0) {
            throw new NoSuchElementException();
        }
        
        T minValue = heap[0];
        heap[0] = heap[elementSize - 1];
        
        percolateDown(0);
        elementSize--;
        return minValue;
    }
    
    private void percolateDown(int curIndex) {
        int childIndex1 = 4 * curIndex + 1;
        int childIndex2 = 4 * curIndex + 2;
        int childIndex3 = 4 * curIndex + 3;
        int childIndex4 = 4 * curIndex + 4;
        
        if(childIndex1 < elementSize && heap[childIndex1].compareTo(heap[curIndex]) < 0) {
            T temp = heap[childIndex1];
            heap[childIndex1] = heap[curIndex];
            heap[curIndex] = temp;
            percolateDown(childIndex1);
        } else if (childIndex2 < elementSize && heap[childIndex2].compareTo(heap[curIndex]) < 0) {
            T temp = heap[childIndex2];
            heap[childIndex2] = heap[curIndex];
            heap[curIndex] = temp;
            percolateDown(childIndex2);
        } else if (childIndex3 < elementSize && heap[childIndex3].compareTo(heap[curIndex]) < 0) {
            T temp = heap[childIndex3];
            heap[childIndex3] = heap[curIndex];
            heap[curIndex] = temp;
            percolateDown(childIndex3);
        } else if (childIndex4 < elementSize && heap[childIndex4].compareTo(heap[curIndex]) < 0) {
            T temp = heap[childIndex4];
            heap[childIndex4] = heap[curIndex];
            heap[curIndex] = temp;
            percolateDown(childIndex4);
        }
        
        /*
        for(int i = 1; i <= NUM_CHILDREN; i++) {
            int childIndex = 4 * curIndex + i;
            if(childIndex < elementSize && 
                    heap[childIndex].compareTo
                    //may need to be > 0
                    (heap[curIndex]) > 0) {
                
                T temp = heap[childIndex];
                heap[childIndex] = heap[curIndex];
                heap[curIndex] = temp;
                
                percolateDown(childIndex);
                break;
            }
        } 
        */
    }

    @Override
    public T peekMin() {
        if(elementSize == 0) {
            throw new NoSuchElementException();
        }
        return heap[0];
    }

    @Override
    public void insert(T item) {
        if(item == null) {
            throw new IllegalArgumentException();
        }
        
        elementSize++;
        if(elementSize == totalSize) {
            resize();
        }

        heap[elementSize - 1] = item;
        percolateUp(elementSize - 1);
    }
    
    private void resize() {
        T temp[] = makeArrayOfT(totalSize * 2);
        for(int i = 0; i < elementSize; i ++) {
            temp[i] = heap[i];
        }
        
        totalSize = totalSize * 2;
        heap = temp;
    }
    
    private void percolateUp(int curIndex) {
        if(curIndex != 0) {
                int parentIndex = (curIndex-1) / 4;
                if(heap[parentIndex].compareTo(heap[curIndex]) > 0) {
                    T temp = heap[parentIndex];
                    heap[parentIndex] = heap[curIndex];
                    heap[curIndex] = temp;
                
                    percolateUp(parentIndex);
            }
        }
    }

    @Override
    public int size() {
        return elementSize;
    }
}

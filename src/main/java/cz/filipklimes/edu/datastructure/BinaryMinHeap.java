package cz.filipklimes.edu.datastructure;

import cz.filipklimes.edu.datastructure.exception.EmptyHeapException;
import cz.filipklimes.edu.datastructure.exception.InvalidInitialCapacityException;

import java.util.*;

/**
 * Implementation of MinHeap (a priority queue) as a binary heap.
 * The underlying structure is an array, which should be cache-friendly.
 * The array will always be at least as big as the number of elements in the
 * heap. However, each time the inserted element should overflow the array,
 * the array will be resized to 2-times it's current size.
 * WARNING: This data structure is not thread-safe.
 */
public class BinaryMinHeap implements MinHeap
{

    private int[] array;

    /**
     * Points to the last element of the heap.
     */
    private int tail;

    /**
     * Construct the binary heap with given initial capacity.
     * Everytime the underlying array is not sufficient to store
     * the elements, it is grown by the factor of 2.
     *
     * @param initialCapacity Initial capacity of the underlying array.
     */
    public BinaryMinHeap(final int initialCapacity)
    {
        if (initialCapacity < 1) {
            throw new InvalidInitialCapacityException();
        }
        this.array = new int[initialCapacity];
        this.tail = -1;
    }

    public BinaryMinHeap()
    {
        this(128);
    }

    /**
     * @inheritDoc
     */
    public int peek()
    {
        if (tail < 0) {
            throw new EmptyHeapException();
        }
        return array[0];
    }

    /**
     * @inheritDoc
     */
    public int pop()
    {
        if (tail < 0) {
            throw new EmptyHeapException();
        }
        int min = array[0];

        array[0] = array[tail];
        --tail;
        bubbleDown(0);

        return min;
    }

    /**
     * @inheritDoc
     */
    public void push(final int value)
    {
        ++tail;
        if (tail >= array.length) {
            // Resize the array by factor of 2
            int newSize = array.length << 1;
            if (newSize < 0) {
                throw new OutOfMemoryError();
            }
            array = Arrays.copyOf(array, newSize);
        }

        array[tail] = value;
        bubbleUp(tail);
    }

    /**
     * Merges elements of the other heap into this heap.
     *
     * @param other
     */
    public void merge(final BinaryMinHeap other)
    {
        int requiredSize = size() + other.size();
        int newSize = array.length;
        while (requiredSize > newSize) {
            newSize = newSize << 1;
            if (newSize < 0) {
                throw new OutOfMemoryError();
            }
        }
        array = Arrays.copyOf(array, newSize);

        for (int i = 0; i <= other.tail; i++) {
            push(other.array[i]);
        }
    }

    /**
     * @inheritDoc
     */
    public int size()
    {
        return tail + 1;
    }

    private int leftChild(int index)
    {
        return index << 1;
    }

    private int rightChild(int index)
    {
        return (index << 1) + 1;
    }

    private int parent(int index)
    {
        return index >> 1;
    }

    /**
     * Bubbles down through the heap and restores heap property.
     * Finishes in log(n) time, where n is the number of elements.
     *
     * @param index Index from which to bubble down.
     */
    private void bubbleDown(int index)
    {
        while (index <= tail) {
            int leftChildIndex = leftChild(index);
            int rightChildIndex = rightChild(index);

            if (leftChildIndex > tail) {
                break; // We have nowhere to go, we are done
            }
            if (rightChildIndex > tail) {
                // This element has no right child, check heap property with left and then exit
                if (array[leftChildIndex] < array[index]) {
                    swap(leftChildIndex, index);
                }
                break;
            }

            if (array[index] < array[leftChildIndex] && array[index] < array[rightChildIndex]) {
                // Heap property is held
                break;
            }

            if (array[leftChildIndex] < array[rightChildIndex]) {
                swap(leftChildIndex, index);
                index = leftChildIndex;
            } else {
                swap(rightChildIndex, index);
                index = rightChildIndex;
            }
        }
    }

    /**
     * Bubbles up through the heap and restores heap property.
     * Finishes in log(n) time, where n is the number of elements.
     *
     * @param index Index from which to bubble up.
     */
    private void bubbleUp(int index)
    {
        while (index >= 1) { // When index is 0, we are in root
            int parentIndex = parent(index);
            if (array[parentIndex] > array[index]) {
                // Swap with parent if heap property is broken
                swap(parentIndex, index);
            }
            // Continue to the parent
            index = parentIndex;
        }
    }

    private void swap(int from, int to)
    {
        int temp = array[to];
        array[to] = array[from];
        array[from] = temp;
    }

}

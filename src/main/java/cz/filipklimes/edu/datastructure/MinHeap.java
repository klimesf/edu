package cz.filipklimes.edu.datastructure;

/**
 * A priority queue accepting integers and returning them in ascending order.
 */
public interface MinHeap
{

    /**
     * Returns the value which would be popped next.
     * The value remains in the heap.
     *
     * @return The minimum value within the heap.
     * @throws cz.filipklimes.edu.datastructure.exception.EmptyHeapException If the heap is empty.
     */
    int peek();

    /**
     * Pops the minimum value from the heap and returns its value.
     * The value will no longer be present within the heap.
     *
     * @return The minimum value within the heap.
     * @throws cz.filipklimes.edu.datastructure.exception.EmptyHeapException If the heap is empty.
     */
    int pop();

    /**
     * Inserts the given value to the heap.
     *
     * @param value The value. Can be negative.
     */
    void push(int value);

    /**
     * Returns number of elements stored in the heap.
     *
     * @return Number of stored elements.
     */
    int size();

}

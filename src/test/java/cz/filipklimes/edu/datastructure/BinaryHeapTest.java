package cz.filipklimes.edu.datastructure;

import cz.filipklimes.edu.datastructure.exception.EmptyHeapException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinaryHeapTest
{

    @Test
    void testPeek()
    {
        BinaryMinHeap heap = new BinaryMinHeap();
        heap.push(1);
        assertEquals(1, heap.peek());
        heap.push(-1);
        assertEquals(-1, heap.peek());
        heap.push(2);
        assertEquals(-1, heap.peek());
        heap.push(-1);
        assertEquals(-1, heap.peek());
        heap.push(Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, heap.peek());
    }

    @Test
    void testPop()
    {
        BinaryMinHeap heap = new BinaryMinHeap();
        for (int i = 255; i >= -256; i--) {
            heap.push(i);
        }
        for (int i = -256; i < 256; i++) {
            assertEquals(i, heap.pop());
        }
        assertEquals(0, heap.size());
    }

    @Test
    void testResize()
    {
        BinaryMinHeap heap = new BinaryMinHeap(2);
        for (int i = 0; i < 256; i++) {
            heap.push(i);
        }

        assertEquals(0, heap.peek());
        assertEquals(256, heap.size());
    }

    @Test
    void testMerge()
    {
        BinaryMinHeap heap = new BinaryMinHeap(2);
        BinaryMinHeap other = new BinaryMinHeap(2);
        for (int i = 0; i < 256; i++) {
            heap.push(i);
            other.push(i - 256);
        }

        assertEquals(256, heap.size());
        assertEquals(256, other.size());

        heap.merge(other);
        assertEquals(512, heap.size());

        for (int i = -256; i < 256; i++) {
            assertEquals(i, heap.pop());
        }
    }

    @Test
    void testEmptyHeapExceptions()
    {
        BinaryMinHeap heap = new BinaryMinHeap();
        assertThrows(EmptyHeapException.class, heap::peek);
        assertThrows(EmptyHeapException.class, heap::pop);
    }

}

# Computer science snippets for educational purposes

This repository contains code snippets implementing computer-science concepts
which are meant for educational purposes.

## Binary min heap implementation

This package contains [binary min heap](https://github.com/klimesf/edu/blob/master/src/main/java/cz/filipklimes/edu/datastructure/BinaryMinHeap.java)
implementation backed by primitive integer array.

These are the results of benchmark vs `java.util.PriorityQueue<Integer>`.
First, we added 10M random numbers and then polled them. The test was carried
out in 10 iterations. The `PriorityQueue` requires a warm-up and then exhibits
similar performance. However, the poll operation is much slower. The times are
calculated as a mean over all iterations with standard deviation.
You can check out the [benchmark implementation](https://github.com/klimesf/edu/blob/master/src/main/java/cz/filipklimes/edu/datastructure/HeapBenchmark.java).

```
BinaryMinHeap#push: 492.062327ms +/- 66.602692ms
PriorityQueue<Integer>#add: 971.143220ms +/- 960.383883ms

BinaryMinHeap#pop: 2852.478666ms +/- 62.338150ms
PriorityQueue<Integer>#poll: 12725.931642ms +/- 142.818597ms
```

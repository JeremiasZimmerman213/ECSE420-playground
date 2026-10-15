# Lecture 4 notes

## Thread Cooperation: Conditions
Busy waiting: Thread continuously consumes CPU resources checking something that hasn't changed.
Java provides `Condition` objects that allow threads to sleep until another thread signals them.
1. `await()` releases the lock.
2. `signalAll()` wakes waiting threads.
3. We use `while`, not `if`.

## Producer-Consumer and Blocking Queues
Imagine a system processing images uploaded by users.
You have two components:
- **Producer**: Receives images and adds them to a queue.
- **Consumer**: Takes images from the queue and processes them.

Java Solution: `BlockingQueue`
```java
BlockingQueue<Integer> queue =
    new ArrayBlockingQueue<>(3);
```
**Why this matters**: In concurrent programming, prefer using existing thread-safe abstractions rather than implementing synchronization yourself.

## Semaphores
Lock that allows more than one thread to hold it (still has a capacity)
```java
Semaphore semaphore = new Semaphore(3);
```
The semaphore ensures that no more than three threads enter the protected section simultaneously.

## Deadlocks
A **deadlock** occurs when threads are stuck waiting for resources held by one another, preventing any of them from proceeding.
One prevention method: Resource Ordering.

**Key intuition**: Deadlocks arise from circular dependencies. Consistent lock ordering prevents circular lock acquisition.

## Thread Lifecycle
| State | 	Meaning |
| ----- | --------- |
| New | 	Thread created but not started | 
| Ready | 	Thread can execute but isn't currently using the CPU | 
| Running | 	Thread currently executing |
| Blocked | 	Thread waiting for something |
| Finished | 	Execution completed |

## Parallel Programming: Fork/Join
### The idea: Divide and conquer
Each section can be processed independently. Afterward, we combine the results. This is exactly the idea behind the Fork/Join Framework.

Creating, scheduling, and coordinating subtasks takes time. That's why Fork/Join commonly uses a threshold:
```java
if (problem is small)
    solve sequentially
else
    split into subtasks
    solve subtasks
    combine results
```





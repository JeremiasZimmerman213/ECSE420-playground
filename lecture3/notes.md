# Lecture 3 Notes

## Race Condition:
A race condition occurs when the program's correctness depends on the unpredictable timing or ordering of concurrent operations.

## Synchronization
Think of a bathroom with a lock on its door.
Only one person can enter at a time. Everyone else must wait until the person inside leaves.
This is called **mutual exclusion**.

`Synchornized` keyword:
```Java
class BankAccount {
    private int balance = 0;

    public synchronized void deposit(int amount) {
        balance = balance + amount;
    }
}
```

It tells Java that a thread must acquire the account object's lock before executing this method.

Before a thread can execute this funciton, it must acquire the lock. If the lock is taken by another thread, it must wait.

The important concept here is the critical section: the portion of code that accesses shared data and must be protected from conflicting concurrent operations.

### Two ways to sync code:
**option A**: sync the entire code
**option B**: Sync a specific block

## Explicit Locks — `ReentrantLock`
Instead of using synchronized, you manually acquire and release a lock.

## Thread Cooperation — Conditions
Java provides **conditions**, which allow a thread to sleep until another thread signals that something has changed.

| Method | Purpose |
| ------ | ------- |
| `await()` | Wait until signaled |
| `signal()` | Wake one waiting thread |
| `signalAll()` | Wake all waiting threads |

Calling await for a thread releases its lock. Main reason is because there might be another thread of which it is awaiting, and that is thread might be waiting for the lock. if the lock is not released, then the code would be halted forever.

Always check the condition with while, not with if:
```java
while (balance < amount) {
    fundsAvailable.await();
}
```

## Other sync concepts
- Producer–Consumer Problem
- Semaphores
- Synchronized Collections

## Deadlocks — When Threads Get Stuck Forever
- One solution: resource ordering (every thread acquires resources in same order)
Remember: A race condition produces potentially incorrect results. A deadlock prevents progress.

## Parallel Programming — Fork/Join Framework
We also want to use multiple CPU cores to finish computational tasks faster.
Standard Pattern:
```java
if (problem is small):
    solve sequentially
else:
    divide into smaller problems
    execute subproblems in parallel
    combine results
```



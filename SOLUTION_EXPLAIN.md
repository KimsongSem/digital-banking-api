# + Concurrence transfer handling design in Transfer Service

TransferService built to handle concurrent money transfers safely. Since multiple threads may attempt to access and modify same accounts simultaneously, 
it's critical to ensure data consistency, avoid race conditions, and prevent deadlocks. 
Solution is implement by using pessimistic database locking with consistent locking order strategy based on account number.

### - Locking Strategy
To prevent concurrent modifications of account balances, the service retrieves account records using pessimistic write locks via the JPA annotation:

```
@Lock(LockModeType.PESSIMISTIC_WRITE)
Optional<Account> findWithLockByAccountNumber(String accountNumber);
```
When thread access account with this lock mode, 
database row is locked for writing and for other transactions attempting to access the same row 
must wait until the lock is released. This ensures that only one transfer can update an 
account's balance at a time, it's prevent race conditions and lost updates.

### - Consistent Lock Ordering
Deadlocks can occur when two threads attempt transfer money between the same accounts in opposite directions. 
For example:

Thread A: transfers from ACC000001 → ACC000002

Thread B: transfers from ACC000002 → ACC000001

If both threads lock the "from" account first, each hold one lock and waiting, resulting in a deadlock.
to avoid this, the service ensures consistent lock order by:
```
if (from.compareTo(to) < 0) {
    firstLock = accountService.getAccountWithLockByAccountNumber(from, true);
    secondLock = accountService.getAccountWithLockByAccountNumber(to, false);
    fromIsFirst = true;
} else {
    firstLock = accountService.getAccountWithLockByAccountNumber(to, false);
    secondLock = accountService.getAccountWithLockByAccountNumber(from, true);
    fromIsFirst = false;
}
```
This any two threads working with the same pair of accounts will always locks in the same order.
so it can prevent deadlock occur as well.

For example: always lock ACC000001 before ACC000002
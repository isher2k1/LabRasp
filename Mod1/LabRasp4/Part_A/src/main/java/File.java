import java.util.ArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class File {
    public ArrayList<Record> records;
    private ReadWriteLock lock;

    public File() {
        records = InitializeStartRecords();
        lock = new ReentrantReadWriteLock();
    }
    private ArrayList<Record> InitializeStartRecords() {
        ArrayList<Record> res = new ArrayList<>();
        res.add(new Record("Vladimir", "Rybak", "Andriy", "555-55"));
        res.add(new Record("Mark", "Zukerberg", "Kolya", "333-33"));
        res.add(new Record("Eve", "Katz", "Ludvigh", "222-22"));
        res.add(new Record("Adrian", "Spilberg", "Papus", "NumerOfAdrian"));

        return res;
    }

    public void LockRead() {
        lock.readLock().lock();
    }
    public void UnlockRead() {
        lock.readLock().unlock();
    }
    public void LockWrite() {
        lock.writeLock().lock();
    }
    public void UnlockWrite() {
        lock.writeLock().unlock();
    }
}
import java.util.concurrent.atomic.AtomicBoolean;

public class Loader extends Thread{
    private AtomicBoolean isWorking;
    private AtomicBoolean thiefWorking;
    private AtomicBoolean accountantWorking;
    public static final int DELAY = 100;
    ItemQueue fromThief;
    private ItemQueue toAccountant;

    Loader(ItemQueue fromThief, ItemQueue toAccountant,
           AtomicBoolean thiefWorking, AtomicBoolean isWorking, AtomicBoolean accountantWorking) {
        this.fromThief = fromThief;
        this.toAccountant = toAccountant;
        this.isWorking = isWorking;
        this.thiefWorking = thiefWorking;
        this.accountantWorking = accountantWorking;
    }

    @Override
    public void run() {
        Item item;
        while (isWorking.get() || !fromThief.isEmpty()) {
            item = fromThief.get();
            System.out.println("Loader loaded item  #" + item.getCode() + " : $" + item.getPrice());
            try {
                sleep(DELAY);
            } catch (InterruptedException e) {
                this.interrupt();
            }
            toAccountant.add(item);
            if (fromThief.isEmpty() && !thiefWorking.get()) {
                accountantWorking.set(false);
            }
        }
    }
}

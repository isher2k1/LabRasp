import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    public static final int MAX_ELEMENTS = 10;


    public static void main(String[] args) {
        AtomicBoolean thiefWorking = new AtomicBoolean(true);
        AtomicBoolean loaderWorking = new AtomicBoolean(true);
        AtomicBoolean accountantWorking = new AtomicBoolean(true);

        ItemQueue fromThief = new ItemQueue(MAX_ELEMENTS);
        ItemQueue toAccountant = new ItemQueue(MAX_ELEMENTS);

        Thief thief = new Thief(fromThief, thiefWorking, loaderWorking);
        Loader loader = new Loader(fromThief, toAccountant, thiefWorking, loaderWorking, accountantWorking);
        Accountant accountant = new Accountant(toAccountant, loaderWorking, accountantWorking);

        thief.start();
        loader.start();
        accountant.start();

        try {
            accountant.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

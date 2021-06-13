import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        ItemQueue roundOneMonasteryOne = new ItemQueue(Settings.COMPETITORS);
        ItemQueue roundTwoMonasteryOne = new ItemQueue(Settings.COMPETITORS);
        ItemQueue roundOneMonasteryTwo = new ItemQueue(Settings.COMPETITORS);
        ItemQueue roundTwoMonasteryTwo = new ItemQueue(Settings.COMPETITORS);

        Random rand = new Random(System.currentTimeMillis());

        for (int i = 0; i < Settings.COMPETITORS; i++) {
            roundOneMonasteryOne.add(new Monk(rand.nextInt(Settings.MAX_QI), Settings.FIRST_MONASTERY));
            roundOneMonasteryTwo.add(new Monk(rand.nextInt(Settings.MAX_QI), Settings.SECOND_MONASTERY));
        }

        List<Lobby> lobbies = new ArrayList<>();
        for (int i = 0; i < Settings.LOBBIES; i++) {
            lobbies.add(new Lobby(roundOneMonasteryOne,
                    roundTwoMonasteryOne,
                    roundOneMonasteryTwo,
                    roundTwoMonasteryTwo));
            lobbies.get(i).start();
            try {
                lobbies.get(i).join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

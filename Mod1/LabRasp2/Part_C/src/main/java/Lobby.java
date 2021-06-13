import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Lobby extends Thread{
    ItemQueue roundOneMonasteryOne;
    ItemQueue roundTwoMonasteryOne;
    ItemQueue roundOneMonasteryTwo;
    ItemQueue roundTwoMonasteryTwo;

    private AtomicBoolean isAlive = new AtomicBoolean(true);

    Lobby(
            ItemQueue roundOneMonasteryOne,
            ItemQueue roundTwoMonasteryOne,
            ItemQueue roundOneMonasteryTwo,
            ItemQueue roundTwoMonasteryTwo
    ) {
        this.roundOneMonasteryOne = roundOneMonasteryOne;
        this.roundTwoMonasteryOne = roundTwoMonasteryOne;
        this.roundOneMonasteryTwo = roundOneMonasteryTwo;
        this.roundTwoMonasteryTwo = roundTwoMonasteryTwo;
    }

    private String monkString(Monk monk) {
        return monk.getMonastery() + " [" + monk.getQi() + "] ";
    }

    private Monk compete(Monk monk1, Monk monk2) {
        Random random = new Random(System.currentTimeMillis());
        System.out.println("Monk from " + monkString(monk1) + " vs " + monkString(monk2));
        if (monk1.getQi() > monk2.getQi())
            return monk1;
        else if (monk1.getQi() < monk2.getQi())
            return monk2;
        else return (random.nextInt(2) == 0 ? monk1 : monk2);
    }

    @Override
    public void run() {
        Monk competitor1;
        Monk competitor2;
        Monk roundWinner;
        while (isAlive.get()) {
            competitor1 = null;
            competitor2 = null;
            roundWinner = null;
            if (roundOneMonasteryOne.isEmpty() && roundOneMonasteryTwo.isEmpty()) {
                if (roundTwoMonasteryOne.isEmpty() && roundTwoMonasteryTwo.isEmpty())
                    break;
                roundOneMonasteryOne.copy(roundTwoMonasteryOne);
                roundOneMonasteryTwo.copy(roundTwoMonasteryTwo);
                System.out.println("Round is finished!");
            } else if (roundOneMonasteryOne.isEmpty() && !roundOneMonasteryTwo.isEmpty()) {
                if (roundOneMonasteryTwo.getSize() == 1) {
                    System.out.println("WINNER FROM " +
                            Settings.SECOND_MONASTERY + "!");
                    roundOneMonasteryTwo.clear();
                    break;
                }
                competitor1 = roundOneMonasteryTwo.get();
                competitor2 = roundOneMonasteryTwo.get();
            } else if (!roundOneMonasteryOne.isEmpty() && roundOneMonasteryTwo.isEmpty()) {
                if (roundOneMonasteryOne.getSize() == 1) {
                    System.out.println("WINNER FROM " +
                            Settings.FIRST_MONASTERY + "!");
                    roundOneMonasteryOne.clear();
                    break;
                }
                competitor1 = roundOneMonasteryOne.get();
                competitor2 = roundOneMonasteryOne.get();
            } else {
                competitor1 = roundOneMonasteryOne.get();
                competitor2 = roundOneMonasteryTwo.get();
            }

            if (competitor1 != null && competitor2 != null) {
                roundWinner = compete(competitor1, competitor2);
                if (roundWinner.getMonastery().equals(Settings.FIRST_MONASTERY))
                    roundTwoMonasteryOne.add(roundWinner);
                else
                    roundTwoMonasteryTwo.add(roundWinner);
                System.out.println("Won monk from " + monkString(roundWinner));
            }

            try {
                sleep(Settings.SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

package bullscows;

public class Game {

    public static void start() {
        BullsAndCows bullsAndCows = new BullsAndCows();

        bullsAndCows.initialize();
        bullsAndCows.getUserInput();
    }
}

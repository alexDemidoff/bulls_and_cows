package bullscows;

public class Game {

    public static void start() {
        BullsAndCows bullsAndCows = new BullsAndCows();

        bullsAndCows.readSecretCodeLength();
        bullsAndCows.readNumberOfPossibleSymbols();
        bullsAndCows.prepareSecretCode();
        bullsAndCows.readUserGuess();
    }
}

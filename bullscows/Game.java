package bullscows;

public class Game {

    public static void start() {
        BullsAndCows bullsAndCows = BullsAndCows.initialize();

        bullsAndCows.readSecretCodeLength();
        bullsAndCows.readNumberOfPossibleSymbols();
        bullsAndCows.prepareSecretCode();
        bullsAndCows.readUserGuess();
    }
}

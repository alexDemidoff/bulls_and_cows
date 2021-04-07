package bullscows;

import java.util.Random;
import java.util.Scanner;

public class BullsAndCows {

    private static final Scanner scanner = new Scanner(System.in);
    private final int MAX_NUMBER_OF_POSSIBLE_SYMBOLS = 36;

    private GameStatus currentStatus;

    private String secretCode;
    private int secretCodeLength;
    private int numberOfPossibleSymbols;

    private BullsAndCows() {
        currentStatus = GameStatus.SUCCESS;
    }

    public static BullsAndCows initialize() {
        return new BullsAndCows();
    }

    public void readSecretCodeLength() {
        System.out.println("Input the length of the secret code:");

        String secretCodeLengthStr = scanner.nextLine();
        if (!secretCodeLengthStr.matches("^([0-9]|[1-9][0-9]+)$")) {
            System.out.printf("Error: \"%s\" isn't a valid number.\n", secretCodeLengthStr);
            currentStatus = GameStatus.ERROR;
        } else if (!secretCodeLengthStr.matches("^([1-9]|[1-3][0-6])$")) {
            System.out.println("Error: maximum length of the secret code is 36 and minimum one is 1.");
            currentStatus = GameStatus.ERROR;
        }

        if (currentStatus == GameStatus.SUCCESS) {
            secretCodeLength = Integer.parseInt(secretCodeLengthStr);
        }
    }

    public void readNumberOfPossibleSymbols() {
        if (currentStatus == GameStatus.SUCCESS) {
            System.out.println("Input the number of possible symbols in the code:");

            String numberOfPossibleSymbolsStr = scanner.nextLine();
            if (!numberOfPossibleSymbolsStr.matches("^([1-9]|[1-9][0-9]+)$")) {
                System.out.printf("Error: \"%s\" isn't a valid number.\n", numberOfPossibleSymbolsStr);
                currentStatus = GameStatus.ERROR;
            } else if (numberOfPossibleSymbolsStr.matches("^([3-9][7-9]|[4-9][0-9]|[1-9]+[0-9]+[0-9]+)$")) {
                System.out.printf("Error: maximum number of possible symbols in the code is %d (0-9, a-z).\n", MAX_NUMBER_OF_POSSIBLE_SYMBOLS);
                currentStatus = GameStatus.ERROR;
            } else {
                numberOfPossibleSymbols = Integer.parseInt(numberOfPossibleSymbolsStr);

                if (numberOfPossibleSymbols < secretCodeLength) {
                    System.out.printf("Error: it's not possible to generate a code with a length of %d with %s unique symbols.\n", secretCodeLength, numberOfPossibleSymbolsStr);
                    currentStatus = GameStatus.ERROR;
                }
            }
        }
    }

    public void prepareSecretCode() {
        if (currentStatus == GameStatus.SUCCESS) {
            secretCode = generateSecretCode(secretCodeLength, numberOfPossibleSymbols);

            int leftNumberRange = 0;
            int rightNumberRange = numberOfPossibleSymbols > 10 ? 9 : numberOfPossibleSymbols - 1;

            String info;
            if (numberOfPossibleSymbols <= 10) {
                info = String.format("(%d-%d)", leftNumberRange, rightNumberRange);
            } else {
                char leftCharacterRange = 'a';
                char rightCharacterRange = (char) ('a' + numberOfPossibleSymbols - 11);

                info = String.format("(%d-%d), (%c-%c)", leftNumberRange, rightNumberRange,
                        leftCharacterRange, rightCharacterRange);
            }

            String hiddenSecretCode = "*".repeat(secretCodeLength);

            System.out.printf("The secret code is prepared: %s %s\n", hiddenSecretCode, info);
        }
    }

    public void readUserGuess() {
        if (currentStatus == GameStatus.SUCCESS) {
            System.out.println("Okay, let's start a game!");

            int turn = 1;
            String userGuess;
            do {
                System.out.printf("Turn %d:\n", turn);
                userGuess = scanner.nextLine();
                showGrade(secretCode, userGuess);
                turn++;
            } while (getBullCount(secretCode, userGuess) != secretCodeLength);

            System.out.println("Congratulations! You guessed the secret code.");
        }
    }

    private String generateSecretCode(int length, int numberOfPossibleSymbols) {
        Random r = new Random();
        StringBuilder randomNumber = new StringBuilder();

        char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'g', 'k', 'l',
                'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

        int leftBorder = 0;
        int rightBorder = numberOfPossibleSymbols;
        int randomIndex;

        while (leftBorder != length) {
            randomIndex = r.nextInt(rightBorder - leftBorder) + leftBorder;

            randomNumber.append(digits[randomIndex]);
            char buf = digits[randomIndex];
            digits[randomIndex] = digits[leftBorder];
            digits[leftBorder] = buf;

            leftBorder++;
        }

        return randomNumber.toString();
    }

    private void showGrade(String secretCode, String userGuess) {
        int bullCount = getBullCount(secretCode, userGuess);
        int cowCount = getCowCount(secretCode, userGuess);

        if (bullCount == 0 && cowCount == 0) {
            System.out.println("Grade: None.");
        } else if (bullCount == 0) {
            System.out.printf("Grade: %d cow(s).\n", cowCount);
        } else if (cowCount == 0) {
            System.out.printf("Grade: %d bull(s).\n", bullCount);
        } else {
            System.out.printf("Grade: %d bull(s) and %d cow(s).\n", bullCount, cowCount);
        }
    }

    private int getCowCount(String secretCode, String userGuess) {
        char[] secretCodeDigits = secretCode.toCharArray();
        char[] userGuessDigits = userGuess.toCharArray();

        int count = 0;

        for (int i = 0; i < secretCodeDigits.length; i++) {
            for (int j = 0; j < userGuessDigits.length; j++) {
                if (secretCodeDigits[i] == userGuessDigits[j] && (i != j)) {
                    count++;
                }
            }
        }

        return count;
    }

    private int getBullCount(String secretCode, String userGuess) {
        char[] secretCodeDigits = secretCode.toCharArray();
        char[] userGuessDigits = userGuess.toCharArray();

        int count = 0;

        for (int i = 0; i < secretCodeDigits.length; i++) {
            if (userGuessDigits[i] == secretCodeDigits[i]) {
                count++;
            }
        }

        return count;
    }
}

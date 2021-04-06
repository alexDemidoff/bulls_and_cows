package bullscows;

import java.util.Random;
import java.util.Scanner;

public class BullsAndCows {

    private static final Scanner scanner = new Scanner(System.in);
    private final int SECRET_CODE_MAX_LENGTH = 36;
    private final int SECRET_CODE_MIN_LENGTH = 1;

    private String secretCode;
    private int secretCodeLength;
    private int numberOfPossibleSymbols;

    public void readSecretCodeLength() {
        do {
            System.out.println("Please, enter the secret code's length:");
            secretCodeLength = Integer.parseInt(scanner.nextLine());
        } while (secretCodeLength < SECRET_CODE_MIN_LENGTH || secretCodeLength > SECRET_CODE_MAX_LENGTH);
    }

    public void readNumberOfPossibleSymbols() {
        do {
            System.out.println("Input the number of possible symbols in the code:");

            numberOfPossibleSymbols = Integer.parseInt(scanner.nextLine());

        } while (numberOfPossibleSymbols < secretCodeLength || numberOfPossibleSymbols > SECRET_CODE_MAX_LENGTH);
    }

    public void prepareSecretCode() {
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

    public void readUserGuess() {
        System.out.println("Okay, let's start a game!");

        int turn = 1;
        String userGuess;
        do {
            System.out.printf("Turn %d:\n", turn);
            userGuess = scanner.nextLine();
            showGrade(secretCode, userGuess);
            turn++;
        } while (getBullCount(secretCode, userGuess) != secretCode.length());

        System.out.println("Congratulations! You guessed the secret code.");
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

package bullscows;

import exceptions.WrongLengthException;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class BullsAndCows {

    private static final Scanner scanner = new Scanner(System.in);
    private String secretCode;

    public void initialize() {
        System.out.println("Please, enter the secret code's length:");

        boolean isCorrect = false;
        do {
            int length = 0;
            try {
                length = Integer.parseInt(scanner.nextLine());
                secretCode = generateSecretCode(length);
                isCorrect = true;
            } catch (WrongLengthException e) {
                System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.\n", length);
            }
        } while (!isCorrect);
    }

    public void getUserInput() {
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


    /*
    private String generateSecretCode(int length) throws WrongLengthException {
        if (length > 10) {
            throw new WrongLengthException();
        }

        ArrayList<Character> res = new ArrayList<>();
        char[] randomNumberDigits;
        long randomNumber;
        int randomNumberLength;
        boolean isFirstDigit;

        do {
            randomNumber = System.nanoTime();
            randomNumberDigits = String.valueOf(randomNumber).toCharArray();
            isFirstDigit = true;
            res.clear();

            int i = randomNumberDigits.length - 1;
            randomNumberLength = 0;

            while (randomNumberLength != length && i >= 0) {
                if (isFirstDigit) {
                    if (randomNumberDigits[i] != '0') {
                        res.add(randomNumberDigits[i]);
                        isFirstDigit = false;
                        randomNumberLength++;
                    }
                } else {
                    if (!res.contains(randomNumberDigits[i])) {
                        res.add(randomNumberDigits[i]);
                        randomNumberLength++;
                    }
                }
                i--;
            }
        } while (randomNumberLength != length);

        StringBuilder sb = new StringBuilder();

        for (char c : res) {
            sb.append(c);
        }

        return sb.toString();

    }

     */

    private String generateSecretCode(int length) throws WrongLengthException {
        if (length > 10) {
            throw new WrongLengthException();
        }

        int[] digits = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random r = new Random();
        StringBuilder randomNumber = new StringBuilder();

        int low = 0;
        int high = 10;
        int randomIndex;
        boolean isFirstDigit = true;

        while (low != length) {
            if (isFirstDigit) {
                randomIndex = r.nextInt(high - 1) + 1;
                isFirstDigit = false;
            } else {
                randomIndex = r.nextInt(high - low) + low;
            }

            randomNumber.append(digits[randomIndex]);
            int buf = digits[randomIndex];
            digits[randomIndex] = digits[low];
            digits[low] = buf;

            low++;
        }

        return randomNumber.toString();
    }

    private void showGrade(String secretCode, String userGuess) {
        int bullCount = 0;
        int cowCount = 0;

        bullCount = getBullCount(secretCode, userGuess);
        cowCount = getCowCount(secretCode, userGuess);

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

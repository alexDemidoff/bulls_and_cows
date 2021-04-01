package bullscows;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int secretCode = 1234;

        System.out.println("The secret code is prepared: ****.");
        int userGuess = scanner.nextInt();

        showGrade(secretCode, userGuess);
    }

    private static void showGrade(int secretCode, int userGuess) {
        int bullCount = 0;
        int cowCount = 0;

        bullCount = getBullCount(secretCode, userGuess);
        cowCount = getCowCount(secretCode, userGuess);

        if (bullCount == 0 && cowCount == 0) {
            System.out.printf("Grade: None. The secret code is %d.\n", secretCode);
        } else if (bullCount == 0) {
            System.out.printf("Grade: %d cow(s). The secret code is %d.\n", cowCount, secretCode);
        } else if (cowCount == 0) {
            System.out.printf("Grade: %d bull(s). The secret code is %d.\n", bullCount, secretCode);
        } else {
            System.out.printf("Grade: %d bull(s) and %d cow(s). The secret code is %d.\n", bullCount, cowCount, secretCode);
        }
    }

    private static int getCowCount(int secretCode, int userGuess) {
        char[] secretCodeDigits = String.valueOf(secretCode).toCharArray();
        char[] userGuessDigits = String.valueOf(userGuess).toCharArray();

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

    private static int getBullCount(int secretCode, int userGuess) {
        char[] secretCodeDigits = String.valueOf(secretCode).toCharArray();
        char[] userGuessDigits = String.valueOf(userGuess).toCharArray();

        int count = 0;

        for (int i = 0; i < secretCodeDigits.length; i++) {
            if (userGuessDigits[i] == secretCodeDigits[i]) {
                count++;
            }
        }

        return count;
    }
}

import java.io.*;
import java.util.Scanner;

public class HighScoreManager {
    private static final String FILE_PATH = "highscore.txt";

    // Save high score to a file
    public static void saveHighScore(int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(String.valueOf(score));
        } catch (IOException e) {
            System.out.println("An error occurred while saving the high score.");
            e.printStackTrace();
        }
    }

    // Read high score from a file
    public static int loadHighScore() {
        try (Scanner scanner = new Scanner(new File(FILE_PATH))) {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            }
        } catch (FileNotFoundException e) {
            // If file doesn't exist, assume score is 0
            System.out.println("No high score found, starting fresh.");
        }
        return 0; // default high score
    }

}

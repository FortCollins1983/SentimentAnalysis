import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class SentimentAnalysis {

    public static void main(String[] args) {
        HashTable<String, Integer> sentiments = new HashTable<>(100);

        // Load each word or phrase and its rating from the data file.
        try (Scanner file = new Scanner(new File("sentiments.txt"))) {
            while (file.hasNextLine()) {
                String line = file.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                int comma = line.lastIndexOf(',');
                String phrase = line.substring(0, comma).trim();
                int rating = Integer.parseInt(line.substring(comma + 1).trim());
                sentiments.put(phrase, rating);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find sentiments.txt.");
            return;
        }

        int wordCount = 0;
        int totalSentiment = 0;
        String previousWord = "";

        System.out.println("Enter text:");
        try (Scanner keyboard = new Scanner(System.in)) {
            while (keyboard.hasNextLine()) {
                String instring = keyboard.nextLine();
                if (instring.trim().equals("END")) {
                    break;
                }

                String[] words = instring.replaceAll("[^a-zA-Z ]", "")
                        .toLowerCase(Locale.ROOT).trim().split("\\s+");

                for (String word : words) {
                    if (word.isEmpty()) {
                        continue;
                    }
                    wordCount++;

                    Integer rating = sentiments.get(word);
                    if (rating != null) {
                        totalSentiment += rating;
                    }

                    // Check pairs too, including pairs across input lines.
                    if (!previousWord.isEmpty()) {
                        Integer phraseRating = sentiments.get(previousWord + " " + word);
                        if (phraseRating != null) {
                            totalSentiment += phraseRating;
                        }
                    }
                    previousWord = word;
                }
            }
        }

        double average = 0.0;
        if (wordCount > 0) {
            average = (double) totalSentiment / wordCount;
        }

        System.out.println("Words: " + wordCount);
        System.out.println("Sentiment: " + totalSentiment);
        System.out.printf(Locale.US, "Overall: %.2f%n", average);
    }
}

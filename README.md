# SentimentAnalysis

A Java sentiment analysis program using the supplied HashTable class.
The table doubles and rehashes its entries when its load factor exceeds 0.5.
The program adds scores for individual words and matching two-word phrases.

## Run with Maven

Open a terminal in the SentimentAnalysis project folder, then run:

```sh
mvn compile
java -cp target/classes SentimentAnalysis
```

Keep sentiments.txt in the project folder. Enter text on one or more lines.
Type END on its own line to show the results.

## Run without Maven

From the project folder:

```sh
mkdir -p out
javac -d out src/main/java/*.java
java -cp out SentimentAnalysis
```

## OnlineGDB

Create a Java project. Add HashTable.java and SentimentAnalysis.java from
src/main/java, and add sentiments.txt as a text file. Use this Main.java:

```java
public class Main {
    public static void main(String[] args) {
        SentimentAnalysis.main(args);
    }
}
```

## Data

sentiments.txt contains the 2,454 supplied data rows. One word appears twice;
the hash table updates its entry rather than storing duplicate keys.
The assignment identifies the data as adapted from AFINN (2011).

## Submission

Upload this project's contents to a GitHub repository named SentimentAnalysis.
Include pom.xml, sentiments.txt, and the src folder. Submit the repository
link in D2L as directed by your instructor.

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class  Game {
    private static Trie dictionary;
    private static Trie dictionarySix;

    public Game() {
        dictionary = new Trie();
        dictionarySix = new Trie();
    }

    public static void main(String[] args) {
        try {
            Game game = new Game();
            File dictionaryFile = new File("TextTwist/FinalDictionary.txt");
            game.loadDictionary(dictionaryFile);
            File dictionarySixFile = new File("TextTwist/SixLetterDictionary.txt");
            game.loadSixLetterDictionary(dictionarySixFile);
            game.dictionarySix.printTrie();
            String importantWord = game.createWord();
            System.out.println("Important Word: " + importantWord);
            ArrayList<String> anagrams = game.getAnagrams(importantWord);
            System.out.println("Anagrams between 3-6 letters: " + anagrams);
            String shuffledWord = game.shuffle(importantWord);
            System.out.println("Shuffled Word: " + shuffledWord);
            char[] hiveList = importantWord.toCharArray();
            game.playGame(hiveList);
        } catch (FileNotFoundException e) {
            System.out.println("Error: Dictionary file not found.");
        }
    }

    // Loads a dictionary of words from a file and adds them to the dictionary trie
    public void loadDictionary(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String word = scanner.nextLine().replace("'", "");
            dictionary.add(word);
        }
        scanner.close();
    }
    //Loads a dictionary of six-letter words from a file and adds them to the six-letter dictionary trie
    public void loadSixLetterDictionary(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String word = scanner.nextLine().replace("'", "");
            dictionarySix.add(word);
        }
        scanner.close();
    }
    /**
     * Collects all the words stored in the given trie.
     * The method recursively traverses the trie and gathers words by checking the word end markers.
     * returns an ArrayList of all words stored in the trie
     */
    public ArrayList<String> collectAllWords(Trie trie) {
        ArrayList<String> words = new ArrayList<>();
        collectWords(trie.getRoot(), "", words);
        return words;
    }
    /**
     * Recursively collects words from the trie, starting from the given node.
     * This helper method is called by collectAllWords to traverse the trie and build the word list.
     * node - the current trie node being processed
     * currentWord - the current word formed during traversal
     * words - the list of words being collected
     */
    private void collectWords(TrieNode node, String currentWord, ArrayList<String> words) {
        if (node == null) return;
        if (node.wordEnd == 1) {
            words.add(currentWord);
        }
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                char c = (char) (i + 'a');
                collectWords(node.children[i], currentWord + c, words);
            }
        }
    }
    /**
     * Creates a random six-letter word from the six-letter dictionary.
     * The method collects all the six-letter words and selects a random one to return.
     * returns a randomly chosen six-letter word
     */
    public String createWord() {
        ArrayList<String> words = collectAllWords(dictionarySix);
        Random rand = new Random();
        String word = words.get(rand.nextInt(words.size()));
        return word;
    }
    /**
     * Finds all possible anagrams of the given word from the dictionary.
     * generates all possible words that can be formed from input word letters, filters them by length
     * importantWord - the word whose anagrams are to be found
     * returns an ArrayList of valid anagrams
     */
    public static ArrayList<String> getAnagrams(String importantWord) {
        ArrayList<String> possibleAnagrams = new ArrayList<>();
        ArrayList<String> allWords = getPossibleWords(importantWord, dictionary);
        for (String word : allWords) {
            if (word.length() >= 3 && word.length() <= 6) {
                possibleAnagrams.add(word);
            }
        }
        return possibleAnagrams;
    }
    /**
     * Checks whether a given guess is a valid word and matches any anagram of the target word.
     * The method verifies if the guess is an anagram of the provided word and has a length of at least 3 characters.
     * guess - the word guessed by the player
     * word - the target word to check against
     * returns the length of the valid word, or 0 if invalid
     */
    public static int checkWord(String guess, String word) {
        if (getAnagrams(word).contains(guess) && guess.length() >= 3) {
            return guess.length();
        }
        return 0;
    }

    /**
     * Shuffles the characters in the given word randomly to create a new arrangement of letters.
     * the parameter importantWord is the word whose characters are going to shuffled
     * returns a new string with the characters of the input word in a random order
     */

    public static String shuffle(String importantWord) {
        ArrayList<Character> letters = new ArrayList<>();
        for (char c : importantWord.toCharArray()) {
            letters.add(c);
        }
        Collections.shuffle(letters);
        StringBuilder shuffledWord = new StringBuilder();
        for (char c : letters) {
            shuffledWord.append(c);
        }
        return shuffledWord.toString();
    }

    /**Executes the Text Twist game where the player tries to form valid words
     * using the provided letters in the hive array. The game provides feedback
     * on word validity and scores based on word length. The game continues
     * until the player types 'quit'.
     * @param hiveList a character array containing the letters that can be used to form words during the game
     */

    public void playGame(char[] hiveList) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Text Twist!");
        System.out.println("Try to find as many words as possible using these letters:");
        System.out.println("Hive: " + new String(hiveList));
        System.out.println("Type 'quit' to exit.");
        ArrayList<String> foundWords = new ArrayList<>();
        while (true) {
            System.out.print("Enter a word: ");
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("quit")) {
                break;
            }
            if (input.length() < 3) {
                System.out.println("Invalid word! Words must be at least 3 letters long.");
                continue;
            }
            int score = checkWord(input, hiveList.toString());
            if (score > 0 && isHive(input, hiveList) && !foundWords.contains(input)) {
                foundWords.add(input);
                System.out.println("Valid word! Score: " + score);
            } else {
                System.out.println("Invalid word or already found!");
            }
        }
        System.out.println("Game Over! You found " + foundWords.size() + " words.");
    }

    //Ensures that each word that the user passes in is composed of entirely hive letters
    public boolean isHive(String word, char[] hiveList) {
        int[] hiveCount = new int[26]; //The max number of letters that could be in hive
        int[] wordCount = new int[26];
        //These for-each loops simply increment the value of hive to ensure that the word that the user guesses utilizes all of the letters in the hive.
        for (char c : hiveList) {
            hiveCount[c - 'a']++;
        }
        for (char c : word.toCharArray()) {
            wordCount[c - 'a']++;
            if (hiveCount[c - 'a'] < wordCount[c - 'a']) { // Not every letter in the hive is present in the word.
                return false;
            }
        }
        return true;
    }

    //Creates an array list of very possible word that can be formed with a seed of letters
    public static ArrayList<String> getPossibleWords(String input, Trie dictionary) {
        ArrayList<String> possibleWords = new ArrayList<>();
        generatePossibleWords("", input, possibleWords, dictionary);
        return possibleWords;
    }

    /**Helper method for getPossibleWords.
     * Recursively generate all possible words efficiently by passing in a prefix, which at first is just null, and
     * a remaining word, which at first is just the word itself. As you iterate through the list of characters and prefixes
     * of other words are discovered, the prefixes are added to the list of possible words. This list is than used in getPossible words.
     */

    private static void generatePossibleWords(String prefix, String remaining, ArrayList<String> possibleWords, Trie dictionary) {
        if (!prefix.isEmpty() && dictionary.contains(prefix) && !possibleWords.contains(prefix)) {
            possibleWords.add(prefix);
        }
        for (int i = 0; i < remaining.length(); i++) {
            generatePossibleWords(
                    prefix + remaining.charAt(i),
                    remaining.substring(0, i) + remaining.substring(i + 1), possibleWords, dictionary);
        }
    }
}
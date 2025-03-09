import java.io.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;

public class Trie {
    private TrieNode root;

    //trie constructor
    public Trie() {
        root = new TrieNode();
    }

    // method that adds a word to the trie
    public void add(String word) {
        word = word.toLowerCase();
        TrieNode cur = root;
        //increments through the entire word
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            //checks if letter node is null
            if (cur.children[c - 'a'] == null) {
                cur.children[c - 'a'] = new TrieNode();
            }
            cur = cur.children[c - 'a'];
        }
        //end of word
        cur.wordEnd = 1;
    }

    //checks if a trie is empty
    static boolean isEmpty(TrieNode root) {
        for (int i = 0; i < 26; i++) {
            if (root.children[i] != null) {
                return false;
            }
        }
        return true;
    }

    //removes a word from a trie by setting wordEnd to 0
    public void remove(String word) {
        if (word == null || word.isEmpty()) return;

        TrieNode cur = root;

        // traverse the Trie to find the word
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            cur = cur.children[c - 'a'];
            if (cur == null) {
                return; //word dne
            }
        }
        cur.wordEnd = 0;
    }

    //checks if a trie contains a word
    public boolean contains(String word) {
        TrieNode cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (cur.children[c - 'a'] == null) {
                return false;
            }
            cur = cur.children[c - 'a'];
        }
        if (cur != null && cur.wordEnd == 1) {
            return true;
        }
        return false;
    }

    //checks to see if there is a word in a trie with a "prefix" word/if one word contains another
    public boolean prefix(TrieNode root, String check) {
        TrieNode cur = root;

        for (char c : check.toCharArray()) {
            //prefix does not exist
            if (cur.children[c - 'a'] == null) {
                return false;
            }
            cur = cur.children[c - 'a'];
        }
        return true;
    }

    //returns the root node of a Trie
    public TrieNode getRoot() {
        return root;
    }

    //prints a trie
    //made this method for checking testing stuffs
    public void printTrie() {
        printTrie(root, "");
    }

    //prints a trie
    private void printTrie(TrieNode node, String currentWord) {
        if (node == null) return;
        if (node.wordEnd == 1) {
            System.out.println(currentWord);
        }
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                char c = (char) (i + 'a');
                printTrie(node.children[i], currentWord + c);
            }
        }
    }
}
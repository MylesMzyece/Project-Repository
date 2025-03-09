public class TrieNode {
    int wordEnd;
    TrieNode[] children;
    public TrieNode(){
        wordEnd = 0;
        children = new TrieNode[26];
    }
}

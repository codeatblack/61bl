import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;



/**
 * Created by kos on 8/3/16.
 */
public class Trie {
    private String activeState = "";
    private HashMap<Character, TrieNode> dictionary;
    private TrieNode[] dict;
    private LinkedList<String> locations;

    public Trie() {
        this.dict = new TrieNode[36];
        this.locations = new LinkedList<>();
        this.alphabatize();
    }



    public void insert(String word) {

        //String word = initial.toUpperCase();
        TrieNode current = dict[0];
        boolean number = false;
        String origWord = new String(word);
        word = word.toLowerCase();
        String fixed = "";
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == ' ') {
                fixed += ch;
            }
        }
        word = fixed;

        if (word.length() > 0) {
            int startsWith = word.toUpperCase().charAt(0) - 'A';

            if (startsWith >= 0) {
                current = dict[startsWith];
            }

            if (startsWith < 0) {
                current = dict[startsWith + 43];
            }
        }

        if (current.dictionary.isEmpty()) {
            current.dictionary.add(0, origWord);
        }

        if (word.length() > 1) {

            if (current.next.containsKey(word.toLowerCase().charAt(1))) {
                current.next.get(word.charAt(1)).dictionary.add(0, origWord);
                current.parseThrough(current.next.get(word.charAt(1)), origWord, 1);
            }

            if (!current.next.containsKey(word.toLowerCase().charAt(1))) {
                current.next.put(word.charAt(1), new TrieNode(origWord, current));
                current.next.get(word.charAt(1)).dictionary.add(0, origWord);
                current.parseThrough(current.next.get(word.charAt(1)), origWord, 1);
            }
        }


        //TrieNode temp = current.next.get(word.charAt(1));

        //=temp.parseThrough(temp,word, word.length());
    }

    public void alphabatize() {
        int letter = 0;
        int count = 0;
        while (count < 26) {
            dict[count] = new TrieNode(Integer.toString(count));
            count++;
            letter++;
        }

        if (count == 26) {
            while (count < 36) {
                dict[count] = new TrieNode(Integer.toString(letter));
                letter++;
                count++;
            }
        }

    }

    public void setLocations(MapNode store, String name) {
        int n = 1;
        TrieNode current = dict[0];


        String origWord = new String(name);
        name = name.toLowerCase();
        String fixed = "";
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == ' ') {
                fixed += ch;
            }
        }
        name = fixed;

        if (name.length() > 0) {
            int startsWith = name.toUpperCase().charAt(0) - 'A';

            if (startsWith >= 0) {
                current = dict[startsWith];
            }

            if (startsWith < 0) {
                current = dict[startsWith + 43];
            }

            current.placeLocations(store,
                    current.next.get(name.toLowerCase().charAt(1)),
                    name, 1, origWord);
        }
    }

    public List<MapNode> sendLocations(String name) {
        int count = 1;
        TrieNode current = this.dict[0];

        String origWord = new String(name);
        name = name.toLowerCase();
        String fixed = "";
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == ' ') {
                fixed += ch;
            }
        }
        name = fixed;



        if (!name.equals("")) {

            int startsWith = name.toUpperCase().charAt(0) - 'A';

            if (startsWith >= 0) {
                current = dict[startsWith];
            }

            if (startsWith < 0) {
                current = dict[startsWith + 43];
            }


            while (count < name.length() - 1) {
                if (count == 2) {
                    current = current.next.get(name.toLowerCase().charAt(count - 1)).
                            next.get(name.toLowerCase().charAt(count));
                } else {
                    current = current.next.get(name.toLowerCase().charAt(count));
                }
                count++;
            }
            return current.allStores;
        } else {
            return null;
        }
    }

    public TrieNode getWords(int prefix) {
        if (prefix < 0) {
            return dict[prefix + 43];
        } else {
            return dict[prefix];
        }

    }
}

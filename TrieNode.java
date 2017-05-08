import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by kos on 8/3/16.
 */

public class TrieNode {
    HashMap<Character, TrieNode> next;
    TrieNode prev;
    String name;
    List<MapNode> allStores = new LinkedList<>();
    LinkedList<String> dictionary = new LinkedList<>();



    public TrieNode(String keyy) {
        this.name = keyy;
        this.dictionary = new LinkedList<>();
        //this.dictionary.add("");
        this.next = new HashMap<>();
    }

    public TrieNode(String vall, TrieNode previous) {
        //this.name = keyy;
        this.dictionary = new LinkedList<>();
        this.dictionary.add(0, vall);
        this.prev = previous;
        this.next = new HashMap<>();
    }

    public void addNewLocation(MapNode store) {
        this.allStores.add(store);
    }

    public void parseThrough(TrieNode node, String initial, Integer n) {
        TrieNode temp = node;
        int parentLength = n;

        String origWord = new String(initial);
        initial = initial.toLowerCase();
        String fixed = "";
        for (int i = 0; i < initial.length(); i++) {
            char ch = initial.charAt(i);
            if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == ' ') {
                fixed += ch;
            }
        }
        initial = fixed;

        if (!this.dictionary.contains(initial)) {
            this.dictionary.add(0, initial);
        }
        if (n != initial.length()
                && !temp.next.containsKey(initial.toLowerCase().charAt(parentLength))) {
            temp.next.put((initial.toLowerCase().charAt(parentLength)),
                    new TrieNode(origWord, node));
            temp = temp.next.get(initial.toLowerCase().charAt(parentLength));
            parseThrough(temp, origWord, n + 1);

        }

        if (n !=  initial.length()
                && temp.next.containsKey(initial.toLowerCase().
                charAt(parentLength))) {
            temp.next.get(initial.toLowerCase().charAt(parentLength)).
                    dictionary.add(0, origWord);
            temp = temp.next.get(initial.toLowerCase().charAt(parentLength));
            parseThrough(temp, origWord, n + 1);
        }


    }

    public void placeLocations(MapNode store, TrieNode node,
                                String initial, Integer n, String origWord) {
        TrieNode temp = node;
        int parentLength = n;
        initial = initial.toLowerCase();

        if (n == initial.length() - 1) {
            node.addNewLocation(store);
        }

        if (n < initial.length() - 1  && temp.next.containsKey(initial.
                toLowerCase().charAt(n))) {
            temp = temp.next.get(initial.toLowerCase().charAt(n));
            placeLocations(store, temp, initial, n + 1, origWord);
        }


    }


    public LinkedList<String> getNodesLocations(TrieNode node, String initial, int n) {
        TrieNode temp = node;
        n =  1;
        LinkedList<String> answer = node.next.get(initial.charAt(n)).dictionary;

        if (initial.length() > 2) {
            temp = node.next.get(initial.charAt(n));
            n++;
            while (n < initial.length()) {
                temp = temp.next.get(initial.charAt(n));
                answer = temp.dictionary;
                n++;
            }
        }

        return answer;
    }

}

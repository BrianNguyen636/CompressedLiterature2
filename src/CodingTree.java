import java.util.*;
/**
@author Brian Nguyen
 */
public class CodingTree {
    MyHashTable<String, String> codes = new MyHashTable<>(32768);
    List<Byte> bits = new ArrayList<>();
    PriorityQueue<Node> queue;
    MyHashTable<String, Integer> freqTable;
    static final String characterSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'-";

    public CodingTree(String message) {

        frequencyCount(message);

        populateQueue();

        mergeTrees();

        mapCodes(queue.peek());

        System.out.println("Encoding");

        BitSet bitset = new BitSet();
        int x = 0;
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);

            if (characterSet.contains(String.valueOf(c))) {
                s.append(c);
            } else {
                if (s.length() > 0) {
                    String word = String.valueOf(s);
                    String codeString = codes.get(word);
                    for (int j = 0; j < codeString.length(); j++) {
                        bitset.set(x + j, codeString.charAt(j) != '0');
                    }
                    x += codeString.length();
                    s = new StringBuilder();
                }

                String codeString = codes.get(String.valueOf(c));
                for (int j = 0; j < codeString.length(); j++) {
                    bitset.set(x + j, codeString.charAt(j) != '0');
                }
                x += codeString.length();
            }
        }
        byte[] bitArray = bitset.toByteArray();
        for (byte b : bitArray) {
            bits.add(b);
        }
    }

    private void mapCodes(Node tree) {
        mapCodes(tree, "");
    }

    /*
    Recursive function, if left and right are null, then map code to character.
     */
    private void mapCodes(Node current, String code) {
        if (current.left == null && current.right == null) {
            codes.put(current.word, code);
        } else {
            if (current.left != null) {
                mapCodes(current.left, code + "0");
            }
            if (current.right != null) {
                mapCodes(current.right, code + "1");
            }
        }
    }
    private void mergeTrees() {
        while (queue.size() != 1) {
            Node left = queue.poll();
            Node right = queue.poll();
            Node root = new Node(left.weight + right.weight);
            root.left = left;
            root.right = right;
            queue.add(root);
        }
    }

    private void populateQueue() {
        queue = new PriorityQueue<>(freqTable.buckets, new AscendingComparator());
        for (int i = 0; i < freqTable.buckets; i++) {
            if (freqTable.bucketList.get(i) != null) {
                queue.add(new Node(freqTable.bucketList.get(i).key, freqTable.bucketList.get(i).value));
            }
        }
    }
    private void frequencyCount(String message) {
        freqTable = new MyHashTable<>(32768);
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            String c = String.valueOf(message.charAt(i));

            if (characterSet.contains(c)) {
                s.append(c);
            } else {
                if (s.length() > 0) {

                    String word = String.valueOf(s);
                    if (freqTable.containsKey(word)) {
                        freqTable.put(word, freqTable.get(word) + 1);
                    } else freqTable.put(word, 1);
                    s = new StringBuilder();
                }
                if (freqTable.containsKey(c)) {
                    freqTable.put(c, freqTable.get(c) + 1);
                } else freqTable.put(c, 1);
            }
        }
    }
    class AscendingComparator implements Comparator<Node>  {

        @Override
        public int compare(Node o1, Node o2) {
            return o1.weight - o2.weight;
        }
    }
    class Node {

        String word;
        int weight = 0;
        Node left = null;
        Node right = null;

        Node(String theWord, int theWeight) {
            word = theWord;
            weight = theWeight;
        }
        Node(int theWeight) {
            weight = theWeight;
        }
    }
}


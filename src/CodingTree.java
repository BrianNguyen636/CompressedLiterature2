import java.util.*;
/*
@Author Brian Nguyen
 */
public class CodingTree {
    Map<String, String> codes = new HashMap<>();
    List<Byte> bits = new ArrayList<>();
    PriorityQueue<Node> queue;
    Map<Character, Integer> freqMap;

    public CodingTree(String message) {

        frequencyCount(message);

        populateQueue();

        mergeTrees();

        mapCodes(queue.peek());

        System.out.println("Encoding");

        BitSet bitset = new BitSet();
        int x = 0;
        for (int i = 0; i < message.length(); i++) {
            Character c = message.charAt(i);

            String codeString = codes.get(c);

            for (int j = 0; j < codeString.length(); j++) {
                bitset.set(x + j, codeString.charAt(j) != '0');
            }
            x += codeString.length();
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
            codes.put(current.character, code);
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
        queue = new PriorityQueue<>(freqMap.size(), new AscendingComparator());
        for (Character c : freqMap.keySet()) {
            queue.add(new Node(c,freqMap.get(c)));
        }
    }
    //EDIT NEEDED
    private void frequencyCount(String message) {
        freqMap = new HashMap<>();
        for (int i = 0; i < message.length(); i++) {
            Character c = message.charAt(i);
            if (!freqMap.containsKey(c)) {
                freqMap.put(c, 1);
            } else {
                freqMap.put(c, freqMap.get(c) + 1);
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


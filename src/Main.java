import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
@author Brian Nguyen
 */
public class Main {

    public static void main(String[] args) {
//        testCodingTree();
//        testMyHashTable();


        long start = System.currentTimeMillis();
        File file = new File(args[0]);
        String text = "";
        long filesize = 0;
        try {
            System.out.println("Reading input");


            text = new String(Files.readAllBytes(file.toPath()));

            CodingTree tree = new CodingTree(text);

            File codes = new File("codes.txt");
            OutputStream codeWriter = new FileOutputStream(codes);
            codeWriter.write(tree.codes.toString().getBytes(StandardCharsets.UTF_8));

            File compressed = new File("compressed.txt");
            System.out.println("Writing to file");

            OutputStream writer = new FileOutputStream(compressed);

            byte[] bit = new byte[tree.bits.size()];
            for (int i = 0; i < tree.bits.size(); i++) {
                bit[i] = tree.bits.get(i);
            }
            writer.write(bit);
            writer.close();
            filesize = compressed.length() / 1024;

            System.out.println();
            tree.codes.stats();
            System.out.println();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("Original file size: " + file.length() / 1024 + " kibibytes.");
        System.out.println("Compressed file size: " + filesize + " kibibytes.");
        System.out.println("Runtime: " + (end - start) / 1000 + " seconds. (" +(end - start)+" ms)");
    }

    public static void testCodingTree() {
        CodingTree test = new CodingTree("I drift tonight carried by the waves, I will just be a black sea." +
                "I drift tonight a finite one, and the infinite one.");
    }
    public static void testMyHashTable() {
        MyHashTable<String, String> hashTable = new MyHashTable<>(10);
        System.out.println(hashTable);
        hashTable.put("Sheep", "Tsunomaki");
        System.out.println(hashTable);
        System.out.println("Contains sheep: " +  hashTable.containsKey("Sheep"));
        System.out.println("Changing sheep to Watame");
        hashTable.put("Sheep", "Watame");
        System.out.println(hashTable);
        System.out.println("Adding robot");
        hashTable.put("Robot", "Roboco");
        System.out.println(hashTable);
        System.out.println("Value of Robot: " + hashTable.get("Robot"));
        hashTable.put("Tako", "Ina");
        System.out.println(hashTable);
        hashTable.put("Duck", "Subaru");
        System.out.println(hashTable);
        hashTable.stats();
    }
}

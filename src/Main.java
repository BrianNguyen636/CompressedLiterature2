import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/*
@Author Brian Nguyen
 */
public class Main {

    public static void main(String[] args) {
//        testCodingTree();
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
        CodingTree test = new CodingTree("drift tonight");
    }
}

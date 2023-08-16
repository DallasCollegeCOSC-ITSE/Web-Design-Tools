package SetsMaps.CountingKeywords;

import java.io.File;
import java.util.*;

public class CountKeywords {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner (System.in);

        System.out.println("Enter a java source file: ");

        String filename = in.nextLine();

        File file = new File(filename);

        if(file.exists())
        {
            System.out.println("The number of keywords in " + filename
            + " is " + countKeywords(file));
        }
    }

    public static int countKeywords(File file) throws Exception {
        // Every Keyword in java
        String[] keywordString = {"abstract", "assert", "boolean", "break", "byte", ""
                + "case", "catch", "char", "class", "const",
                "continue", "default", "do", "double", "else", "enum",
                "extends", "for", "final", "finally",
                "float", "goto", "if", "implements", "import",
                "instanceof", "int", "interface", "long", "native",
                "new", "package", "private", "protected", "public",
                "return", "short", "static", "stricfp", "super",
                "switch", "synchronized", "this", "throw", "throws",
                "transient", "try", "void", "volatile", "while", "true",
                "false", "null"};

        Set<String> keywordSet = new HashSet<>(Arrays.asList(keywordString));

        int count = 0;

        Scanner input = new Scanner(file);

        while (input.hasNext()) {
            String word = input.next();
            if (keywordSet.contains(word))
                count++;
        }
        return count;
    }
}

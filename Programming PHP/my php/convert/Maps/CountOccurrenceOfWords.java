package SetsMaps.CountOccurrences;

import java.util.Map;
import java.util.TreeMap;

public class CountOccurrenceOfWords {

    public static void main(String[] args) {
        // Set text in a string
        String text = "Good morning. Have a good class. "
                + "Have a good visit, Have fun!";

        // Create a TreeMap to hold words as key and count as value
        Map<String, Integer> map = new TreeMap<>();

       String[] words = text.split("[\\s+\\p{P}]");
        for (int i = 0; i < words.length; i++) {
            String key = words[i].toLowerCase();

            if(key.length() > 0)
                if(!map.containsKey(key))
                    map.put(key,i);
            else
                {
                    int value = map.get(key);
                    value++;
                    map.put(key, value);
                }
        }
        map.forEach((k,v) -> System.out.println(k +"\t" +v));
    }
}

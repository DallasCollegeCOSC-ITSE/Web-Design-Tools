package SetsMaps.Map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class UsingHashMapAndTreeMap {
    public static void main(String[] args) {
        // Create a HashMap
        Map<String, Integer> hashMap = new HashMap<>();

        hashMap.put("Smith",30);
        hashMap.put("Anderson",31);
        hashMap.put("Lewis",29);
        hashMap.put("Anderson",29);
        System.out.println("Display entries in HasMap");
        System.out.println(hashMap +"\n");

        //Create a TreeMap from the proceding HashMap
        Map<String,Integer> treeMap = new TreeMap<>(hashMap);
        System.out.println("Display entries in ascending order of key");
        System.out.println(treeMap);

        // Create a LinkedHashMap
        Map<String, Integer>linkedHashMap = new LinkedHashMap<>(16,0.75f,true);

        linkedHashMap.put("Smith",30);
        linkedHashMap.put("Anderson",31);
        linkedHashMap.put("Lewis",29);
        linkedHashMap.put("Anderson",29);
        System.out.println("\nThe age for " +"Lewis is "
        + linkedHashMap.get("Lewis"));

        System.out.println("Display entries for LinkedHashMap");
        System.out.println(linkedHashMap);

        //Display each entry with name and age
        System.out.println("\nNames and ages are " );
        treeMap.forEach((name,age) -> System.out.println(name + ": " + age + " "));
    }

}

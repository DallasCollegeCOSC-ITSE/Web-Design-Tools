package SetsMaps.Sets;

import java.util.HashSet;
import java.util.Set;

public class TestHashSet {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();

        //Add strings to the set

        set.add("London");
        set.add("Paris");
        set.add("New York");
        set.add("San Francisco");
        set.add("Beijing");
        set.add("New York");

        System.out.println(set);

        //Display the elements in the hash set
        for(String s: set)
        System.out.println(s.toUpperCase() + " ");

        // Process the elements using a forEach method
        System.out.println();

        set.forEach(e -> System.out.println(e.toLowerCase() + " "));
    }



}

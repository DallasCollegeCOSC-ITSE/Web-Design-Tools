package SetsMaps.Sets;

import java.util.LinkedHashSet;
import java.util.Set;

public class TestLinkedHashSet {

    public static void main(String[] args) {
        // create a hash set
        Set<String> set = new LinkedHashSet<>();

        // Add strings to the set
        set.add("London");
        set.add("Paris");
        set.add("New York");
        set.add("San Francisco");
        set.add("New York");

        System.out.println(set);

        //Display the elements in the hash set
        for(String element: set)
            System.out.println(element.toLowerCase() + " ");
    }
}

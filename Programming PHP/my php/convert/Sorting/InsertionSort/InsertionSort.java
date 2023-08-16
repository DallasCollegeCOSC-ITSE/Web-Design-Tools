package Sorting.InsertionSort;

import java.util.Arrays;

public class InsertionSort {
    /** The method for sorting the ints*/
    public static void main(String[] args) {
        testInsertionSort();
    }

    public static void testInsertionSort() {
        int[][] testCases = {
                {},                         // Empty Array
                {5},                        // Array with a Single Element
                {1, 2, 3, 4, 5},             // Array with Already Sorted Elements
                {5, 4, 3, 2, 1},             // Array with Reverse Sorted Elements
                {4, 2, 3, 2, 1},             // Array with Duplicate Elements
                {-4, 2, -1, 5, 0},           // Array with Negative Numbers
                {1000, -500, 200, -1000, 0, 300, 150},  // Array with Large Range of Numbers
                {2, 2, 2, 2, 2},             // Array with Repetitive Elements
                {9, 8, 7, 6, 5, 4, 3, 2, 1, 0},       // Array with Large Number of Elements
                {5, 3, 0, 7, 0, 2, 4}        // Array with Null Values
        };

        for (int i = 0; i < testCases.length; i++) {
            int[] arr = testCases[i];
            System.out.println("Test Case " + (i + 1) + ":");
            System.out.println("Before sorting: " + Arrays.toString(arr));
            insertionSort(arr);
            System.out.println("After sorting: " + Arrays.toString(arr));
            System.out.println();
        }
    }

    //O(n^2)
    public static void insertionSort(int[]list)
    {
        for (int i = 0; i < list.length; i++) {
            /**Insert list[i] into a sorted sublist
             * list[0..i-1] so that list[0..i] is sorted */

            int currentElement = list[i];
            int k;
            for(k=i-1;k>=0 && list[k] > currentElement; k--)
                list[k+1] = list[k];

            // Insert the current element into list[k+1]
            list[k+1] = currentElement;
        }
    }
}


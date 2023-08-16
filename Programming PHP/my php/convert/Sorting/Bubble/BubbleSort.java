package Sorting.BubbleSort;

public class BubbleSort {

    public static void basicBubble(int[] list)
    {
        for (int i = 0; i < list.length; i++) {
            // perform the kth pass
            for (int j = 0; j < list.length; j++) {
                if(list[i] > list[i+1])
                {
                    int temp = list[j];
                    list[j] = list[j+1];
                    list[j+1] = temp;
                }
            }

        }
    }

    public static void improvedBubble(int[] list)
    {
        boolean needNextPass = true;
        for (int i = 0; i < list.length && needNextPass; i++) {
            //Array may be sorted and next pass not needed
            needNextPass = false;
            // Perform the kth pass
            for (int j = 0; j < list.length-i; j++) {
                if (list[i] > list[i+1]) {
                    // Swap list[i] with list[i+1]
                    int temp = list[j];
                    list[j] = list[j+1];
                    list[j+1] = temp;
                }

            }

        }
    }

    /*the best-case time for a bubble sort is O(n) **/
    /* the worst-case time for a bubble sort is O(n^2) */

    public static void bubbleSort(int[] list)
    {	//O(n^2)
        boolean needNextPass = true;

        for (int i = 1; i < list.length && needNextPass; i++) {
            // Array may be sorted and next pass not needed
            needNextPass = false;
            for (int j = 0; j < list.length-i; j++)
                if(list[j] > list[j+1])
                {
                    // Swap list[i] with list[i+1]
                    int temp = list[j];
                    list[j] = list[j+1];
                    list[j+1] = temp;

                    needNextPass = true; // Next pass still needed
                }
        }
    }
    /** A test method */
    public static void main(String[] args)
    {
        int[] list = {2,3,2,5,6,1,-2,3,14,12};
        bubbleSort(list);
        for (int i = 0; i < list.length; i++) {
            System.out.println(list[i]+" ");
        }
    }


}

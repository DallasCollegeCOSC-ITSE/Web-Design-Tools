package Sorting.Heap;
import java.util.Comparator;

public class HeapSort {

    /** Heap sort method*/
    public static <E> void heapSort(E[] list)
    {
        // Create a Heap of integers
        heapSort(list, (e1,e2) -> ((Comparable<E>)e1).compareTo(e2));
    }

    /** Heap sort method */
    public static<E> void heapSort(E[] list, Comparator<E> c)
    {
        // Create a Heap of integers
        Heap<E> heap = new Heap<>(c);

        // Add elements to the heap
        for (int i = 0; i < list.length; i++)
            heap.add(list[i]);

        // Remove elements to the heap
            for (int j = list.length-1; j >=0; j--)
                list[j] = heap.remove();
    }

    public static void main(String[] args) {
        Integer[] list = {-44,-5,-3,3,3,1,-4,-0,1,2,4,5,-53};
        
        heapSort(list);

        for (int i = 0; i < list.length; i++)
            System.out.println(list[i]+"");
    }
}

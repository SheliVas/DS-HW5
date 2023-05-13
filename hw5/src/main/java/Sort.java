import java.util.Arrays;

public class Sort<T extends Comparable<T>> {
    

    public void quickSort(T[] array) {
      // call for sorting the array.  
    }

    // private void quickSort(T[] array, int low, int high) {
    //  if (high - low > 2) {
    //     int pivot = partition(array, low, high);
    //     quickSort(array, low, pivot - 1);
    //     quickSort(array, pivot + 1, high);
    //  } else {
    //     insertionSort(array, low, high);
    //  }
    // }
    
    public void quickSortRecitation(T[] array) {
        // Implementation goes here
    }

    // explain why insertion sort
    public void insertionSort(T[] array, int low, int high) {
        for (int i = low; i <= high; i++) {
            T val = array[i];
            int j = i-1;
            while (j >= low && array[j].compareTo(val) > 0) {
                array[j+1] = array[j];
                j--;
            }
            array[j+1] = val;
        }
    }


 // ---------------------------------------------- MergeSort--------------------

 public void mergeSortRecursive(T[] array) {
    // Implementation goes here
}

    
public void mergeSortIterative(T[] array) {
    // Implementation goes here
}


// -------------------------------- RadixSort ----------------------

public static void radixSort(int[] array, int base) {
    // Implementation goes here
}




//  --------------------------------- TESTING SECTION ----------------------------------------------------------------

  // Insertion Sort Test
  public static void testInsertionSort() {
    Integer[] array1 = {5, 2, 9, 1, 7};
    Integer[] array2 = {1, 2, 3, 4, 5};
    Integer[] array3 = {5, 4, 3, 2, 1};
    Integer[] array4 = {2, 4, 6, 8, 10};
    Integer[] array5 = {10, 8, 6, 4, 2};
    Integer[] array6 = {1};
    Integer[] array7 = {};

    Sort<Integer> sorter = new Sort<>();

    System.out.println("Before sorting: " + Arrays.toString(array1));
    sorter.insertionSort(array1, 0, array1.length - 1);
    System.out.println("After sorting: " + Arrays.toString(array1));

    System.out.println("Before sorting: " + Arrays.toString(array2));
    sorter.insertionSort(array2, 0, array2.length - 1);
    System.out.println("After sorting: " + Arrays.toString(array2));

    System.out.println("Before sorting: " + Arrays.toString(array3));
    sorter.insertionSort(array3, 0, array3.length - 1);
    System.out.println("After sorting: " + Arrays.toString(array3));

    System.out.println("Before sorting: " + Arrays.toString(array4));
    sorter.insertionSort(array4, 0, array4.length - 1);
    System.out.println("After sorting: " + Arrays.toString(array4));

    System.out.println("Before sorting: " + Arrays.toString(array5));
    sorter.insertionSort(array5, 0, array5.length - 1);
    System.out.println("After sorting: " + Arrays.toString(array5));

    System.out.println("Before sorting: " + Arrays.toString(array6));
    sorter.insertionSort(array6, 0, array6.length - 1);
    System.out.println("After sorting: " + Arrays.toString(array6));

    System.out.println("Before sorting: " + Arrays.toString(array7));
    sorter.insertionSort(array7, 0, array7.length - 1);
    System.out.println("After sorting: " + Arrays.toString(array7));
}
}

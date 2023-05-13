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
    Integer[] array = {5, 2, 9, 1, 7};
    Sort<Integer> sorter = new Sort<>();
    
    System.out.println("Before sorting: " + Arrays.toString(array));
    
    sorter.insertionSort(array, 0, array.length - 1);
    
    System.out.println("After sorting: " + Arrays.toString(array));
}
}

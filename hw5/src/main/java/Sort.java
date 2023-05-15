import java.util.Arrays;

public class Sort<T extends Comparable<T>> {

    /**
     * Sorts the given array using the QuickSort algorithm.
     *
     * @param array the array to be sorted
     */
    public void quickSort(T[] array) {
        quickSort(array, 0, array.length - 1);
    }

    /**
     * Recursively sorts the subarray within the specified range using the QuickSort
     * algorithm.
     *
     * @param array the array to be sorted
     * @param low   the starting index of the subarray
     * @param high  the ending index of the subarray
     */
    private void quickSort(T[] array, int low, int high) {
        if (high > low) {
            int pivot = partition(array, low, high);
            quickSort(array, low, pivot - 1);
            quickSort(array, pivot + 1, high);
        } else {
            insertionSort(array, low, high);
        }
    }

    /**
     * Sorts the given array using the QuickSort algorithm based on the recitation
     * code.
     *
     * @param array the array to be sorted
     */
    public void quickSortRecitation(T[] array) {
        // Implementation goes here
    }

    /**
     * Partitions the array around a pivot element for the QuickSort algorithm.
     *
     * @param array the array to be partitioned
     * @param low   the starting index of the partition
     * @param high  the ending index of the partition
     * @return the pivot index
     */
    private int partition(T[] array, int low, int high) {
        T pivot = array[high];
        int i = low - 1;
        for (int j = low; j <= high - 1; j++) {
            if (array[j].compareTo(pivot) <= 0) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, high);
        return i + 1;
    }

    /**
     * Swaps two elements in the array.
     *
     * @param array the array containing the elements
     * @param i     the index of the first element to be swapped
     * @param j     the index of the second element to be swapped
     */
    private void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * Sorts the given subarray using the InsertionSort algorithm.
     *
     * @param array the array to be sorted
     * @param low   the starting index of the subarray
     * @param high  the ending index of the subarray
     */
    private void insertionSort(T[] array, int low, int high) {
        for (int i = low; i <= high; i++) {
            T val = array[i];
            int j = i - 1;
            while (j >= low && array[j].compareTo(val) > 0) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = val;
        }
    }

    // ---------------------------------------------- MergeSort--------------------

    public void mergeSortRecursive(T[] array) {

        if (array.length <= 1) {
            return; // Array of size 1 or less is already sorted
        }

        int mid = array.length / 2;
        T[] leftArray = Arrays.copyOfRange(array, 0, mid);
        T[] rightArray = Arrays.copyOfRange(array, mid, array.length);

        mergeSortRecursive(leftArray);
        mergeSortRecursive(rightArray);

        merge(array, leftArray, rightArray);
    }

    private void merge(T[] array, T[] leftArray, T[] rightArray) {
        int leftIndex = 0;
        int rightIndex = 0;
        int mergedIndex = 0;

        while (leftIndex < leftArray.length && rightIndex < rightArray.length) {
            if (leftArray[leftIndex].compareTo(rightArray[rightIndex]) <= 0) {
                array[mergedIndex] = leftArray[leftIndex];
                leftIndex++;
            } else {
                array[mergedIndex] = rightArray[rightIndex];
                rightIndex++;
            }
            mergedIndex++;
        }

        while (leftIndex < leftArray.length) {
            array[mergedIndex] = leftArray[leftIndex];
            leftIndex++;
            mergedIndex++;
        }

        while (rightIndex < rightArray.length) {
            array[mergedIndex] = rightArray[rightIndex];
            rightIndex++;
            mergedIndex++;
        }

    }

    public void mergeSortIterative(T[] array) {

        if (array.length <= 1) {
            return; // Array of size 1 or less is already sorted
        }

        int n = array.length;
        T[] tempArray = Arrays.copyOf(array, n);
        T[] source = array;
        T[] destination = tempArray;

        for (int currSize = 1; currSize < n; currSize *= 2) {
            for (int leftStart = 0; leftStart < n; leftStart += 2 * currSize) {
                int mid = Math.min(leftStart + currSize - 1, n - 1);
                int rightEnd = Math.min(leftStart + 2 * currSize - 1, n - 1);

                merge(source, destination, leftStart, mid, rightEnd);
            }

            // Swap source and destination arrays
            T[] temp = source;
            source = destination;
            destination = temp;
        }

        // Copy the sorted elements from the source array back to the original array
        if (array != source) {
            System.arraycopy(source, 0, array, 0, n);
        }
    }

    private void merge(T[] source, T[] destination, int leftStart, int mid, int rightEnd) {
        int leftIndex = leftStart;
        int rightIndex = mid + 1;
        int mergedIndex = leftStart;

        while (leftIndex <= mid && rightIndex <= rightEnd) {
            if (source[leftIndex].compareTo(source[rightIndex]) <= 0) {
                destination[mergedIndex] = source[leftIndex];
                leftIndex++;
            } else {
                destination[mergedIndex] = source[rightIndex];
                rightIndex++;
            }
            mergedIndex++;
        }

        // Copy the remaining elements from the left subarray, if any
        while (leftIndex <= mid) {
            destination[mergedIndex] = source[leftIndex];
            leftIndex++;
            mergedIndex++;
        }

        // Copy the remaining elements from the right subarray, if any
        while (rightIndex <= rightEnd) {
            destination[mergedIndex] = source[rightIndex];
            rightIndex++;
            mergedIndex++;
        }

    }

    // -------------------------------- RadixSort ----------------------

    public static void radixSort(int[] array, int base) {
        // Implementation goes here
    }

    // --------------------------------- TESTING SECTION
    // ----------------------------------------------------------------

    // Insertion Sort Test
    public static void testInsertionSort() {
        // Test case 1: Empty array
        Integer[] array1 = {};
        Sort<Integer> sorter = new Sort<>();
        System.out.println("Before sorting: " + Arrays.toString(array1));
        sorter.insertionSort(array1, 0, array1.length - 1);
        System.out.println("After sorting: " + Arrays.toString(array1));
        // Expected output: []

        // Test case 2: Array with a single element
        Integer[] array2 = { 5 };
        System.out.println("Before sorting: " + Arrays.toString(array2));
        sorter.insertionSort(array2, 0, array2.length - 1);
        System.out.println("After sorting: " + Arrays.toString(array2));
        // Expected output: [5]

        // Test case 3: Array with repeated elements
        Integer[] array3 = { 5, 2, 9, 2, 7, 5 };
        System.out.println("Before sorting: " + Arrays.toString(array3));
        sorter.insertionSort(array3, 0, array3.length - 1);
        System.out.println("After sorting: " + Arrays.toString(array3));
        // Expected output: [2, 2, 5, 5, 7, 9]

        // Test case 4: Array already sorted in descending order
        Integer[] array4 = { 9, 7, 5, 3, 1 };
        System.out.println("Before sorting: " + Arrays.toString(array4));
        sorter.insertionSort(array4, 0, array4.length - 1);
        System.out.println("After sorting: " + Arrays.toString(array4));
        // Expected output: [1, 3, 5, 7, 9]

        // Test case 5: Array with negative numbers
        Integer[] array5 = { -5, 2, -9, 1, -7 };
        System.out.println("Before sorting: " + Arrays.toString(array5));
        sorter.insertionSort(array5, 0, array5.length - 1);
        System.out.println("After sorting: " + Arrays.toString(array5));
        // Expected output: [-9, -7, -5, 1, 2]

        // Test case 6: Array with large number of elements
        Integer[] array6 = { 9, 2, 5, 1, 7, 4, 8, 3, 6 };
        System.out.println("Before sorting: " + Arrays.toString(array6));
        sorter.insertionSort(array6, 0, array6.length - 1);
        System.out.println("After sorting: " + Arrays.toString(array6));
        // Expected output: [1, 2, 3, 4, 5, 6, 7, 8, 9]
    }

    // quickSort test
    public static void testQuickSort() {
        // Test case 1: Empty array
        Integer[] array1 = {};
        Sort<Integer> sorter = new Sort<>();
        System.out.println("Before sorting: " + Arrays.toString(array1));
        sorter.quickSort(array1);
        System.out.println("After sorting: " + Arrays.toString(array1));
        // Expected output: []

        // Test case 2: Array with a single element
        Integer[] array2 = { 5 };
        System.out.println("Before sorting: " + Arrays.toString(array2));
        sorter.quickSort(array2);
        System.out.println("After sorting: " + Arrays.toString(array2));
        // Expected output: [5]

        // Test case 3: Array with repeated elements
        Integer[] array3 = { 5, 2, 9, 2, 7, 5 };
        System.out.println("Before sorting: " + Arrays.toString(array3));
        sorter.quickSort(array3);
        System.out.println("After sorting: " + Arrays.toString(array3));
        // Expected output: [2, 2, 5, 5, 7, 9]

        // Test case 4: Array already sorted in descending order
        Integer[] array4 = { 9, 7, 5, 3, 1 };
        System.out.println("Before sorting: " + Arrays.toString(array4));
        sorter.quickSort(array4);
        System.out.println("After sorting: " + Arrays.toString(array4));
        // Expected output: [1, 3, 5, 7, 9]

        // Test case 5: Array with negative numbers
        Integer[] array5 = { -5, 2, -9, 1, -7 };
        System.out.println("Before sorting: " + Arrays.toString(array5));
        sorter.quickSort(array5);
        System.out.println("After sorting: " + Arrays.toString(array5));
        // Expected output: [-9, -7, -5, 1, 2]

        // Test case 6: Array with large number of elements
        Integer[] array6 = { 9, 2, 5, 1, 7, 4, 8, 3, 6 };
        System.out.println("Before sorting: " + Arrays.toString(array6));
        sorter.quickSort(array6);
        System.out.println("After sorting: " + Arrays.toString(array6));
        // Expected output: [1, 2, 3, 4, 5, 6, 7, 8, 9]
    }

    // Recursive MergeSort Test
    public static void testRecursiveMergeSort() {
        Sort<Integer> sorter = new Sort<>();
        // Test case 1
        Integer[] array1 = { 5, 2, 9, 1, 7 };
        System.out.println("Original array: " + Arrays.toString(array1));
        sorter.mergeSortRecursive(array1);
        System.out.println("Sorted array: " + Arrays.toString(array1));

        // Test case 2
        Integer[] array2 = { 10, 4, 8, 3, 6 };
        System.out.println("Original array: " + Arrays.toString(array2));
        sorter.mergeSortRecursive(array2);
        System.out.println("Sorted array: " + Arrays.toString(array2));

        // Test case 3
        Integer[] array3 = { 1, 2, 3, 4, 5 };
        System.out.println("Original array: " + Arrays.toString(array3));
        sorter.mergeSortRecursive(array3);
        System.out.println("Sorted array: " + Arrays.toString(array3));
    }

    // Iterative MergeSort Test
    public static void testIterativeMergeSort() {
        Sort<Integer> sorter = new Sort<>();

        // Test case 1
        Integer[] array1 = { 5, 2, 9, 1, 7 };
        System.out.println("Original array: " + Arrays.toString(array1));
        sorter.mergeSortIterative(array1);
        System.out.println("Sorted array: " + Arrays.toString(array1));

        // Test case 2
        Integer[] array2 = { 10, 4, 8, 3, 6 };
        System.out.println("Original array: " + Arrays.toString(array2));
        sorter.mergeSortIterative(array2);
        System.out.println("Sorted array: " + Arrays.toString(array2));

        // Test case 3
        Integer[] array3 = { 1, 2, 3, 4, 5 };
        System.out.println("Original array: " + Arrays.toString(array3));
        sorter.mergeSortIterative(array3);
        System.out.println("Sorted array: " + Arrays.toString(array3));
    }

}

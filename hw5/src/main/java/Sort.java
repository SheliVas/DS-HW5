import java.util.Arrays;

public class Sort<T extends Comparable<T>> {

    private T[] mergedArray; // Temporary array for merging - used in mergeSort
    private int naiveSortThreshold; // Threshold for using naive sorting algorithms

    /**
     * Sets the threshold for using naive sorting algorithms.
     *
     * @param threshold the threshold value
     */
    public void setNaiveSortThreshold(int threshold) {
        naiveSortThreshold = threshold;
    }

    /**
     * Sorts the given array using the QuickSort algorithm.
     *
     * @param array the array to be sorted
     */
    public void quickSortClass(T[] array) {
        quickSortLec(array, 0, array.length - 1);
    }

    /**
     * Recursively sorts the subarray within the specified range using the QuickSort
     * algorithm.
     *
     * @param array the array to be sorted
     * @param low   the starting index of the subarray
     * @param high  the ending index of the subarray
     */
    private void quickSortLec(T[] array, int low, int high) {
        if (high - low > naiveSortThreshold) {
            int pivot = partitionLec(array, low, high);
            quickSortLec(array, low, pivot - 1);
            quickSortLec(array, pivot + 1, high);
        } else {
            insertionSort(array, low, high);
        }
    }

    private int partitionLec(T[] array, int low, int high) {
        T pivot = array[high];
        int j = high;
        int i = low - 1;

        while (true) {
            do {
                j--;
            } while (j >= low && array[j].compareTo(pivot) > 0);

            do {
                i++;
            } while (i <= high && array[i].compareTo(pivot) <= 0);

            if (i < j) {
                swap(array, i, j);
            } else {
                swap(array, j + 1, high);
                return j + 1;
            }
        }
    }

    /**
     * Sorts the given array using the QuickSort algorithm based on the recitation
     * code.
     *
     * @param array the array to be sorted
     */
    public void quickSortRecitation(T[] array) {
        quickSortRec(array, 0, array.length - 1);
    }

    /**
     * Recursively sorts the subarray within the specified range using the QuickSort
     * algorithm.
     *
     * @param array the array to be sorted
     * @param low   the starting index of the subarray
     * @param high  the ending index of the subarray
     */
    private void quickSortRec(T[] array, int low, int high) {
        if (low < high) {
            if (high - low >= naiveSortThreshold) {
                int pivot = partitionRec(array, low, high);
                quickSortRec(array, low, pivot - 1);
                quickSortRec(array, pivot + 1, high);
            } else {
                insertionSort(array, low, high);
            }
        }
    }

    /**
     * Partitions the array around a pivot element for the QuickSort algorithm.
     *
     * @param array the array to be partitioned
     * @param low   the starting index of the partition
     * @param high  the ending index of the partition
     * @return the pivot index
     */
    private int partitionRec(T[] array, int low, int high) {
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
        mergedArray = Arrays.copyOf(array, array.length);
        mergeSortRecursive(array, 0, array.length - 1);
    }

    private void mergeSortRecursive(T[] array, int low, int high) {
        if (high - low <= naiveSortThreshold) {
            insertionSort(array, low, high);
        } else {
            int mid = low + (high - low) / 2;
            mergeSortRecursive(array, low, mid);
            mergeSortRecursive(array, mid + 1, high);

            // Optimize by checking if merging is necessary
            if (array[mid].compareTo(array[mid + 1]) > 0) {
                mergeR(array, low, mid, high);
            }
        }
    }

    private void mergeR(T[] array, int low, int mid, int high) {
        int leftIndex = low;
        int rightIndex = mid + 1;
        int mergedIndex = low;

        while (leftIndex <= mid && rightIndex <= high) {
            if (array[leftIndex].compareTo(array[rightIndex]) <= 0) {
                mergedArray[mergedIndex++] = array[leftIndex++];
            } else {
                mergedArray[mergedIndex++] = array[rightIndex++];
            }
        }

        while (leftIndex <= mid) {
            mergedArray[mergedIndex++] = array[leftIndex++];
        }

        while (rightIndex <= high) {
            mergedArray[mergedIndex++] = array[rightIndex++];
        }

        // Copy merged elements back to the original array
        System.arraycopy(mergedArray, low, array, low, high - low + 1);
    }

    public void mergeSortIterative(T[] array) {
        int n = array.length;
        T[] tempArray = Arrays.copyOf(array, n);
        T[] source = array;
        T[] destination = tempArray;

        for (int currSize = 1; currSize < n; currSize *= 2) {
            for (int leftStart = 0; leftStart < n; leftStart += 2 * currSize) {
                int mid = Math.min(leftStart + currSize - 1, n - 1);
                int rightEnd = Math.min(leftStart + 2 * currSize - 1, n - 1);

                if (mid < rightEnd) {
                    mergeI(source, destination, leftStart, mid, rightEnd);
                }
            }

            // Swap the source and destination arrays
            T[] temp = source;
            source = destination;
            destination = temp;
        }

        // If the last merge was performed on the tempArray, copy the sorted elements
        // back to the original array
        if (source == tempArray) {
            System.arraycopy(tempArray, 0, array, 0, n);
        }
    }

    private void mergeI(T[] source, T[] destination, int leftStart, int mid, int rightEnd) {
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

        // Copy the merged elements back to the source array
        System.arraycopy(destination, leftStart, source, leftStart, rightEnd - leftStart + 1);
    }

    // -------------------------------- RadixSort ----------------------

    /**
     * Sorts an array of integers using Radix Sort algorithm.
     * The sorting is performed in non-decreasing order.
     *
     * @param array the array to be sorted
     * @param base  the base used for representing the numbers (e.g., 10 for
     *              decimal, 2 for binary, 16 for hexadecimal)
     */
    public static void radixSort(int[] array, int base) {
        int max = getMax(array);

        // Apply counting sort for each digit
        for (int exp = 1; max / exp > 0; exp *= base) {
            countingSort(array, exp);
        }
    }

    /**
     * Performs counting sort on the given array based on the specified exponent.
     *
     * @param array the array to be sorted
     * @param exp   the exponent value indicating the current digit being considered
     */
    private static void countingSort(int[] array, int exp) {
        int n = array.length;
        int min = array[0];
        int max = array[0];

        // Find the minimum and maximum values in the array
        for (int i = 1; i < n; i++) {
            if (array[i] < min) {
                min = array[i];
            } else if (array[i] > max) {
                max = array[i];
            }
        }

        // Calculate the range of values
        int range = max - min + 1;
        int[] count = new int[range];
        int[] output = new int[n];

        for (int i = 0; i < n; i++) {
            count[array[i] - min]++;
        }

        for (int i = 1; i < range; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            output[count[array[i] - min] - 1] = array[i];
            count[array[i] - min]--;
        }

        System.arraycopy(output, 0, array, 0, n);
    }

    /**
     * Finds the maximum value in the given array.
     *
     * @param array the array to find the maximum value from
     * @return the maximum value in the array, or Integer.MIN_VALUE if the array is
     *         empty
     */
    private static int getMax(int[] array) {
        if (array.length == 0) {
            return Integer.MIN_VALUE;
        }

        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    // --------------------------------- TESTING SECTION
    // ----------------------------------------------------------------

    // Insertion Sort Test
    public static void testInsertionSort() {
        Sort<Integer> sorter = new Sort<>();
        // Test case 1: Empty array
        Integer[] array1 = {};
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

    // quickSortLec test
    public static void testQuickSortLec() {
        Sort<Integer> sorter = new Sort<>();
        // Test case 1: Empty array
        Integer[] array1 = {};
        System.out.println("Before sorting: " + Arrays.toString(array1));
        sorter.quickSortClass(array1);
        System.out.println("After sorting: " + Arrays.toString(array1));
        // Expected output: []

        // Test case 2: Array with a single element
        Integer[] array2 = { 5 };
        System.out.println("Before sorting: " + Arrays.toString(array2));
        sorter.quickSortClass(array2);
        System.out.println("After sorting: " + Arrays.toString(array2));
        // Expected output: [5]

        // Test case 3: Array with repeated elements
        Integer[] array3 = { 5, 2, 9, 2, 7, 5 };
        System.out.println("Before sorting: " + Arrays.toString(array3));
        sorter.quickSortClass(array3);
        System.out.println("After sorting: " + Arrays.toString(array3));
        // Expected output: [2, 2, 5, 5, 7, 9]

        // Test case 4: Array already sorted in descending order
        Integer[] array4 = { 9, 7, 5, 3, 1 };
        System.out.println("Before sorting: " + Arrays.toString(array4));
        sorter.quickSortClass(array4);
        System.out.println("After sorting: " + Arrays.toString(array4));
        // Expected output: [1, 3, 5, 7, 9]

        // Test case 5: Array with negative numbers
        Integer[] array5 = { -5, 2, -9, 1, -7 };
        System.out.println("Before sorting: " + Arrays.toString(array5));
        sorter.quickSortClass(array5);
        System.out.println("After sorting: " + Arrays.toString(array5));
        // Expected output: [-9, -7, -5, 1, 2]

        // Test case 6: Array with large number of elements
        Integer[] array6 = { 9, 2, 5, 1, 7, 4, 8, 3, 6 };
        System.out.println("Before sorting: " + Arrays.toString(array6));
        sorter.quickSortClass(array6);
        System.out.println("After sorting: " + Arrays.toString(array6));
        // Expected output: [1, 2, 3, 4, 5, 6, 7, 8, 9]
    }

    // quickSortRec test
    public static void testQuickSortRec() {
        Sort<Integer> sorter = new Sort<>();
        // Test case 1: Empty array
        Integer[] array1 = {};
        System.out.println("Before sorting: " + Arrays.toString(array1));
        sorter.quickSortRecitation(array1);
        System.out.println("After sorting: " + Arrays.toString(array1));
        // Expected output: []

        // Test case 2: Array with a single element
        Integer[] array2 = { 5 };
        System.out.println("Before sorting: " + Arrays.toString(array2));
        sorter.quickSortRecitation(array2);
        System.out.println("After sorting: " + Arrays.toString(array2));
        // Expected output: [5]

        // Test case 3: Array with repeated elements
        Integer[] array3 = { 5, 2, 9, 2, 7, 5 };
        System.out.println("Before sorting: " + Arrays.toString(array3));
        sorter.quickSortRecitation(array3);
        System.out.println("After sorting: " + Arrays.toString(array3));
        // Expected output: [2, 2, 5, 5, 7, 9]

        // Test case 4: Array already sorted in descending order
        Integer[] array4 = { 9, 7, 5, 3, 1 };
        System.out.println("Before sorting: " + Arrays.toString(array4));
        sorter.quickSortRecitation(array4);
        System.out.println("After sorting: " + Arrays.toString(array4));
        // Expected output: [1, 3, 5, 7, 9]

        // Test case 5: Array with negative numbers
        Integer[] array5 = { -5, 2, -9, 1, -7 };
        System.out.println("Before sorting: " + Arrays.toString(array5));
        sorter.quickSortRecitation(array5);
        System.out.println("After sorting: " + Arrays.toString(array5));
        // Expected output: [-9, -7, -5, 1, 2]

        // Test case 6: Array with large number of elements
        Integer[] array6 = { 9, 2, 5, 1, 7, 4, 8, 3, 6 };
        System.out.println("Before sorting: " + Arrays.toString(array6));
        sorter.quickSortRecitation(array6);
        System.out.println("After sorting: " + Arrays.toString(array6));
        // Expected output: [1, 2, 3, 4, 5, 6, 7, 8, 9]
    }

    // Recursive MergeSort Test

    public static void testRecursiveMergeSort() {
        Sort<Integer> sorter = new Sort<>();

        // Test case 1: Array with random elements
        Integer[] array1 = { 5, 2, 9, 1, 7 };
        System.out.println("Before sorting: " + Arrays.toString(array1));
        sorter.mergeSortRecursive(array1);
        System.out.println("After sorting: " + Arrays.toString(array1));
        // Expected output: [1, 2, 5, 7, 9]

        // Test case 2: Array with duplicate elements
        Integer[] array2 = { 10, 4, 8, 3, 6, 4, 8 };
        System.out.println("Before sorting: " + Arrays.toString(array2));
        sorter.mergeSortRecursive(array2);
        System.out.println("After sorting: " + Arrays.toString(array2));
        // Expected output: [3, 4, 4, 6, 8, 8, 10]

        // Test case 3: Array with already sorted elements
        Integer[] array3 = { 1, 2, 3, 4, 5 };
        System.out.println("Before sorting: " + Arrays.toString(array3));
        sorter.mergeSortRecursive(array3);
        System.out.println("After sorting: " + Arrays.toString(array3));
        // Expected output: [1, 2, 3, 4, 5]

        // Test case 4: Array with descending order elements
        Integer[] array4 = { 9, 8, 7, 6, 5, 4, 3, 2, 1 };
        System.out.println("Before sorting: " + Arrays.toString(array4));
        sorter.mergeSortRecursive(array4);
        System.out.println("After sorting: " + Arrays.toString(array4));
        // Expected output: [1, 2, 3, 4, 5, 6, 7, 8, 9]

        // Test case 5: Empty array
        Integer[] array5 = {};
        System.out.println("Before sorting: " + Arrays.toString(array5));
        sorter.mergeSortRecursive(array5);
        System.out.println("After sorting: " + Arrays.toString(array5));
        // Expected output: []

        // Test case 6: Array with a single element
        Integer[] array6 = { 1 };
        System.out.println("Before sorting: " + Arrays.toString(array6));
        sorter.mergeSortRecursive(array6);
        System.out.println("After sorting: " + Arrays.toString(array6));
        // Expected output: [1]
    }

    // Iterative MergeSort Test
    public static void testIterativeMergeSort() {
        Sort<Integer> sorter = new Sort<>();

        // Test case 1: Array with random elements
        Integer[] arr1 = { 5, 2, 9, 1, 7 };
        System.out.println("Before sorting: " + Arrays.toString(arr1));
        sorter.mergeSortIterative(arr1);
        System.out.println("After sorting: " + Arrays.toString(arr1));
        // Expected output: [1, 2, 5, 7, 9]

        // Test case 2: Array with duplicate elements
        Integer[] arr2 = { 10, 4, 8, 3, 6, 4, 8 };
        System.out.println("Before sorting: " + Arrays.toString(arr2));
        sorter.mergeSortIterative(arr2);
        System.out.println("After sorting: " + Arrays.toString(arr2));
        // Expected output: [3, 4, 4, 6, 8, 8, 10]

        // Test case 3: Array with already sorted elements
        Integer[] arr3 = { 1, 2, 3, 4, 5 };
        System.out.println("Before sorting: " + Arrays.toString(arr3));
        sorter.mergeSortIterative(arr3);
        System.out.println("After sorting: " + Arrays.toString(arr3));
        // Expected output: [1, 2, 3, 4, 5]

        // Test case 4: Array with descending order elements
        Integer[] arr4 = { 9, 8, 7, 6, 5, 4, 3, 2, 1 };
        System.out.println("Before sorting: " + Arrays.toString(arr4));
        sorter.mergeSortIterative(arr4);
        System.out.println("After sorting: " + Arrays.toString(arr4));
        // Expected output: [1, 2, 3, 4, 5, 6, 7, 8, 9]

        // Test case 5: Empty array
        Integer[] arr5 = {};
        System.out.println("Before sorting: " + Arrays.toString(arr5));
        sorter.mergeSortIterative(arr5);
        System.out.println("After sorting: " + Arrays.toString(arr5));
        // Expected output: []

        // Test case 6: Array with a single element
        Integer[] arr6 = { 1 };
        System.out.println("Before sorting: " + Arrays.toString(arr6));
        sorter.mergeSortIterative(arr6);
        System.out.println("After sorting: " + Arrays.toString(arr6));
        // Expected output: [1]
    }

    // counting sort test
    public static void testCountingSort() {
        // Test case 1: Empty array
        int[] array1 = {};
        Sort<Integer> sorter = new Sort<>();
        System.out.println("Before sorting: " + Arrays.toString(array1));
        sorter.countingSort(array1, 0);
        System.out.println("After sorting: " + Arrays.toString(array1));
        // Expected output: []

        // Test case 2: Array with a single element
        int[] array2 = { 5 };
        System.out.println("Before sorting: " + Arrays.toString(array2));
        sorter.countingSort(array2, 5);
        System.out.println("After sorting: " + Arrays.toString(array2));
        // Expected output: [5]

        // Test case 3: Array with repeated elements
        int[] array3 = { 3, 1, 2, 2, 1, 3 };
        System.out.println("Before sorting: " + Arrays.toString(array3));
        sorter.countingSort(array3, 3);
        System.out.println("After sorting: " + Arrays.toString(array3));
        // Expected output: [1, 1, 2, 2, 3, 3]

        // Test case 4: Array with all elements equal
        int[] array4 = { 5, 5, 5, 5, 5 };
        System.out.println("Before sorting: " + Arrays.toString(array4));
        sorter.countingSort(array4, 5);
        System.out.println("After sorting: " + Arrays.toString(array4));
        // Expected output: [5, 5, 5, 5, 5]

        // Test case 5: Array with negative elements
        int[] array5 = { -5, -2, -9, -1, -7 };
        System.out.println("Before sorting: " + Arrays.toString(array5));
        sorter.countingSort(array5, 0);
        System.out.println("After sorting: " + Arrays.toString(array5));
        // Expected output: [-9, -7, -5, -2, -1]
    }

    // radixSort test

    // public static void testRadixSort() {

    // // Test case 1: Empty array
    // int[] array1 = {};
    // System.out.println("Before sorting: " + Arrays.toString(array1));
    // radixSort(array1, 10);
    // System.out.println("After sorting: " + Arrays.toString(array1));
    // // Expected output: []

    // // Test case 2: Array with a single element
    // int[] array2 = { 5 };
    // System.out.println("Before sorting: " + Arrays.toString(array2));
    // radixSort(array2, 10);
    // System.out.println("After sorting: " + Arrays.toString(array2));
    // // Expected output: [5]

    // // Test case 3: Array with multiple elements
    // int[] array3 = { 9, 7, 5, 3, 1 };
    // System.out.println("Before sorting: " + Arrays.toString(array3));
    // radixSort(array3, 10);
    // System.out.println("After sorting: " + Arrays.toString(array3));
    // // Expected output: [1, 3, 5, 7, 9]

    // // Test case 4: Array with negative values
    // int[] array4 = { -5, 2, -9, 1, -7 };
    // System.out.println("Before sorting: " + Arrays.toString(array4));
    // radixSort(array4, 10);
    // System.out.println("After sorting: " + Arrays.toString(array4));
    // // Expected output: [-9, -7, -5, 1, 2]

    // // Test case 5: Array with duplicate values
    // int[] array5 = { 9, 2, 5, 1, 7, 4, 8, 3, 6 };
    // System.out.println("Before sorting: " + Arrays.toString(array5));
    // radixSort(array5, 10);
    // System.out.println("After sorting: " + Arrays.toString(array5));
    // // Expected output: [1, 2, 3, 4, 5, 6, 7, 8, 9]
    // }

    public static void testRadixSort() {
        // Test case 1: Empty array
        int[] array1 = {};
        System.out.println("Before sorting: " + Arrays.toString(array1));
        radixSort(array1, 10);
        System.out.println("After sorting: " + Arrays.toString(array1));
        // Expected output: []

        // Test case 2: Array with a single element
        int[] array2 = { 5 };
        System.out.println("Before sorting: " + Arrays.toString(array2));
        radixSort(array2, 10);
        System.out.println("After sorting: " + Arrays.toString(array2));
        // Expected output: [5]

        // Test case 3: Array with multiple elements (base 10)
        int[] array3 = { 9, 7, 5, 3, 1 };
        System.out.println("Before sorting: " + Arrays.toString(array3));
        radixSort(array3, 10);
        System.out.println("After sorting: " + Arrays.toString(array3));
        // Expected output: [1, 3, 5, 7, 9]

        // Test case 4: Array with negative values (base 10)
        int[] array4 = { -5, 2, -9, 1, -7 };
        System.out.println("Before sorting: " + Arrays.toString(array4));
        radixSort(array4, 10);
        System.out.println("After sorting: " + Arrays.toString(array4));
        // Expected output: [-9, -7, -5, 1, 2]

        // Test case 5: Array with duplicate values (base 10)
        int[] array5 = { 9, 2, 5, 1, 7, 4, 8, 3, 6 };
        System.out.println("Before sorting: " + Arrays.toString(array5));
        radixSort(array5, 10);
        System.out.println("After sorting: " + Arrays.toString(array5));
        // Expected output: [1, 2, 3, 4, 5, 6, 7, 8, 9]

        // Test case 6: Array with multiple elements (base 2)
        int[] array6 = { 9, 7, 5, 3, 1 };
        System.out.println("Before sorting: " + Arrays.toString(array6));
        radixSort(array6, 2);
        System.out.println("After sorting: " + Arrays.toString(array6));
        // Expected output: [1, 3, 5, 7, 9]

        // Test case 7: Array with multiple elements (base 16)
        int[] array7 = { 9, 7, 15, 3, 1 };
        System.out.println("Before sorting: " + Arrays.toString(array7));
        radixSort(array7, 16);
        System.out.println("After sorting: " + Arrays.toString(array7));
        // Expected output: [1, 3, 7, 9, 15]
    }

}

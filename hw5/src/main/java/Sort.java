import java.util.Arrays;

public class Sort<T extends Comparable<T>> {

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

    /**
     * Partitions the array around a pivot element for the QuickSort algorithm.
     *
     * @param array the array to be partitioned
     * @param low   the starting index of the partition
     * @param high  the ending index of the partition
     * @return the pivot index
     */
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

    // ----------------------------------------------
    // MergeSort----------------------------------

    /**
     * Sorts the array using the Merge Sort algorithm in a recursive manner.
     * This method creates a temporary array to assist in the sorting process.
     *
     * @param array the array to be sorted
     */
    public void mergeSortRecursive(T[] array) {
        int n = array.length;
        T[] tempArray = (T[]) new Comparable[n];
        arrayCopy(array, 0, tempArray, 0, n);
        mergeSortRecursive(array, tempArray, 0, array.length - 1);
    }

    /**
     * Recursively performs the Merge Sort algorithm on a subarray.
     * This method uses a temporary array for merging sorted subarrays.
     *
     * @param array     the array to be sorted
     * @param tempArray the temporary array used for merging
     * @param low       the starting index of the subarray
     * @param high      the ending index of the subarray
     */
    private void mergeSortRecursive(T[] array, T[] tempArray, int low, int high) {
        if (high - low <= naiveSortThreshold) {
            insertionSort(array, low, high);
        } else {
            int mid = low + (high - low) / 2;
            mergeSortRecursive(array, tempArray, low, mid);
            mergeSortRecursive(array, tempArray, mid + 1, high);

            // Optimize by checking if merging is necessary
            if (array[mid].compareTo(array[mid + 1]) > 0) {
                merge(array, tempArray, low, mid, high);
            }
        }
    }

    /**
     * Sorts the array using the Merge Sort algorithm in an iterative manner.
     * This method creates a temporary array to assist in the sorting process.
     *
     * @param array the array to be sorted
     */
    public void mergeSortIterative(T[] array) {
        int n = array.length;
        T[] tempArray = (T[]) new Comparable[n];
        arrayCopy(array, 0, tempArray, 0, n);
        T[] source = array;
        T[] destination = tempArray;

        for (int currSize = 1; currSize < n; currSize *= 2) {
            for (int low = 0; low < n; low += 2 * currSize) {
                int mid = Math.min(low + currSize - 1, n - 1);
                int high = Math.min(low + 2 * currSize - 1, n - 1);

                if (mid < high) {
                    merge(source, destination, low, mid, high);
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
            arrayCopy(tempArray, 0, array, 0, n);
        }
    }

    /**
     * Merges two sorted subarrays into a single sorted subarray.
     * This method is used by the Merge Sort algorithm.
     *
     * @param array     the array containing the subarrays to be merged
     * @param tempArray the temporary array used for merging
     * @param low       the starting index of the first subarray
     * @param mid       the ending index of the first subarray and the starting
     *                  index of the second subarray
     * @param high      the ending index of the second subarray
     */
    private void merge(T[] array, T[] tempArray, int low, int mid, int high) {
        int leftIndex = low;
        int rightIndex = mid + 1;
        int mergedIndex = low;

        while (leftIndex <= mid && rightIndex <= high) {
            if (array[leftIndex].compareTo(array[rightIndex]) <= 0) {
                tempArray[mergedIndex++] = array[leftIndex++];
            } else {
                tempArray[mergedIndex++] = array[rightIndex++];
            }
        }

        while (leftIndex <= mid) {
            tempArray[mergedIndex++] = array[leftIndex++];
        }

        while (rightIndex <= high) {
            tempArray[mergedIndex++] = array[rightIndex++];
        }

        // Copy merged elements back to the original array
        arrayCopy(tempArray, low, array, low, high - low + 1);
    }

    // -------------------------------- RadixSort -------------------------------

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
            countingSort(array, exp, base);
        }
    }

    /**
     * Performs counting sort on the given array based on the specified exponent.
     *
     * @param array the array to be sorted
     * @param exp   the exponent value indicating the current digit being considered
     */
    private static void countingSort(int[] array, int exp, int base) {
        int n = array.length;
        int[] count = new int[base];
        int[] output = new int[n];

        // Count the occurrences of each digit (0 to base-1) at the current digit
        // position
        for (int i = 0; i < n; i++) {
            int digit = ((array[i] / exp) % base + base) % base; // Adjust the digit calculation for negative numbers
            count[digit]++;
        }

        // Calculate the cumulative count
        for (int i = 1; i < base; i++) {
            count[i] += count[i - 1];
        }

        // Build the output array by placing elements in their sorted positions
        for (int i = n - 1; i >= 0; i--) {
            int digit = ((array[i] / exp) % base + base) % base; // Adjust the digit calculation for negative numbers
            output[--count[digit]] = array[i];
        }

        arrayCopy(output, 0, array, 0, n);
    }

    // ----------------------- Helper methods --------------------------

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

    /**
     * Returns the maximum absolute value in the given array.
     * If the array is empty, it returns 0.
     *
     * @param array the array to find the maximum absolute value from
     * @return the maximum absolute value in the array
     */
    private static int getMax(int[] array) {
        if (array.length == 0) {
            return 0;
        }

        int max = Math.abs(array[0]);
        for (int i = 1; i < array.length; i++) {
            int absValue = Math.abs(array[i]);
            if (absValue > max) {
                max = absValue;
            }
        }
        return max;
    }

    /**
     * Copies elements from the source array to the destination array.
     *
     * @param src     the source array to copy elements from
     * @param srcPos  the starting position in the source array
     * @param dest    the destination array to copy elements to
     * @param destPos the starting position in the destination array
     * @param length  the number of elements to be copied
     * @param <T>     the type of elements in the arrays
     */
    public static <T> void arrayCopy(T[] src, int srcPos, T[] dest, int destPos, int length) {
        for (int i = 0; i < length; i++) {
            dest[destPos + i] = src[srcPos + i];
        }
    }

    /**
     * Copies elements from the source array to the destination array.
     * This method is specifically designed for copying integer elements.
     *
     * @param src     the source array to copy elements from
     * @param srcPos  the starting position in the source array
     * @param dest    the destination array to copy elements to
     * @param destPos the starting position in the destination array
     * @param length  the number of elements to be copied
     */
    public static void arrayCopy(int[] src, int srcPos, int[] dest, int destPos, int length) {
        for (int i = 0; i < length; i++) {
            dest[destPos + i] = src[srcPos + i];
        }
    }

}

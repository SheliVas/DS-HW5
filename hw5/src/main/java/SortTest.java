import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class SortTest {
    private static final int NUMITER = 100;
    private static final int[] inputSizes = { 10000, 50000, 100000, 500000, 1000000 };
    private static final int[] radixSortBases = { 2, 5, 10, 15, 20, 25 };

    public static void main(String[] args) {
        Sort sorter = new Sort();
        Random randomGenerator = new Random();

        sorter.setNaiveSortThreshold(8);

        try {
            FileWriter fileWriter = new FileWriter("SortingTests.txt");

            fileWriter.write("Experiment 1: Random Inputs\n");
            fileWriter.write("---------------------------\n");
            runExperiment(sorter, randomGenerator, inputSizes, false, fileWriter);

            fileWriter.write("Experiment 2: Sorted Inputs in Increasing Order\n");
            fileWriter.write("---------------------------\n");
            // runExperiment(sorter, randomGenerator, inputSizes, true, fileWriter);

            fileWriter.write("Experiment 3: Sorted Inputs in Decreasing Order\n");
            fileWriter.write("---------------------------\n");
            // runExperiment(sorter, randomGenerator, inputSizes, false, fileWriter);

            fileWriter.write("Radix Sort Experiment\n");
            fileWriter.write("---------------------------\n");
            for (int range : new int[] { 10, 20, 30 }) {
                fileWriter.write("Range: [0, 2^" + range + "]\n");
                fileWriter.write("---------------------------\n");
                for (int base : radixSortBases) {
                    fileWriter.write("Base: " + base + "\n");
                    runRadixSortExperiment(sorter, randomGenerator, 500000, range, base, fileWriter);
                    fileWriter.write("---------------------------\n");
                }
            }

            fileWriter.close();
            System.out.println("Sorting tests completed. Results are saved in SortingTests.txt.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing the results to file: " + e.getMessage());
        }
    }

    private static void runExperiment(Sort sorter, Random randomGenerator, int[] inputSizes, boolean sorted,
            FileWriter fileWriter) throws IOException {
        for (int size : inputSizes) {
            int[][] durationList = new int[6][NUMITER];

            for (int k = 0; k < NUMITER; k++) {
                Integer[] A = new Integer[size];

                for (int j = 0; j < size; j++) {
                    A[j] = randomGenerator.nextInt();
                }

                if (sorted) {
                    Arrays.sort(A);
                }

                for (int i = 0; i < 6; i++) {
                    Integer[] B = Arrays.copyOf(A, A.length);

                    long startTime = System.currentTimeMillis();

                    switch (i) {
                        case 0:
                            Arrays.sort(B);
                            break;
                        case 1:
                            sorter.quickSortClass(B);
                            break;
                        case 2:
                            sorter.quickSortRecitation(B);
                            break;
                        case 3:
                            sorter.mergeSortRecursive(B);
                            break;
                        case 4:
                            sorter.mergeSortIterative(B);
                            break;
                        case 5:
                            int[] C = convertFromIntegerArrayToIntArray(B);
                            Sort.radixSort(C, getRadixSortBase(size));
                            break;
                    }

                    long endTime = System.currentTimeMillis();
                    durationList[i][k] = (int) (endTime - startTime);
                }
            }

            fileWriter.write("Results for input size: " + size + "\n");
            fileWriter.write("---------------------------\n");

            String[] algorithmNames = {
                    "Arrays.sort",
                    "Quick Sort (Class)",
                    "Quick Sort (Recitation)",
                    "Merge Sort (Recursive)",
                    "Merge Sort (Iterative)",
                    "Radix Sort"
            };

            for (int i = 0; i < 6; i++) {
                double average = calculateAverage(durationList[i]);
                double standardDeviation = calculateStandardDeviation(durationList[i], average);

                fileWriter.write("Algorithm: " + algorithmNames[i] + "\n");
                fileWriter.write("Average: " + average + "\n");
                fileWriter.write("Standard Deviation: " + standardDeviation + "\n");
                fileWriter.write("---------------------------\n");
            }
        }
    }

    private static void runRadixSortExperiment(Sort sorter, Random randomGenerator, int size, int range, int base,
            FileWriter fileWriter) throws IOException {
        int[] durationList = new int[NUMITER];

        for (int k = 0; k < NUMITER; k++) {
            Integer[] A = new Integer[size];

            for (int j = 0; j < size; j++) {
                A[j] = randomGenerator.nextInt((int) Math.pow(2, range));
            }

            Integer[] B = Arrays.copyOf(A, A.length);

            long startTime = System.currentTimeMillis();

            int[] C = convertFromIntegerArrayToIntArray(B);
            Sort.radixSort(C, getRadixSortBase(size));

            long endTime = System.currentTimeMillis();
            durationList[k] = (int) (endTime - startTime);
        }

        double average = calculateAverage(durationList);
        double standardDeviation = calculateStandardDeviation(durationList, average);

        fileWriter.write("Algorithm: Radix Sort \n");
        fileWriter.write("Average: " + average + "\n");
        fileWriter.write("Standard Deviation: " + standardDeviation + "\n");

    }

    private static double calculateAverage(int[] array) {
        double sum = 0;

        for (int value : array) {
            sum += value;
        }

        return sum / array.length;
    }

    private static double calculateStandardDeviation(int[] array, double average) {
        double deviationSum = 0;

        for (int value : array) {
            deviationSum += Math.pow(value - average, 2);
        }

        return Math.sqrt(deviationSum / array.length);
    }

    private static int getRadixSortBase(int size) {
        // Choose the best base for Radix Sort based on the input size
        if (size <= 100000) {
            return 10;
        } else if (size <= 500000) {
            return 15;
        } else {
            return 20;
        }
    }

    public static int[] convertFromIntegerArrayToIntArray(Integer[] B) {
        int[] C = new int[B.length];
        for (int i = 0; i < B.length; i++) {
            C[i] = B[i];
        }
        return C;
    }
}
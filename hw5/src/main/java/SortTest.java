
import java.util.Arrays;
import java.util.Random;

public class SortTest {
    private static final int[] INPUT_SIZES = { 10000, 50000, 100000, 500000, 1000000 };
    private static final int NUMITER = 100;

    public static void main(String[] args) {
        Sort<Integer> sort = new Sort<>();
        Random random = new Random();

        // Performance measurement arrays
        long[][] durationListRandom = new long[6][INPUT_SIZES.length];
        long[][] durationListSortedInc = new long[6][INPUT_SIZES.length];
        long[][] durationListSortedDec = new long[6][INPUT_SIZES.length];

        for (int i = 0; i < NUMITER; i++) {
            for (int j = 0; j < INPUT_SIZES.length; j++) {
                int inputSize = INPUT_SIZES[j];

                Integer[] randomArray = new Integer[inputSize];
                Integer[] sortedIncArray = new Integer[inputSize];
                Integer[] sortedDecArray = new Integer[inputSize];

                // Generate random input
                for (int k = 0; k < inputSize; k++) {
                    int value = random.nextInt(inputSize);
                    randomArray[k] = value;
                    sortedIncArray[k] = value;
                    sortedDecArray[k] = value;
                }

                Arrays.sort(sortedIncArray); // Sort in increasing order
                Arrays.sort(sortedDecArray, (a, b) -> b.compareTo(a)); // Sort in decreasing order

                // Measure the running time for each algorithm on random input
                durationListRandom[0][j] += measureTime(() -> sort.quickSortClass(randomArray));
                durationListRandom[1][j] += measureTime(() -> sort.quickSortRecitation(randomArray));
                durationListRandom[2][j] += measureTime(
                        () -> sort.radixSort(Arrays.stream(randomArray).mapToInt(Integer::intValue).toArray(), 10));
                durationListRandom[3][j] += measureTime(() -> sort.mergeSortRecursive(randomArray));
                durationListRandom[4][j] += measureTime(() -> sort.mergeSortIterative(randomArray));
                durationListRandom[5][j] += measureTime(() -> Arrays.sort(randomArray));

                // Measure the running time for each algorithm on sorted input (increasing
                // order)
                durationListSortedInc[0][j] += measureTime(() -> sort.quickSortClass(sortedIncArray));
                durationListSortedInc[1][j] += measureTime(() -> sort.quickSortRecitation(sortedIncArray));
                durationListSortedInc[2][j] += measureTime(
                        () -> sort.radixSort(Arrays.stream(sortedIncArray).mapToInt(Integer::intValue).toArray(), 10));
                durationListSortedInc[3][j] += measureTime(() -> sort.mergeSortRecursive(sortedIncArray));
                durationListSortedInc[4][j] += measureTime(() -> sort.mergeSortIterative(sortedIncArray));
                durationListSortedInc[5][j] += measureTime(() -> Arrays.sort(sortedIncArray));

                // Measure the running time for each algorithm on sorted input (decreasing
                // order)
                durationListSortedDec[0][j] += measureTime(() -> sort.quickSortClass(sortedDecArray));
                durationListSortedDec[1][j] += measureTime(() -> sort.quickSortRecitation(sortedDecArray));
                durationListSortedDec[2][j] += measureTime(
                        () -> sort.radixSort(Arrays.stream(sortedDecArray).mapToInt(Integer::intValue).toArray(), 10));
                durationListSortedDec[3][j] += measureTime(() -> sort.mergeSortRecursive(sortedDecArray));
                durationListSortedDec[4][j] += measureTime(() -> sort.mergeSortIterative(sortedDecArray));
                durationListSortedDec[5][j] += measureTime(() -> Arrays.sort(sortedDecArray));

                // Compute average duration and standard deviation for each algorithm and input
                // case
                double[][] averageDurationsRandom = computeAverageDurations(durationListRandom);
                double[][] averageDurationsSortedInc = computeAverageDurations(durationListSortedInc);
                double[][] averageDurationsSortedDec = computeAverageDurations(durationListSortedDec);

                double[][] standardDeviationsRandom = computeStandardDeviations(durationListRandom,
                        averageDurationsRandom);
                double[][] standardDeviationsSortedInc = computeStandardDeviations(durationListSortedInc,
                        averageDurationsSortedInc);
                double[][] standardDeviationsSortedDec = computeStandardDeviations(durationListSortedDec,
                        averageDurationsSortedDec);

                // Print the results in tables
                System.out.println("Random Inputs:");
                printResults(INPUT_SIZES, averageDurationsRandom, standardDeviationsRandom);

                System.out.println("\nSorted Inputs (Increasing Order):");
                printResults(INPUT_SIZES, averageDurationsSortedInc, standardDeviationsSortedInc);

                System.out.println("\nSorted Inputs (Decreasing Order):");
                printResults(INPUT_SIZES, averageDurationsSortedDec, standardDeviationsSortedDec);

            }
        }
    }

    private static long measureTime(Runnable runnable) {
        long startTime = System.currentTimeMillis();
        runnable.run();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    private static double[][] computeAverageDurations(long[][] durationList) {
        int numAlgorithms = durationList.length;
        int numInputSizes = durationList[0].length;

        double[][] averageDurations = new double[numAlgorithms][numInputSizes];
        for (int i = 0; i < numAlgorithms; i++) {
            for (int j = 0; j < numInputSizes; j++) {
                averageDurations[i][j] = durationList[i][j] / (double) NUMITER;
            }
        }
        return averageDurations;
    }

    private static double[][] computeStandardDeviations(long[][] durationList, double[][] averageDurations) {
        int numAlgorithms = durationList.length;
        int numInputSizes = durationList[0].length;

        double[][] standardDeviations = new double[numAlgorithms][numInputSizes];
        for (int i = 0; i < numAlgorithms; i++) {
            for (int j = 0; j < numInputSizes; j++) {
                long sumSquaredDifferences = 0;
                for (int k = 0; k < NUMITER; k++) {
                    long difference = durationList[i][j] - averageDurations[i][j];
                    sumSquaredDifferences += difference * difference;
                }
                double variance = sumSquaredDifferences / (double) NUMITER;
                standardDeviations[i][j] = Math.sqrt(variance);
            }
        }
        return standardDeviations;
    }

    private static void printResults(int[] inputSizes, double[][] averageDurations, double[][] standardDeviations) {
        int numAlgorithms = averageDurations.length;
        int numInputSizes = averageDurations[0].length;

        // Print header
        System.out.print("Algorithm\t");
        for (int i = 0; i < numInputSizes; i++) {
            System.out.print("Size " + inputSizes[i] + "\t");
        }
        System.out.println();

        // Print results
        for (int i = 0; i < numAlgorithms; i++) {
            System.out.print("Algorithm " + (i + 1) + "\t");
            for (int j = 0; j < numInputSizes; j++) {
                System.out.printf("%.2f ± %.2f\t", averageDurations[i][j], standardDeviations[i][j]);
            }
            System.out.println();
        }
    }

}
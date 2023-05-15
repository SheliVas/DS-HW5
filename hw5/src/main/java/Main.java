public class Main {
    public static void main(String[] args){
        System.out.println("Starting...");
        

        System.out.println("Testing InsertionSort:");
        Sort.testInsertionSort();
        System.out.println();
        
        
        System.out.println("Testing QuickSort:");
        Sort.testQuickSort();
        System.out.println();
        
        System.out.println("Testing Recursive MergeSort:");
        Sort.testRecursiveMergeSort();
        System.out.println();
       
        System.out.println("Testing iterative MergeSort:");
        Sort.testIterativeMergeSort();
        System.out.println();

       
    }
}

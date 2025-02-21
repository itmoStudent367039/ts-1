public class ShellSort {

    private int outerLoopIterations = 0;
    private int middleLoopIterations = 0;
    private int innerLoopIterations = 0;
    private int shiftsCount = 0;

    public void sort(int[] array) {
        int n = array.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            outerLoopIterations++;
            for (int i = gap; i < n; i += 1) {
                middleLoopIterations++;
                int temp = array[i];
                int j;
                for (j = i; j >= gap && array[j - gap] > temp; j -= gap) {
                    innerLoopIterations++;
                    shiftsCount++;
                    array[j] = array[j - gap];
                }
                array[j] = temp;
            }
        }
    }

    public int getOuterLoopIterations() {
        return outerLoopIterations;
    }

    public int getMiddleLoopIterations() {
        return middleLoopIterations;
    }

    public int getInnerLoopIterations() {
        return innerLoopIterations;
    }

    public int getShiftsCount() {
        return shiftsCount;
    }
}

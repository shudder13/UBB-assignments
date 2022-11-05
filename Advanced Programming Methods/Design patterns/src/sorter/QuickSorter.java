package sorter;

public class QuickSorter extends AbstractSorter {

    @Override
    public void sort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);
            quickSort(array, low, pi - 1);
            quickSort(array, pi + 1, high);
        }
    }

    private int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;

        for (int j = low; j <= high - 1; j++) {
            if (array[j] < pivot) {
                i++;
                int aux = array[i];
                array[i] = array[j];
                array[j] = aux;
            }
        }
        int aux = array[i + 1];
        array[i + 1] = array[high];
        array[high] = aux;
        return i + 1;
    }
}

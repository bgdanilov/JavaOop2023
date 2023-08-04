import java.util.Arrays;

public class MyArrays {
    public static void main(String[] args) {
        // Инициализация массива.
        // new int[] можно опускать, если мы не используем return.
        int[] array1 = {1, 3, 10};
        int[] array2 = new int[] {1, 3,10};

        System.out.println(Arrays.toString(getArray()));

    }

    public static int[] getArray() {
        return new int[] {1, 3, 10};
    }
}

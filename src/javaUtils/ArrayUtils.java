package javaUtils;

import java.util.Arrays;

public class ArrayUtils {
    private static int[] tempInt = new int[0];

    public static char[] unique(char[] array) {
        return unique(array, 0, array.length);
    }

    public static <T> void reverse(T[] array) {
        for (int i = 0, j = array.length - 1; i < j; i++, j--) {
            MiscUtils.swap(array[i], array[j]);
        }
    }

    public static void reverse(int[] array) {
        for (int i = 0, j = array.length - 1; i < j; i++, j--) {
            int t = array[i];
            array[i] = array[j];
            array[j] = t;
        }
    }

    public static long sum(int... array) {
        long s = 0;
        for (int i = 0; i < array.length; i++) {
            s += array[i];
        }
        return s;
    }

    public static <T> void print(OutputWriter out, T[] array) {
        for (int i = 0; i < array.length; i++) {
            out.print(array[i]);
            out.print(' ');
        }
    }

    public static <T> void printLine(OutputWriter out, T[] array) {
        for (int i = 0; i < array.length; i++) {
            out.print(array[i]);
            out.print(' ');
        }
        out.print('\n');
    }

    public static void printArr(OutputWriter out, int[] array) {
        for (int num : array) out.print(num + " ");
        out.print("\n");
    }

    public static int count(int[] array, int ele) {
        int cnt = 0;
        for (int num : array) if (ele == num) cnt++;
        return cnt;
    }

    public static char[] unique(char[] array, int from, int to) {
        if (from == to)
            return new char[0];
        int count = 1;
        for (int i = from + 1; i < to; i++) {
            if (array[i] != array[i - 1])
                count++;
        }
        char[] result = new char[count];
        result[0] = array[from];
        int index = 1;
        for (int i = from + 1; i < to; i++) {
            if (array[i] != array[i - 1])
                result[index++] = array[i];
        }
        return result;
    }

    public static int[] unique(int[] array, int from, int to) {
        if (from == to)
            return new int[0];
        int count = 1;
        for (int i = from + 1; i < to; i++) {
            if (array[i] != array[i - 1])
                count++;
        }
        int[] result = new int[count];
        result[0] = array[from];
        int index = 1;
        for (int i = from + 1; i < to; i++) {
            if (array[i] != array[i - 1])
                result[index++] = array[i];
        }
        return result;
    }

    public static void fill2d(long[][] array, long value) {
        for (int i = 0; i < array.length; i++)
            Arrays.fill(array[i], value);
    }

    public static void fill2d(int[][] array, int value) {
        for (int i = 0; i < array.length; i++)
            Arrays.fill(array[i], value);
    }

    public static void fill2d(char[][] array, char value) {
        for (int i = 0; i < array.length; i++)
            Arrays.fill(array[i], value);
    }


    public static <V> void fill2d(V[][] array, V value) {
        for (int i = 0; i < array.length; i++)
            Arrays.fill(array[i], value);
    }

    public static void fill3d(int[][][] array, int value) {
        for (int i = 0; i < array.length; i++) {
            fill2d(array[i], value);
        }
    }

    public static void fill3d(long[][][] array, int value) {
        for (int i = 0; i < array.length; i++) {
            fill2d(array[i], value);
        }
    }

    public static int[] createOrder(int size) {
        int[] order = new int[size];
        for (int i = 0; i < size; i++)
            order[i] = i;
        return order;
    }

    public static int[] reversePermutation(int[] permutation) {
        int[] result = new int[permutation.length];
        for (int i = 0; i < permutation.length; i++)
            result[permutation[i]] = i;
        return result;
    }

    public static int[] multiplyPermutations(int[] first, int[] second) {
        int count = first.length;
        int[] result = new int[count];
        for (int i = 0; i < count; i++) {
            result[i] = first[second[i]];
        }
        return result;
    }

    public static int[] sort(int[] array, IntComparator comparator) {
        return sort(array, 0, array.length, comparator);
    }

    public static int[] sort(int[] array, int from, int to, IntComparator comparator) {
        ensureCapacityInt(to - from);
        System.arraycopy(array, from, tempInt, 0, to - from);
        sortImpl(array, from, to, tempInt, 0, to - from, comparator);
        return array;
    }

    private static void ensureCapacityInt(int size) {
        if (tempInt.length >= size)
            return;
        size = Math.max(size, tempInt.length << 1);
        tempInt = new int[size];
    }

    private static void sortImpl(int[] array, int from, int to, int[] temp, int fromTemp, int toTemp, IntComparator comparator) {
        if (to - from <= 1)
            return;
        int middle = (to - from) >> 1;
        int tempMiddle = fromTemp + middle;
        sortImpl(temp, fromTemp, tempMiddle, array, from, from + middle, comparator);
        sortImpl(temp, tempMiddle, toTemp, array, from + middle, to, comparator);
        int index = from;
        int index1 = fromTemp;
        int index2 = tempMiddle;
        while (index1 < tempMiddle && index2 < toTemp) {
            if (comparator.compare(temp[index1], temp[index2]) <= 0)
                array[index++] = temp[index1++];
            else
                array[index++] = temp[index2++];
        }
        if (index1 != tempMiddle)
            System.arraycopy(temp, index1, array, index, tempMiddle - index1);
        if (index2 != toTemp)
            System.arraycopy(temp, index2, array, index, toTemp - index2);
    }

    public static int maxPosition(int[] array) {
        return maxPosition(array, 0, array.length);
    }

    public static int maxPosition(long[] array) {
        return maxPosition(array, 0, array.length);
    }

    public static int maxPosition(double[] array) {
        return maxPosition(array, 0, array.length);
    }

    public static int maxElement(int[] array) {
        return array[maxPosition(array)];
    }

    public static int maxElement(int[] array, int from, int to) {
        return array[maxPosition(array, from, to)];
    }

    public static int minElement(int[] array, int from, int to) {
        return array[minPosition(array, from, to)];
    }

    public static int maxPosition(int[] array, int from, int to) {
        if (from >= to)
            return -1;
        int max = array[from];
        int result = from;
        for (int i = from + 1; i < to; i++) {
            if (array[i] > max) {
                max = array[i];
                result = i;
            }
        }
        return result;
    }

    public static int maxPosition(long[] array, int from, int to) {
        if (from >= to)
            return -1;
        long max = array[from];
        int result = from;
        for (int i = from + 1; i < to; i++) {
            if (array[i] > max) {
                max = array[i];
                result = i;
            }
        }
        return result;
    }

    public static int maxPosition(double[] array, int from, int to) {
        if (from >= to)
            return -1;
        double max = array[from];
        int result = from;
        for (int i = from + 1; i < to; i++) {
            if (array[i] > max) {
                max = array[i];
                result = i;
            }
        }
        return result;
    }

    public static int minPosition(int[] array, int from, int to, IntComparator comparator) {
        if (from >= to)
            return -1;
        int min = array[from];
        int result = from;
        for (int i = from + 1; i < to; i++) {
            if (comparator.compare(array[i], min) < 0) {
                min = array[i];
                result = i;
            }
        }
        return result;
    }

    public static int minPosition(long[] array, int from, int to, IntComparator comparator) {
        if (from >= to)
            return -1;
        long min = array[from];
        int result = from;
        for (int i = from + 1; i < to; i++) {
            if (array[i] < min) {
                min = array[i];
                result = i;
            }
        }
        return result;
    }

    public static int minPosition(int[] array, int from, int to) {
        IntComparator comparator = IntComparator.DEFAULT;
        if (from >= to)
            return -1;
        int min = array[from];
        int result = from;
        for (int i = from + 1; i < to; i++) {
            if (comparator.compare(array[i], min) < 0) {
                min = array[i];
                result = i;
            }
        }
        return result;
    }

    public static void columnFill(int columnNumber, int value, int[][]... arrays) {
        for (int k = 0; k < arrays.length; k++) {
            for (int i = 0; i < arrays[k].length; i++)
                arrays[k][i][columnNumber] = value;
        }
    }
}


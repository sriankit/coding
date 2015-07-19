package javaUtils;

public class MiscUtils {
    public static final int[] DX4 = {1, 0, -1, 0};
    public static final int[] DY4 = {0, -1, 0, 1};

    public static void decreaseByOne(int[]... arrays) {
        for (int[] array : arrays) {
            for (int i = 0; i < array.length; i++)
                array[i]--;
        }
    }

    public static <T> void swap(T o1, T o2) {
        T x = o1;
        o1 = o2;
        o2 = x;
    }


    public static String reverse(String s) {
        return reverse(s, 0, s.length() - 1);
    }

    /*
    returns reversed substring s[from : to] both inclusive
     */

    public static String reverse(String s, int from, int to) {
        StringBuilder b = new StringBuilder(s.substring(from, to + 1));
        return b.reverse().toString();
    }
}

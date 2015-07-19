package javaUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class IOUtils {
    public static int[] readIntArray() throws IOException {
        return readIntArray(0);
    }

    public static int[] readIntArray(int start) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(in.readLine());
        int[] array = new int[start + st.countTokens()];
        for (int i = start; i < array.length; i++) {
            array[i] = Integer.parseInt(st.nextToken());
        }
        return array;
    }

    public static int[] readIntArray(InReader in, int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++)
            array[i] = in.readInt();
        return array;
    }

    public static long[] readLongArray(InReader in, int size) {
        long[] array = new long[size];
        for (int i = 0; i < size; i++)
            array[i] = in.readLong();
        return array;
    }

    public static void readIntArrays(InReader in, int[]... arrays) {
        for (int i = 0; i < arrays[0].length; i++) {
            for (int j = 0; j < arrays.length; j++)
                arrays[j][i] = in.readInt();
        }
    }

    public static char[] readCharArray(InReader in, int size) {
        char[] array = new char[size];
        for (int i = 0; i < size; i++)
            array[i] = in.readCharacter();
        return array;
    }

    public static char[][] readTable(InReader in, int rowCount, int columnCount) {
        char[][] table = new char[rowCount][];
        for (int i = 0; i < rowCount; i++)
            table[i] = readCharArray(in, columnCount);
        return table;
    }

    public static void writeIntArray(OutputWriter out, int[] arr, int st) {
        for (int i = st; i < arr.length; i++) {
            out.printf("%d ", arr[i]);
        }
        out.printf("\n");
    }
}

package javaUtils;

import java.util.AbstractList;
import java.util.List;
import java.util.RandomAccess;

abstract class Array<T> extends AbstractList<T> implements RandomAccess {

    public static List<Integer> wrap(int... array) {
        return new IntArray(array);
    }

    protected static class IntArray extends Array<Integer> {
        protected final int[] array;

        protected IntArray(int[] array) {
            this.array = array;
        }

        public int size() {
            return array.length;
        }

        public Integer get(int index) {
            return array[index];
        }

        public Integer set(int index, Integer value) {
            int result = array[index];
            array[index] = value;
            return result;
        }
    }

}
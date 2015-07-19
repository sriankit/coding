package javaUtils;

import java.util.*;

public class CollectionUtils {
    public static int[] toArray(Collection<Integer> collection) {
        int[] array = new int[collection.size()];
        int index = 0;
        for (int element : collection)
            array[index++] = element;
        return array;
    }

    public static <U, V> V mapGet(TreeMap<U, V> collection, U key, V defaultReturn) {
        if (collection.containsKey(key)) return collection.get(key);
        else return defaultReturn;
    }

    public static List<Integer> range(int from, int to) {
        List<Integer> result = new ArrayList<Integer>(Math.max(from, to) - Math.min(from, to) + 1);
        if (to > from) {
            for (int i = from; i <= to; i++)
                result.add(i);
        } else {
            for (int i = from; i >= to; i--)
                result.add(i);
        }
        return result;
    }

    public static void rotate(List<Integer> list) {
        list.add(list.remove(0));
    }

    public static <T extends Comparable<T>> T minElement(Iterable<T> collection) {
        T result = null;
        for (T element : collection) {
            if (result == null || result.compareTo(element) > 0)
                result = element;
        }
        return result;
    }

    public static <T extends Comparable<T>> T maxElement(Iterable<T> collection) {
        T result = null;
        for (T element : collection) {
            if (result == null || result.compareTo(element) < 0)
                result = element;
        }
        return result;
    }

    public static <T> T minElement(Iterable<T> collection, Comparator<T> comparator) {
        T result = null;
        for (T element : collection) {
            if (result == null || comparator.compare(result, element) > 0)
                result = element;
        }
        return result;
    }

    public static <T> T maxElement(Iterable<T> collection, Comparator<T> comparator) {
        T result = null;
        for (T element : collection) {
            if (result == null || comparator.compare(result, element) < 0)
                result = element;
        }
        return result;
    }

    public static <T> List<T> asList(Iterable<T> iterable) {
        List<T> list = new ArrayList<T>();
        for (T element : iterable)
            list.add(element);
        return list;
    }

    public static <T> int count(Iterable<T> array, T sample) {
        int result = 0;
        for (T element : array) {
            if (element.equals(sample))
                result++;
        }
        return result;
    }

    public static <T> int count(Iterable<T> array, Filter<T> filter) {
        int result = 0;
        for (T element : array) {
            if (filter.accept(element))
                result++;
        }
        return result;
    }

    public static <T> List<T> filter(Iterable<T> sequence, Filter<T> filter) {
        List<T> result = new ArrayList<T>();
        for (T element : sequence) {
            if (filter.accept(element))
                result.add(element);
        }
        return result;
    }

    public static <A, V> List<V> apply(Iterable<A> sequence, Function<A, V> function) {
        List<V> result = new ArrayList<V>();
        for (A element : sequence)
            result.add(function.value(element));
        return result;
    }

    public static <T> void reverse(T[] array) {
        for (int i = 0, j = array.length - 1; i < j; i++, j--) {
            MiscUtils.swap(array[i], array[j]);
        }
    }

}

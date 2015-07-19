package javaUtils;

public class Pair<U, V> implements Comparable<Pair<U, V>> {
    public final U first;
    public final V second;

    private Pair(U first, V second) {
        this.first = first;
        this.second = second;
    }

    public static <U, V> Pair<U, V> makePair(U first, V second) {
        return new Pair<U, V>(first, second);
    }

    public U getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;

        return !(first != null ? !first.equals(pair.first) : pair.first != null) && !(second != null ? !second.equals(pair.second) : pair.second != null);

    }

    public int hashCode() {
        int result = first != null ? first.hashCode() : 0;
        result = 31 * result + (second != null ? second.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "(" + first + "," + second + ")";
    }

    public int compareTo(Pair<U, V> o) {

        int value = ((Comparable<U>) first).compareTo(o.first);
        if (value != 0)
            return value;
        return ((Comparable<V>) second).compareTo(o.second);
    }
}
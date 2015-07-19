package javaUtils;

public interface Function<A, V> {
    public abstract V value(A argument);
}

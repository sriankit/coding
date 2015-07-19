package javaUtils;

public interface Filter<T> {
    public boolean accept(T value);
}
package javaUtils;

import java.util.AbstractList;
import java.util.List;

class StringWrapper extends AbstractList<Character> {
    private final CharSequence string;

    private StringWrapper(CharSequence string) {
        this.string = string;
    }

    public static List<Character> wrap(CharSequence string) {
        return new StringWrapper(string);
    }

    public int size() {
        return string.length();
    }

    public Character get(int index) {
        return string.charAt(index);
    }
}
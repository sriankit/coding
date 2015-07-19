package javaUtils;

import java.util.TreeSet;

public class Multiset {
    BIT BIT;
    int maxElement;
    int totalCount;
    TreeSet<Integer> keyset;

    public Multiset(int maxElement) {
        assert (maxElement < 4e5);
        this.maxElement = maxElement;
        BIT = new BIT(maxElement);
        totalCount = 0;
        keyset = new TreeSet<Integer>();
    }

    public Multiset() {
        new Multiset(1001);
    }

    public void putAll(Multiset set) {
        for (int x : set.keyset) {
            BIT.update(x, set.count(x));
            keyset.add(x);
        }
    }

    public boolean containsKey(int x) {
        return keyset.contains(x);
    }

    public void add(int x) {
        BIT.update(x, 1);
        totalCount++;
        keyset.add(x);
    }

    public int count(int x) {
        return (int) BIT.freqAt(x);
    }

    public int getLessOrEqualCount(int x) {
        return (int) BIT.freqTo(x);
    }

    public void deleteOneInstance(int x) {
        BIT.update(x, -1);
        totalCount--;
        if (count(x) == 0) keyset.remove(x);
    }

    public void deleteAllInstances(int x) {
        int count = count(x);
        BIT.update(x, -count);
        totalCount -= count;
        keyset.remove(x);
    }

    public int getLesserCount(int x) {
        if (x <= 0) return 0;
        return (int) BIT.freqTo(x - 1);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (int x : keyset) {
            builder.append(x + ": " + count(x) + ' ');
        }
        builder.append('}');
        return builder.toString();
    }
}

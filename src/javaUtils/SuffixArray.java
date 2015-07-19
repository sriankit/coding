package javaUtils;

import java.util.Arrays;
import java.util.Comparator;

public class SuffixArray {
    /*
    Code taken from open source repository
     */
    // suffix array in O(n*log(n))
    public static int[] suffixArray(final CharSequence str) {
        int n = str.length();
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++)
            order[i] = n - 1 - i;

        Arrays.sort(order, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Character.compare(str.charAt(o1), str.charAt(o2));
            }
        });

        // sa[i] - suffix on i'th position after sorting by first len characters
        // rank[i] - position of the i'th suffix after sorting by first len characters
        int[] sa = new int[n];
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) {
            sa[i] = order[i];
            rank[i] = str.charAt(i);
        }

        for (int len = 1; len < n; len *= 2) {
            int[] r = rank.clone();
            for (int i = 0; i < n; i++) {
                // condition s1 + len < n simulates 0-symbol at the end of the string
                // a separate class is created for each suffix followed by 0-symbol
                rank[sa[i]] = i > 0 && r[sa[i - 1]] == r[sa[i]] && sa[i - 1] + len < n && r[sa[i - 1] + len / 2] == r[sa[i] + len / 2] ? rank[sa[i - 1]] : i;
            }
            // Suffixes are already sorted by first len characters
            // Now sort suffixes by first len * 2 characters
            int[] cnt = new int[n];
            for (int i = 0; i < n; i++)
                cnt[i] = i;
            int[] s = sa.clone();
            for (int i = 0; i < n; i++) {
                // s[i] - order of suffixes sorted by first len characters
                // (s[i] - len) - order of suffixes sorted only by second len characters
                int s1 = s[i] - len;
                // sort only suffixes of length > len, others are already sorted
                if (s1 >= 0)
                    sa[cnt[rank[s1]]++] = s1;
            }
        }
        return sa;
    }

    // longest common prefixes array in O(n)
    public static int[] lcp(int[] sa, CharSequence s) {
        int n = sa.length;
        int[] rank = new int[n];
        for (int i = 0; i < n; i++)
            rank[sa[i]] = i;
        int[] lcp = new int[n - 1];
        for (int i = 0, h = 0; i < n; i++) {
            if (rank[i] < n - 1) {
                int j = sa[rank[i] + 1];
                while (Math.max(i, j) + h < s.length() && s.charAt(i + h) == s.charAt(j + h)) {
                    ++h;
                }
                lcp[rank[i]] = h;
                if (h > 0)
                    --h;
            }
        }
        return lcp;
    }

    public static int[] suffixArray(int[] str) {
        int n = str.length;
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++)
            order[i] = n - 1 - i;

        Arrays.sort(order, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return str[o1] - str[o2];
            }
        });

        // sa[i] - suffix on i'th position after sorting by first len characters
        // rank[i] - position of the i'th suffix after sorting by first len characters
        int[] sa = new int[n];
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) {
            sa[i] = order[i];
            rank[i] = str[i];
        }

        for (int len = 1; len < n; len *= 2) {
            int[] r = rank.clone();
            for (int i = 0; i < n; i++) {
                // condition s1 + len < n simulates 0-symbol at the end of the string
                // a separate class is created for each suffix followed by 0-symbol
                rank[sa[i]] = i > 0 && r[sa[i - 1]] == r[sa[i]] && sa[i - 1] + len < n && r[sa[i - 1] + len / 2] == r[sa[i] + len / 2] ? rank[sa[i - 1]] : i;
            }
            // Suffixes are already sorted by first len characters
            // Now sort suffixes by first len * 2 characters
            int[] cnt = new int[n];
            for (int i = 0; i < n; i++)
                cnt[i] = i;
            int[] s = sa.clone();
            for (int i = 0; i < n; i++) {
                // s[i] - order of suffixes sorted by first len characters
                // (s[i] - len) - order of suffixes sorted only by second len characters
                int s1 = s[i] - len;
                // sort only suffixes of length > len, others are already sorted
                if (s1 >= 0)
                    sa[cnt[rank[s1]]++] = s1;
            }
        }
        return sa;
    }

    // longest common prefixes array in O(n)
    public static int[] lcp(int[] sa, int[] s) {
        int n = sa.length;
        int[] rank = new int[n];
        for (int i = 0; i < n; i++)
            rank[sa[i]] = i;
        int[] lcp = new int[n - 1];
        for (int i = 0, h = 0; i < n; i++) {
            if (rank[i] < n - 1) {
                int j = sa[rank[i] + 1];
                while (Math.max(i, j) + h < s.length && s[i + h] == s[j + h]) {
                    ++h;
                }
                lcp[rank[i]] = h;
                if (h > 0)
                    --h;
            }
        }
        return lcp;
    }
}

package Tasks;

import javaUtils.Tree;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeSet;

public class SubstringReversal {

    public String reverse(String s) {
        StringBuilder builder = new StringBuilder(s);
        return builder.reverse().toString();
    }

    public String getString(int x, int y, String S) {
        return S.substring(0, x) + reverse(S.substring(x, y + 1)) + S.substring(y + 1);
    }

    public int[] solve(String S) {
        char[] sarr = S.toCharArray();
        char minChar = 'z';
        for (int i = 0; i < S.length(); i++) {
            if(minChar > S.charAt(i)) minChar = S.charAt(i);
        }
        Arrays.sort(sarr);
        TreeSet<Point> slns = new TreeSet<>(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                String s1 = SubstringReversal.this.getString(o1.x, o1.y, S);
                String s2 = SubstringReversal.this.getString(o2.x, o2.y, S);
                if (s1.equals(s2)) {
                    return (o1.x == o2.x) ? (o1.y - o2.y) : o1.x - o2.x;
                } else return s1.compareTo(s2);
            }
        });
        int breakInd = -1;
        for (int i = 0; i < S.length(); i++) {
            if(S.charAt(i) != sarr[i]) {
                breakInd = i;
                break;
            }
        }
        if(breakInd == -1) return new int[]{0, 0};
        for (int i = breakInd + 1; i < S.length(); i++) {
            if(S.charAt(i) == sarr[breakInd]) slns.add(new Point(breakInd, i));
        }
        return new int[] {slns.first().x, slns.first().y};
    }
}

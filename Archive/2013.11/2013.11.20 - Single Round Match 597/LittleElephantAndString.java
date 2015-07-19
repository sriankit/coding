package Tasks;

import java.util.Arrays;

public class LittleElephantAndString {
    public int getNumber(String A, String B) {
        int n = A.length();
        if (A.equals(B)) return 0;
        char[] tA = A.toCharArray();
        char[] tB = B.toCharArray();
        Arrays.sort(tA);
        Arrays.sort(tB);
        if (!Arrays.equals(tA, tB)) return -1;
        int ans = -1;
        int cost = Integer.MAX_VALUE;

        int l1 = A.length() - 1, l2 = B.length();
        int bar = 0;
        for (int i = B.length() - 1; i >= 0; ) {
            if (l1 < 0) break;
            System.out.println("l1 = " + l1);
            if (B.charAt(i) != A.charAt(l1)) {
                l1--;
                System.out.println("i = " + i + " " + l1);
                continue;
            }
            if (B.charAt(i) == A.charAt(l1)) {
                bar++;
                i--;
                l1--;
            }
        }

        //if(ans == -1) return -1;
        return A.length() - bar;
    }
}

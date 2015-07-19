package Tasks;

import javaUtils.InReader;
import javaUtils.Manacher;

import java.util.Arrays;

public class TaroFriends {
    public int getNumber(int[] coordinates, int X) {
        int n = coordinates.length;
        int [] lefts = new int[2 * n];
        for (int i = 0; i < n; i++) {
            lefts[2 * i] = coordinates[i] - X;
            lefts[2 * i + 1] = coordinates[i] + X;
        }
        Arrays.sort(lefts);
        System.out.println(Arrays.toString(lefts));
        int ans = Integer.MAX_VALUE;
        for(int left : lefts) {
            int right = left;
            boolean ok = true;
            for (int i = 0; i < n; i++) {
                if(coordinates[i] + X < left) ok = false;
                if(coordinates[i] < left) right = Math.max(right, coordinates[i] + X);
                else if(coordinates[i] - X < left) right = Math.max(right, coordinates[i] + X);
                else right = Math.max(coordinates[i] - X, right);
            }
            int comp = right - left;
            System.out.println(left + " " + right);
            if(comp < ans && ok) {
                ans = Math.min(ans, right - left);
            }
        }
        return ans;
    }
}

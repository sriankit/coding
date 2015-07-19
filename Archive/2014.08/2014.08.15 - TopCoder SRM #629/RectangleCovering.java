package Tasks;

import javaUtils.IntegerUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class RectangleCovering {
    int get(int holeH, int holeW, int[] boardH, int[] boardW) {
        ArrayList<Integer> widths = new ArrayList<Integer>();
        for (int i = 0; i < boardH.length; i++) {
            if(boardH[i] > holeH) widths.add(boardW[i]);
            else if(boardW[i] > holeH) widths.add(boardH[i]);
        }
        Collections.sort(widths);
        int j = widths.size() - 1;
        int ans = 0;
        while (j >= 0 && holeW > 0) {
            holeW -= widths.get(j);
            j --;
            ans ++;
        }
        if(holeW > 0) return -1;
        else return ans;
    }
    public int minimumNumber(int holeH, int holeW, int[] boardH, int[] boardW) {
        for (int i = 0; i < boardH.length; i++) {
            if(boardH[i] > boardW[i]) {
                int t = boardH[i];
                boardH[i] = boardW[i];
                boardW[i] = t;
            }
        }
        int a1 = get(holeH, holeW, boardH, boardW);
        int a2 = get(holeW, holeH, boardH, boardW);
        if(a1 == -1 && a2 == -1) return -1;
        else if (a1 == -1 || a2 == -1) return a1 + a2 + 1;
        else return Math.min(a1, a2);
    }
}

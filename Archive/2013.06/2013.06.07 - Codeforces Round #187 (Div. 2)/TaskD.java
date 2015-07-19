package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;

public class TaskD {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int cnta = in.readInt();
        int cntb = in.readInt();
        String a = in.readString();
        String b = in.readString();
        char c = b.charAt(0);
        int bi = 0;
        int bcntr = 0;
        int acntr = 0;
        ArrayList<Integer> full = new ArrayList<Integer>();
        boolean flag = false;
        for (int i = 0; i < b.length(); i++) {
            boolean lflag = false;
            for (int j = 0; j < a.length(); j++) {
                if (b.charAt(i) == a.charAt(j)) {
                    lflag = true;
                    break;
                }
            }
            if (!lflag) flag = true;
        }
        if (flag) {
            out.printLine(0);
            return;
        }
        do {
            int lbcnt = 0;
            for (int i = 0; i < a.length(); i++) {
                if (c == a.charAt(i)) {
                    if (bi == b.length() - 1) {
                        bi = 0;
                        bcntr++;
                        lbcnt++;
                    } else bi++;
                    c = b.charAt(bi);
                }
            }
            full.add(bcntr);
            acntr++;
            if (acntr > cnta) break;
        } while (bi != 0);

        int na = cnta / acntr;
        int rem = cnta % acntr;
        long maxb = na * bcntr;
        if (rem > 0) maxb += full.get(rem - 1);
        out.printLine(maxb / cntb);
    }
}

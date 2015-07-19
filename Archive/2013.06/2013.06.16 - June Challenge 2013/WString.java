package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;
import java.util.Iterator;

public class WString {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            String s = in.readString();
            int len = s.length();
            int[][] cntr = new int[s.length()][26];
            ArrayList<Integer> hashes = new ArrayList<Integer>();
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != '#') cntr[i][s.charAt(i) - 'a']++;
                if (i > 0) {
                    if (s.charAt(i) == '#') hashes.add(i);
                    for (int j = 0; j < 26; j++) {
                        cntr[i][j] += cntr[i - 1][j];
                    }
                }
            }
            if (hashes.size() < 3) {
                out.printLine(0);
                continue;
            }
            Iterator it = hashes.iterator();
            int a = (Integer) it.next();
            int b = (Integer) it.next();
            int ans = 0;
            while (it.hasNext()) {
                int c = (Integer) it.next();
                boolean comp = true;
                //System.out.println(a + " " + b + " " + c);
                /*if (a == 0 || b - a - 1 == 0 || c - b - 1 == 0 || c == len - 1) {
                    a = b;
                    b = c;
                    continue;
                } */
                //sure that all are non-empty
                int cont = 0;
                int local = 0;
                for (int i = 0; i < 26; i++) {
                    local = Math.max(local, cntr[a][i]);
                }
                cont += local;
                if (local == 0) comp = false;
                //System.out.println("cont = " + cont);
                local = 0;
                for (int i = 0; i < 26; i++) {
                    local = Math.max(local, cntr[b][i] - cntr[a][i]);
                }
                cont += local;
                if (local == 0) comp = false;
                //System.out.println("cont = " + cont);
                local = 0;
                for (int i = 0; i < 26; i++) {
                    local = Math.max(local, cntr[c][i] - cntr[b][i]);
                }
                cont += local;
                if (local == 0) comp = false;
                //System.out.println("cont = " + cont);
                local = 0;
                for (int i = 0; i < 26; i++) {
                    local = Math.max(local, cntr[len - 1][i] - cntr[c][i]);
                }
                cont += local;
                if (local == 0) comp = false;
                //System.out.println(cont);
                if (comp && ans < cont + 3) {
                    ans = cont + 3;
                    //System.out.println("tn = " + testNumber + " a = " + a + " b = " + b + " c = " + c);
                }
                a = b;
                b = c;
            }
            out.printLine(ans);
        }
    }
}

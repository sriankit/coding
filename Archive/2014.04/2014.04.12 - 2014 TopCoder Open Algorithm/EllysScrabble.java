package Tasks;

import java.util.ArrayList;
import java.util.Collections;

public class EllysScrabble {
    int min(String s) {
        int ind = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(ind) > s.charAt(i)) ind = i;
        }
        return ind;
    }

    String tr(String s, int ind) {
        System.out.println(s + " --> " + ind);
        String ret = "";
        for (int i = 0; i < s.length(); i++) {
            if (i != ind) ret += s.charAt(i);
        }
        return ret;
    }

    public String getMin(String letters, int maxDistance) {
        /*System.out.println(letters);
        String ans = "";
        for (int i = 0; i < letters.length() - maxDistance - 1; i++) {
            String res = letters.substring(i, i + maxDistance + 1);
            int minind = min(res);
            System.out.println(minind + " " + i + " " + res);
            if(letters.charAt(i) == letters.charAt(minind + i)) {
                ans += letters.charAt(i);
            }
            else {
                //letters = letters.substring(0, i) + letters.charAt(minind + i) + getMin(tr(letters.substring(i), minind), maxDistance);
                ans += letters.charAt(minind + i);
                letters = letters.substring(0, i) + letters.charAt(minind + i) + tr(letters.substring(i), minind);

            }
            System.out.println(ans);
        }
        return ans;     */
        int n = letters.length();
        ArrayList<Integer> av[] = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            av[i] = new ArrayList<Integer>();
            av[i].add(i);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 1; j <= maxDistance; j++) {
                int indl = i - j;
                int indr = i + j;
                if (indl >= 0) av[indl].add(i);
                if (indr < n) av[indr].add(i);
            }
        }
        String ans = "";
        boolean[] mark = new boolean[n];
        for (int i = 0; i < n; i++) {
            char c = 'Z' + 1;
            int t = -1;
            Collections.sort(av[i]);
            for (int comp : av[i]) {
                if (!mark[comp]) {
                    if (comp == i - maxDistance) {
                        c = letters.charAt(comp);
                        t = comp;
                        break;
                    }
                    if (letters.charAt(comp) < c) {
                        c = letters.charAt(comp);
                        t = comp;
                    }
                }
            }
            ans += c;
            if (t == -1) System.out.println(i + " " + ans);
            mark[t] = true;
        }
        return ans;
    }
}

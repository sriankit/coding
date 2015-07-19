package Tasks;

import javaUtils.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class WolvesAndSheep {
    public int getmin(String[] field) {
        int r = field.length;
        int c = field[0].length();
        int ans = r + c;
        char[][] table = new char[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                table[i][j] = field[i].charAt(j);
            }
        }

        //r == 1
        if (r == 1) {
            int cnt = 0;
            int p = -1;
            for (int i = 0; i < c; i++) {
                if (table[0][i] != '.') {
                    if (p == -1) {
                        p = table[0][i];
                    } else if (p != table[0][i]) {
                        cnt++;
                        p = table[0][i];
                    }
                }
            }
            return cnt;
        }


        for (int mask = 0; mask < 1 << (r - 1); mask++) {
            int below[] = new int[r];
            below[r - 1] = 1;
            int lmask = mask;

            for (int i = 0; lmask > 0; i++, lmask >>= 1) {
                if ((lmask & 1) != 0) below[i] = 1;
            }
            //System.out.println(mask + " " + Arrays.toString(below));
            int[] sto = new int[c];
            boolean fail = false;
            ArrayList<Pair<Integer, Integer>> segments = new ArrayList<Pair<Integer, Integer>>();
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    if (table[i][j] == 'W') sto[j] = (sto[j] != 1 ? 2 : -1);
                    else if (table[i][j] == 'S') sto[j] = (sto[j] != 2 ? 1 : -1);
                    if (sto[j] == -1) {
                        fail = true;
                        break;
                    }
                }
                if (below[i] == 1) {
                    //System.out.println(mask + " " + i + " " + Arrays.toString(sto));
                    for (int j = 0; j < c; j++) {
                        if (sto[j] == -1) {
                            fail = true;
                            break;
                        }
                    }
                    int prev = -1, series = 0, idx = 0;
                    for (int j = 0; j < c; j++) {
                        if (sto[j] != 0) {
                            if (prev == -1) {
                                idx = j;
                                series = sto[j];
                                prev = sto[j];
                            } else {
                                if (prev == sto[j]) {
                                    idx = j;
                                    series = sto[j];
                                    prev = sto[j];
                                } else {
                                    segments.add(Pair.makePair(idx, j - 1));
                                    idx = j;
                                    prev = sto[j];
                                    series = sto[j];
                                }
                            }
                        }
                    }
                    for (int j = 0; j < sto.length; j++) {
                        sto[j] = 0;
                    }
                }
            }
            if (fail) continue;
            Collections.sort(segments, new Comparator<Pair<Integer, Integer>>() {
                @Override
                public int compare(Pair<Integer, Integer> o1, Pair<Integer, Integer> o2) {
                    return o1.second - o2.second;
                }
            });
            int colFence = 0;
            int segcnt = segments.size();
            boolean[] mark = new boolean[segcnt];
            for (int i = 0; i < segcnt; i++) {
                if (!mark[i]) {
                    mark[i] = true;
                    colFence++;
                    int brk = segments.get(i).second;
                    for (int i1 = 0; i1 < segments.size(); i1++) {
                        Pair<Integer, Integer> p = segments.get(i1);
                        if (p.first <= brk && brk <= p.second) {
                            mark[i1] = true;
                        }
                    }
                }
            }
            int rc = Integer.bitCount(mask);
            int compte = rc + colFence;
            if (compte < ans) {
                ans = compte;
                //.out.println(rc + " " + colFence + " " + mask);
            }
        }
        //rem case with r = 1

        return ans;
    }
}

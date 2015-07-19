package Tasks;

import javaUtils.*;


import java.util.Arrays;

public class ANKINTER {

    int[] a;
    int w;
    int mx[][], mn[][];
    int cnt[];


    private long solveInstance(int lo, int hi) {
        //System.out.println(lo + " " + hi);
        long res = 0;
        int len = hi - lo + 1;
        if(len < w) return 0;
        if(len < 4) {
            for (int i = lo; i <= hi; i++) {
                int mx = a[i], mn = a[i];
                for (int j = i; j <= hi; j++) {
                    if (mx < a[j]) mx = a[j];
                    if (mn > a[j]) mn = a[j];
                    if (j - i + 1 >= w && mx - mn == j - i) {
                        res++;
                    }
                }
            }
        } else {
            int mid = lo + hi >> 1;
            // find answer for two halves
            res = solveInstance(lo, mid) + solveInstance(mid + 1, hi);
            int[] length = new int[]{mid - lo + 1, hi - mid};

            for (int i = mid; i >= lo; i--) {
                if(i == mid) {
                    mx[0][0] = mn[0][0] = a[mid];
                } else {
                    mx[0][mid - i] = Math.max(mx[0][mid - i - 1], a[i]);
                    mn[0][mid - i] = Math.min(mn[0][mid - i - 1], a[i]);
                }
            }

            for (int i = mid + 1; i < hi + 1; i++) {
                if(i == mid + 1) {
                    mx[1][0] = mn[1][0] = a[mid + 1];
                } else {
                    mx[1][i - mid - 1] = Math.max(mx[1][i - 2 - mid], a[i]);
                    mn[1][i - mid - 1] = Math.min(mn[1][i - 2 - mid], a[i]);
                }
            }

            // now merge the two halves

            // Case 1:
            // simple case when max and min are both in same half
            for (int i = 0; i < 2; i++) {
                int other = i ^ 1;
                for (int idx = 1; idx < length[i]; idx++) {
                    int d = mx[i][idx] - mn[i][idx];
                    if(d + 1 < w) continue;
                    int idx2 = d - idx - 1;
                    if(idx2 >= 0 && idx2 < length[other] && mx[other][idx2] < mx[i][idx] && mn[other][idx2] > mn[i][idx]) {
                        res++;
                    }
                }
            }

            // Case 2:
            // tricky case when max on one side and min on other
            // max - min = i1 + i2 + 1
            // (max - i1) - (min + i2) = 1
            // we need to very carefully implement the following using a 3-pointer approach
            // minp: all elements from start upto minp are valid. only moves forward
            // maxp: all elements from maxp upto end are valid. only moves forward
            // wp: all elements from wp to end are valid. only moves backward
            for (int i = 0; i < 2; i++) {
                int other = i ^ 1;
                // we assume that i represents index of half that shall contain min
                int i2, wp;
                int minp = -1, maxp = 0;
                if(w > length[other]) {
                    wp = length[other] - 1;
                    i2 = w - 2 - wp;
                } else {
                    wp = w - 2;
                    i2 = 0;
                }
                for (int j = 0; j < length[other]; j++) {
                    cnt[mx[other][j] - j] = 0;
                }
                for (int j = 0; j < length[i]; j++) {
                    cnt[mn[i][j] + j + 1] = 0;
                }
                /*
                This commented code contains solution without the >= w constraint. It is provided only for better understanding purposes.
                The actual solution gets a little messy with the handling of additional wp pointer.

                while(i2 < length[i]) {
                    while(minp + 1 < length[other] && mn[other][minp + 1] > mn[i][i2]) {
                        minp ++;
                        cnt[mx[other][minp] - minp]++;
                    }
                    if(minp == -1) break;
                    while(maxp < length[other] && mx[other][maxp] < mx[i][i2]) {
                        cnt[mx[other][maxp] - maxp]--;
                        maxp++;
                    }
                    res += cnt[mn[i][i2] + i2 + 1];
                    i2++;
                }
                */
                // We shall maintain two artificial pointers l and r.
                // l will equal max(wp, maxp) and r will equal r.
                // note that l can increase as well as decrease while r only increases.
                // This still does not fault our target for O(N) merge as the decrease and increase occur in phases:
                // Phase 1: maxp < wp < minp
                // Here l will decrease
                // Phasse 2: wp < maxp < minp
                // Here l will only increase.

                int l = 0, r = -1;
                while (i2 < length[i]) {
                    int pl = l, pr = r;
                    //update minp
                    while (minp + 1 < length[other] && mn[other][minp + 1] > mn[i][i2]) {
                        minp++;
                    }

                    //update maxp
                    while (maxp < length[other] && mx[other][maxp] < mx[i][i2]) {
                        maxp++;
                    }
                    l = Math.max(maxp, wp);
                    r = minp;
                    if (l <= r) {
                        // Add the newly added elements
                        for (int idx = pr + 1; idx <= r; idx++) {
                            cnt[mx[other][idx] - idx]++;
                        }
                        if (l > pl) {
                            // some elements to be deleted
                            for (int idx = pl; idx < l; idx++) {
                                cnt[mx[other][idx] - idx]--;
                            }
                        } else if (l < pl) {
                            // Add elements on expanding to left
                            for (int idx = pl - 1; idx >= l; idx--) {
                                cnt[mx[other][idx] - idx]++;
                            }
                        }
                        int add = cnt[mn[i][i2] + i2 + 1];
                        res += add;
                    } else {
                        l = pl;
                        r = pr;
                    }
                    i2++;
                    wp--;
                }
            }

        }
        return res;
    }

    private boolean checkData() {
        int n = a.length;
        int map[] = new int[n + 1];
        for(int ele : a) {
            if(map[ele] == 1) return false;
            map[ele] = 1;
        }
        return true;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        w = in.readInt();
        a = IOUtils.readIntArray(in, n);
        mx = new int[2][n];
        mn = new int[2][n];
        cnt = new int[2 * n + 1];
        if(!checkData()) throw new RuntimeException("Invalid input");
        long ans = solveInstance(0, n - 1);
        out.printLine(ans);
    }
}

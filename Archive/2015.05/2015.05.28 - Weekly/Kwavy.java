package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

/*
Find the number of such values of (l,r) such that num is the kth wavy number in this range.
Maintain 1 <= l <= r <= 1e18.
 */

public class Kwavy {
    long dp[][][][];
    int a[], pos;

    /*
    dir
    0: no digit seen yet
    1: increasing
    2: decreasing
    3: saw only one digit, open to both directions
     */
    long go(int i, int pre, int lastDig, int dir) {
        if(i < 0)
            return dir == 0 ? 0 : 1;
        if(dp[i][pre][lastDig][dir] == -1) {
            long val = 0;
            int lim = 9;
            if(pre == 1) lim = a[i];
            if(dir == 0) {
                val += go(i - 1, 0, 0, 0);
                for (int dig = 1; dig < lim + 1; dig++) {
                    val += go(i - 1, (pre == 1 && dig == a[i]) ? 1 : 0, dig, 3);
                }
            } else if(dir == 1) {
                for (int dig = lastDig + 1; dig < lim + 1; dig++) {
                    val += go(i - 1, (pre == 1 && dig == a[i]) ? 1 : 0, dig, 2);
                }
            } else if(dir == 2) {
                for (int dig = 0; dig < lim + 1 && dig < lastDig; dig++) {
                    val += go(i - 1, (pre == 1 && dig == a[i]) ? 1 : 0, dig, 1);
                }
            } else {
                for (int dig = 0; dig < lim + 1; dig++) {
                    if(dig > lastDig) val += go(i - 1, (pre == 1 && dig == a[i]) ? 1 : 0, dig, 2);
                    if(dig < lastDig) val += go(i - 1, (pre == 1 && dig == a[i]) ? 1 : 0, dig, 1);
                }
            }
            dp[i][pre][lastDig][dir] = val;
        }
        return dp[i][pre][lastDig][dir];
    }

    private long getCount(long u) {
        pos = 0;
        a = new int[19];
        while(u > 0) {
            a[pos++] = (int)(u % 10);
            u /= 10;
        }
        dp = new long[pos][2][10][4];
        for (int i = 0; i < pos; i++) {
            for (int j = 0; j < 2; j++) {
                for (int lastDig = 0; lastDig < 10; lastDig++) {
                    for (int dir = 0; dir < 4; dir++) {
                        dp[i][j][lastDig][dir] = -1;
                    }
                }
            }
        }
        return go(pos - 1, 1, 0, 0);
    }

    private boolean isWavy(long num) {
        if(num < 10) return true;
        int dir = (num % 10) > ((num / 10) % 10) ? 2 : 1;
        int last = (int)(num % 10);
        num /= 10;
        while(num > 0) {
            int dig = (int) (num % 10);
            if(dir == 1 && dig <= last) return false;
            if(dir == 2 && dig >= last) return false;
            num /= 10;
            dir = dir == 1 ? 2 : 1;
            last = dig;
        }
        return true;
    }

    private long getCountBrute (final long num) {
        long number = 1;
        long cnt = 0;
        while (number <= num) {
            if(isWavy(number)) cnt++;
            //else System.out.println(number);
            number ++;
        }
        return cnt;
    }

    private void findKth(long k, long start) {
        long lo = start, hi = (long) 1e18, ans = start;
        long off = getCount(start);
        while(lo <= hi) {
            long mid = lo + hi >> 1;
            if(getCount(mid) - off >= k) {
                ans = mid;
                hi = mid - 1;
            } else lo = mid + 1;
        }
        System.out.println(ans);
    }

    private void findK(long k, long num) {
        long lo = 1, hi = num;
        long totalCount = getCount(num);
        //System.out.println(totalCount + " " + getCountBrute(num));
        long lastGood = -1, firstGood = -1;
        while(lo <= hi) {
            long mid = lo + hi >> 1;
            if(getCount(mid) >= totalCount - k) {
                firstGood = mid;
                hi = mid - 1;
            }
            else lo = mid + 1;
        }
        lo = 1;
        hi = num;
        while(lo <= hi) {
            long mid = lo + hi >> 1;
            if(getCount(mid) <= totalCount - k) {
                lastGood = mid;
                lo = mid + 1;
            }
            else hi = mid - 1;
        }
        System.out.println((firstGood + 1) + " " + (lastGood + 1));
    }

    private long getLeftBoundary(long num, long k) {
        long lo = 1, hi = num;
        long totalCount = getCount(num);
        if(k > totalCount) return -1;
        long ans = num;
        while(lo <= hi) {
            long mid = lo + hi >> 1;
            if(totalCount - getCount(mid) + (isWavy(mid) ? 1 : 0) <= k) {
                ans = mid;
                hi = mid - 1;
            } else lo = mid + 1;
        }
        return ans;
    }

    private long getRightBoundary(long num, long k) {
        long lo = num, hi = (long)1e18;
        long totalCount = getCount(num);
        if(k > getCount(hi) - totalCount) return -1;
        int add = isWavy(num) ? 1 : 0;
        long ans = num;
        while(lo <= hi) {
            long mid = lo + hi >> 1;
            if(getCount(mid) - totalCount + add <= k) {
                ans = mid;
                lo = mid + 1;
            } else hi = mid - 1;
        }
        return ans;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        long num = in.readLong();
        long k = in.readLong();
        out.printLine(getLeftBoundary(num, k) + " " + getRightBoundary(num, k));
    }
}

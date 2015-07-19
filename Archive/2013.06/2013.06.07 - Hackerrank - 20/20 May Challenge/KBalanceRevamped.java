package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class KBalanceRevamped {
    final long MOD = 1000000007;
    final myNode Sentinel = new myNode(0, 0);
    int truk;
    int arr[];
    long inv2 = 500000004;
    long pow10[];
    myNode dp[][][];
    myNode finDP[][][][];
    int start;

    public void solve(int testNumber, InReader in, OutputWriter out) {
        long l = in.readLong();
        long r = in.readLong();
        truk = in.readInt();
        pow10 = new long[19];
        pow10[0] = 1;
        for (int i = 1; i < 19; i++) {
            pow10[i] = pow10[i - 1] * 10;
        }
        long ans = (compute(r) - compute(l - 1)) % MOD;
        while (ans < 0) ans += MOD;
        out.printLine(ans);
    }

    long compute(long num) {
        if (num <= 0) return 0;
        //for lengths <=truk
        long ans = 0;
        long num2 = pow10[truk] - 1;
        if (num2 >= num) return sumToN(num);
        else ans = sumToN(num2);

        long res = num;
        int i = 0;
        arr = new int[19];
        while (res > 0) {
            arr[i++] = (int) (res % 10);
            res /= 10;
        }

        int ndigs = i;
        int k = 1;
        for (int j = truk + 1; j < ndigs; j++) {
            ans = (ans + find(j - 1, k)) % MOD;
            if (k + 1 <= truk) k++;
        }
        //System.out.println(ans);

        //calculation of answers for numbers with length with ndigs
        finDP = new myNode[19][90][2][2];
        //System.out.println("k = " + k);
        for (int sum = 1; sum <= k * 9; sum++) {
            //ans = (ans + findSum(digs, sum, 1, k).sum) % MOD;
            start = ndigs - 1;
            myNode p = findFinal(ndigs - 1, sum, 1, 1, k);
            //System.out.println(p);
            start = k - 1;
            myNode suf = findSum(k - 1, sum, 0, k);
            long add = (p.sum * suf.cnt) % MOD + (suf.sum * p.cnt) % MOD;
            //System.out.printf("%d %d %d %d\n",digs , k, sum, add);
            ans = (ans + add) % MOD;
        }
        //System.out.println("ans = " + ans);
        //Handling extra case for strictness of pre
        int sumk = 0;
        num2 = num;
        for (int cnt = 0; cnt < k; cnt++) {
            sumk += arr[ndigs - 1 - cnt];
            num2 -= pow10[cnt] * (arr[cnt]);
        }
        //System.out.println("num2 = " + num2);
        dp = new myNode[19][90][2];
        myNode suf = findSuf(k - 1, sumk, true);
        //System.out.println("suf = " + suf);
        long cntpre = (suf.cnt > 0) ? suf.cnt : 0;
        if (suf.sum > 0) ans = (ans + suf.sum) % MOD;
        start = k - 1;
        dp = new myNode[19][90][2];
        suf = findSum(k - 1, sumk, 0, k);
        if (suf.sum > 0) ans = ((ans - suf.sum) % MOD) % MOD;
        num2 %= MOD;
        ans = (ans + (num2 * (cntpre - suf.cnt)) % MOD) % MOD;
        while (ans < 0) ans += MOD;
        return ans;
    }

    myNode findSuf(int digs, int sum, boolean pre) {
        int lim = 9;
        int pr = pre ? 1 : 0;
        if (sum < 0) return Sentinel;
        if (dp[digs][sum][pr] != null) return dp[digs][sum][pr];
        if (pre) lim = Math.min(lim, arr[digs]);
        if (digs == 0) {
            //last digit
            if (sum <= lim) return new myNode(1, sum);
            else return Sentinel;
        }
        long retSum = 0;
        long retCnt = 0;
        for (int i = 0; i <= lim; i++) {
            myNode node = findSuf(digs - 1, sum - i, pre && i == arr[digs]);
            long s = node.sum;
            retSum = (retSum + (i * ((pow10[digs] % MOD) * node.cnt) % MOD) % MOD + node.sum) % MOD;
            retCnt = (retCnt + node.cnt) % MOD;

        }
        return dp[digs][sum][pr] = new myNode(retCnt, retSum);
    }

    //answers are computed considering number to be 999..
    long find(int digs, int k) {
        long ans = 0;

        dp = new myNode[19][90][2];
        for (int sum = 1; sum <= k * 9; sum++) {
            //ans = (ans + findSum(digs, sum, 1, k).sum) % MOD;
            start = digs;
            myNode p = findSum(digs, sum, 1, k);
            start = k - 1;
            myNode suf = findSum(k - 1, sum, 0, k);
            long add = (p.sum * suf.cnt) % MOD + (suf.sum * p.cnt) % MOD;
            //System.out.printf("%d %d %d %d\n",digs , k, sum, add);
            ans = (ans + add) % MOD;
        }
        return ans;
    }

    //handle the case when zero==1 and we are at end of prefix
    myNode findSum(int digs, int sum, int zero, int k) {
        int lim = 9;
        if (sum < 0) return Sentinel;
        //if(digs == k-1) return new myNode(1, 0);
        if (digs == 0) {
            if (sum <= lim) {
                return new myNode(1, sum);
            } else return Sentinel;
        }
        if (dp[digs][sum][zero] != null) return dp[digs][sum][zero];
        if (start - digs == k - 1) {
            if (sum <= lim) {
                int nHole = digs - k;
                //calc sum of all numbers in hole
                long n = pow10[nHole] - 1;
                long rsum = sumToN(n);
                long cnt = n + 1; //ek zero bhi to aayega
                rsum = (rsum * pow10[k]) % MOD;
                rsum = (rsum + (sum * (pow10[digs] % MOD) * cnt) % MOD) % MOD;
                return dp[digs][sum][zero] = new myNode(cnt, rsum);
            } else return Sentinel;
        }
        long cnt = 0, rsum = 0;
        for (int i = zero; i < 10; i++) {
            myNode node = findSum(digs - 1, sum - i, 0, k);
            cnt = (cnt + node.cnt) % MOD;
            rsum = (rsum + ((i * pow10[digs] % MOD) * node.cnt) % MOD + node.sum) % MOD;
        }
        return dp[digs][sum][zero] = new myNode(cnt, rsum);
    }

    myNode findFinal(int digs, int sum, int zero, int pre, int k) {
        int lim = 9;
        if (pre == 1) lim = Math.min(lim, arr[digs]);
        if (sum < 0) return Sentinel;
        if (digs == k - 1) return new myNode(1, 0);
        if (finDP[digs][sum][zero][pre] != null) return finDP[digs][sum][zero][pre];
        if (start - digs == k - 1) {
            if (sum <= lim) {
                myNode node = findFinal(digs - 1, 0, 0, pre == 1 && arr[digs] == sum ? 1 : 0, k);
                return finDP[digs][sum][zero][pre] = new myNode(node.cnt, ((sum * (pow10[digs] % MOD) * node.cnt) % MOD + node.sum) % MOD);
            } else return Sentinel;
        }
        boolean hole = false;
        if (start - digs > k - 1) hole = true;
        long cnt = 0, rsum = 0;
        for (int i = zero; i <= lim; i++) {
            myNode node;
            if (!hole) node = findFinal(digs - 1, sum - i, 0, pre == 1 && arr[digs] == i ? 1 : 0, k);
            else node = findFinal(digs - 1, 0, 0, pre == 1 && arr[digs] == i ? 1 : 0, k);

            cnt = (cnt + node.cnt) % MOD;
            rsum = (rsum + ((i * pow10[digs] % MOD) * node.cnt + node.sum) % MOD) % MOD;
        }
        return finDP[digs][sum][zero][pre] = new myNode(cnt, rsum);
    }

    long sumToN(long n) {
        n %= MOD;
        long num = (n * (n + 1)) % MOD;
        return (num * inv2) % MOD;
    }
}

class myNode {
    long cnt, sum;

    myNode(long cnt, long sum) {
        this.cnt = cnt;
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "[" + " " + cnt + " , " + sum + "]";
    }
}


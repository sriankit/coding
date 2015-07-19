package javaUtils;

public class HashStore {

    /**
     * This class maintains a static table of forward and backward hash and can be queried to retrieve either of them in O(1)
     * @Author: Ankit Srivastava
     *
     */

    public static final int HASH_CNT = 1;
    public static final long BASE = (long) (1e6 + 3);
    public static final long[] MOD = new long[] {(long) (1e9 + 9), (long) 1e9 + 7};
    private final long forwardHash[][], backwardHash[][];
    public static long PPOW[][];

    /**
     * @param s: The String whose hash is to be maintained
     *           Contains main logic of program where hash values are generated and stored.
     *           Assumes the string to be 1-based and ignores the first character.
     */
    public HashStore(char[] s) {
        int n = s.length - 1;
        forwardHash = new long[s.length + 1][HASH_CNT];
        backwardHash = new long[s.length + 1][HASH_CNT];
        PPOW = new long[s.length + 1][HASH_CNT];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j < HASH_CNT; j++) {
                if(i == 0) {
                    PPOW[i][j] = 1;
                } else {
                    //System.out.println("checkf = " + forwardHash[i - 1][j] * BASE);
                    forwardHash[i][j] = IntegerUtils.addWithMod(forwardHash[i - 1][j] * BASE, s[i], MOD[j]);
                    //forwardHash[i][j] = (forwardHash[i - 1][j] * BASE + s[i] - 'a' + 1) % MOD[j];
                    //PPOW[i][j] = (PPOW[i - 1][j] * BASE) % MOD[j];
                    PPOW[i][j] = IntegerUtils.addWithMod(0, PPOW[i - 1][j] * BASE, MOD[j]);
                }
            }
        }
        for (int i = n; i > 0; i--) {
            for (int j = 0; j < HASH_CNT; j++) {
                //backwardHash[i][j] = (backwardHash[i + 1][j] * BASE + s[i]) % MOD[j];
                backwardHash[i][j] = IntegerUtils.addWithMod(backwardHash[i + 1][j] * BASE, s[i], MOD[j]);
            }
        }
    }

    /**
     * @param i:  Start index of substring
     * @param j: End index of substring
     * @return: Forward HashValue of substring defined by s[st:end]
     */

    public long[] getForwardHash(int i, int j) {
        long[] ans = new long[HASH_CNT];
        for (int k = 0; k < HASH_CNT; k++) {
            //System.out.println("check = " + " " + forwardHash[i - 1][k] * PPOW[j - i + 1][k]);
            ans[k] = IntegerUtils.subtractWithMod(forwardHash[j][k], forwardHash[i - 1][k] * PPOW[j - i + 1][k], MOD[k]);
            //ans[k] = (forwardHash[j][k] - (forwardHash[i - 1][k] * PPOW[j - i + 1][k]) % MOD[k] + MOD[k]) % MOD[k];
            //System.out.println(ans[k]);
        }
        return ans;
    }

    /**
     * @param i:  Start index of substring
     * @param j: End index of substring
     * @return: Backward HashValue of substring defined by s[st:end]
     */

    public long[] getBackwardHash(int i, int j) {
        long[] ans = new long[HASH_CNT];
        for (int k = 0; k < HASH_CNT; k++) {
            ans[k] = IntegerUtils.subtractWithMod(backwardHash[i][k], (backwardHash[j + 1][k] * PPOW[j - i + 1][k]), MOD[k]);
            //ans[k] = (backwardHash[i][k] - (backwardHash[j + 1][k] * PPOW[j - i + 1][k]) % MOD[k] + MOD[k]) % MOD[k];
        }
        return ans;
    }

    public static void hashAppend(long[] hash, long[] add, int len) {
        for (int i = 0; i < HASH_CNT; i++) {
            hash[i] = IntegerUtils.addWithMod(hash[i] * PPOW[len][i], add[i], MOD[i]);
        }
    }

    public static void hashPrepend(long[] hash, long[] add, int len) {
        for (int i = 0; i < HASH_CNT; i++) {
            hash[i] = IntegerUtils.addWithMod(hash[i], add[i] * PPOW[len][i], MOD[i]);
        }
    }

    public static boolean compareHash(long[] hash1, long[] hash2) {
        for (int i = 0; i < HASH_CNT; i++) {
            if(hash1[i] != hash2[i]) return false;
        }
        return true;
    }
}

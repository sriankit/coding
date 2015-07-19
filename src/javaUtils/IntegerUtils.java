package javaUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class IntegerUtils {

    public static long subtractWithMod(long v1, long v2, long MOD) {
        if(v1 >= MOD) v1 %= MOD;
        if(v2 >= MOD) v2 %= MOD;
        long val = v1 - v2;
        if(val < 0) val += MOD;
        return val;
    }

    public static long addWithMod(long v1, long v2, long MOD) {
        if(v1 >= MOD) v1 %= MOD;
        if(v2 >= MOD) v2 %= MOD;
        long val = v1 + v2;
        if(val >= MOD) return val - MOD;
        else return val;
    }

    public static String getBinary(long a) {
        StringBuilder builder = new StringBuilder();
        while (a > 0) {
            builder.append(((a & 1) == 0) ? 0 : 1);
            a >>= 1;
        }
        builder.reverse();
        return builder.toString();
    }

    public static long gcd(long a, long b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

    public static int log2(long x) {
        if(x == 0) return 1;
        int j = 0;
        while(x > 0) {
            x >>= 1;
            j++;
        }
        return j;
    }

    public static long lcm(long a, long b) {
        return a / gcd(a, b) * b;
    }

    public static int longCompare(long a, long b) {
        if (a < b)
            return -1;
        if (a > b)
            return 1;
        return 0;
    }

    public static long[] generateFactorial(int count, long module) {
        long[] result = new long[count];
        if (module == -1) {
            if (count != 0)
                result[0] = 1;
            for (int i = 1; i < count; i++)
                result[i] = result[i - 1] * i;
        } else {
            if (count != 0)
                result[0] = 1 % module;
            for (int i = 1; i < count; i++)
                result[i] = (result[i - 1] * i) % module;
        }
        return result;
    }

    public static long[] generateReverse(int upTo, long module) {
        long[] result = new long[upTo];
        if (upTo > 1)
            result[1] = 1;
        for (int i = 2; i < upTo; i++)
            result[i] = (module - module / i * result[((int) (module % i))] % module) % module;
        return result;
    }

    public static long[] generateReverseFactorials(int upTo, long module) {
        long[] result = generateReverse(upTo, module);
        if (upTo > 0)
            result[0] = 1;
        for (int i = 1; i < upTo; i++)
            result[i] = result[i] * result[i - 1] % module;
        return result;
    }

    public static long[][] generateBinomialCoefficients(int n, long module) {
        long[][] result = new long[n + 1][n + 1];
        if (module == 1)
            return result;
        for (int i = 0; i <= n; i++) {
            result[i][0] = 1;
            for (int j = 1; j <= i; j++) {
                result[i][j] = result[i - 1][j - 1] + result[i - 1][j];
                if (result[i][j] >= module)
                    result[i][j] -= module;
            }
        }
        return result;
    }

    public static long[][] generateBinomialCoefficients(int n) {
        long[][] result = new long[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            result[i][0] = 1;
            for (int j = 1; j <= i; j++) {
                result[i][j] = result[i - 1][j - 1] + result[i - 1][j];
            }
        }
        return result;
    }

    public static long power(long base, long exponent, long mod) {
        long res = 1, k = base;
        if (k == 1) return 1 % mod;
        for (long p = exponent; p > 0; p >>= 1) {
            if ((p & 1) == 1) res = (res * k) % mod;
            k = (k * k) % mod;
        }
        return res % mod;
    }

    public static long power(long base, long exponent) {
        if (exponent == 0)
            return 1;
        long result = power(base, exponent >> 1);
        result = result * result;
        if ((exponent & 1) != 0)
            result = result * base;
        return result;
    }

    public static long reverse(long number, long module) {
        return power(number, module - 2, module);
    }

    public static int[] generatePrimes(int upTo) {
        boolean[] isPrime = generatePrimalityTable(upTo);
        List<Integer> primes = new ArrayList<Integer>();
        for (int i = 0; i < upTo; i++) {
            if (isPrime[i])
                primes.add(i);
        }
        return CollectionUtils.toArray(primes);
    }


    /**
     * @param n The number to be factorized
     * @return HashMap prime : power
     * @throws java.lang.IllegalArgumentException if n is too large for processing withing 1e8 bounds
     */
    public static HashMap<Long, Integer> getPrimeFactorization(long n)
            throws IllegalArgumentException {
        if (n > 1e15) throw new IllegalArgumentException("This will time out: use a method better than O(sqrt(n))");
        HashMap<Long, Integer> powers = new HashMap<>();
        for (long a = 2; a <= n / a; a++) {
            if (n % a == 0) {
                int exp = 0;
                while (n % a == 0) {
                    n /= a;
                    exp++;
                }
                powers.put(a, exp);
            }
        }
        if (n != 1) powers.put(n, 1);
        return powers;
    }

    public static boolean[] generatePrimalityTable(int upTo) {
        boolean[] isPrime = new boolean[upTo];
        Arrays.fill(isPrime, true);
        isPrime[0] = false;
        if(upTo > 1) isPrime[1] = false;
        for (int i = 2; i * i < upTo; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j < upTo; j += i)
                    isPrime[j] = false;
            }
        }
        return isPrime;
    }

    public static long powerInFactorial(long n, long p) {
        long result = 0;
        while (n != 0) {
            result += n /= p;
        }
        return result;
    }


    public static int sumDigits(CharSequence number) {
        int result = 0;
        for (int i = number.length() - 1; i >= 0; i--)
            result += digitValue(number.charAt(i));
        return result;
    }

    public static int digitValue(char digit) {
        if (Character.isDigit(digit))
            return digit - '0';
        if (Character.isUpperCase(digit))
            return digit + 10 - 'A';
        return digit + 10 - 'a';
    }

    public static boolean isPerfectSquare(long n) {
        long sqrt = (long) Math.sqrt(n);
        return (sqrt * sqrt) == n;
    }

    public static long calculateSumDivisors(long n) {
        long result = 1;
        for (long i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                long pn = i;
                do {
                    pn *= i;
                    n /= i;
                } while (n % i == 0);
                result *= (pn - 1) / (i - 1);
            }
        }
        if (n != 1)
            result *= n + 1;
        return result;
    }

}


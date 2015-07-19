package Tasks;

public class FizzBuzzTurbo {
    public long[] counts(long A, long B) {
        long mul3 = B / 3 - (A - 1) / 3;
        long mul5 = B / 5 - (A - 1) / 5;
        long common = B / 15 - (A - 1) / 15;
        return new long[]{mul3 - common, mul5 - common, common};
    }
}

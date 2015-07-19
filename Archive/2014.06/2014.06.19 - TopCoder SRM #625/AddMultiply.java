package Tasks;

public class AddMultiply {
    public int[] makeExpression(int y) {
        int a, b, c;
        for (int i = -1000; i < 1001; i++) {
            for (int j = -1000; j < 1001; j++) {

                if (i == 0 || i == 1 || j == 0 || j == 1) continue;
                int k = ((y - j) % i == 0 ? (y - j / i) : 0);
                if (k == 0 || k == 1) continue;
                return new int[]{i, k, j};
            }
        }
        return new int[0];
    }
}

package Tasks;

public class EllysNumberGuessing {
    public int getNumber(int[] guesses, int[] answers) {
        int[] poss = new int[]{guesses[0] + answers[0], guesses[0] - answers[0]};
        int mask = 3;
        int i = 0;
        for (int trial : poss) {
            if (trial > 1e9 || trial < 1) {
                mask ^= (1 << i);
                i++;
                continue;
            }
            for (int j = 0; j < guesses.length; j++) {
                if (Math.abs(guesses[j] - trial) != answers[j]) {
                    mask ^= (1 << i);
                    break;
                }
            }
            i++;
        }
        if (mask == 3) return -1;
        if (mask == 0) return -2;
        return poss[mask >> 1];
    }
}

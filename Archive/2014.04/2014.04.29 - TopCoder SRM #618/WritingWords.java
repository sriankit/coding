package Tasks;

public class WritingWords {
    public int write(String word) {
        int res = 0;
        for (int i = 0; i < word.length(); i++) {
            res += (word.charAt(i) - 'A' + 1);
        }
        return res;
    }
}

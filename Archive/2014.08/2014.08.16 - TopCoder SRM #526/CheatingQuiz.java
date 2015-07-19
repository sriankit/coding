package Tasks;

public class CheatingQuiz {
    public int[] howMany(String answers) {
        int a[] = new int[3];
        for (int i = 0; i < answers.length(); i++) {
            a[answers.charAt(i) - 'A']++;
        }
        int ret[] = new int[answers.length()];
        for (int i = 0; i < answers.length(); i++) {
            int ch = 0;
            for (int c : a)
                if (c > 0) ch++;
            ret[i] = ch;
            a[answers.charAt(i) - 'A'] --;
        }
        return ret;
    }
}

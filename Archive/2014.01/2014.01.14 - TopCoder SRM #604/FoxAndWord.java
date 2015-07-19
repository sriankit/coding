package Tasks;

public class FoxAndWord {
    public int howManyPairs(String[] words) {
        int ans = 0;
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < i; j++) {
                String s1 = words[i];
                String s2 = words[j];

                if (s1.length() != s2.length()) continue;
                for (int k = 0; k < s1.length() - 1; k++) {
                    if (s2.startsWith(s1.substring(k + 1)) && s2.endsWith(s1.substring(0, k + 1))) {
                        ans++;
                        break;
                    }
                }
            }
        }
        return ans;
    }
}

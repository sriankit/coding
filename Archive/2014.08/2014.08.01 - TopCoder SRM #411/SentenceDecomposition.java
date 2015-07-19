package Tasks;

import java.nio.charset.spi.CharsetProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SentenceDecomposition {

    public int hamming (String s1, String s2) {
        if(s1.length() != s2.length()) return  -1;
        char[] s1arr = s1.toCharArray();
        char[] s2arr = s2.toCharArray();
        Arrays.sort(s1arr);
        Arrays.sort(s2arr);
        if(!Arrays.equals(s1arr, s2arr)) return -1;
        int ans = 0;
        for (int i = 0; i < s1.length(); i++) {
            if(s1.charAt(i) != s2.charAt(i)) ans ++;
        }
        return ans;
    }

    public int decompose(String sentence, String[] validWords) {
        HashMap<Integer, ArrayList<String>> wordByLen = new HashMap<>();
        for(String word : validWords) {
            if(wordByLen.get(word.length()) == null) wordByLen.put(word.length(), new ArrayList<>());
            wordByLen.get(word.length()).add(word);
        }
        int dp[] = new int[sentence.length() + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        for (int i = 0; i < sentence.length(); i++) {
            for (int j = i; j >= 0; j--) {
                if(j - 1 >= 0 && dp[j - 1] == Integer.MAX_VALUE) continue;
                String sub = sentence.substring(j, i + 1);
                int len = sub.length();
                if(wordByLen.containsKey(len)) {
                    for(String valids : wordByLen.get(len)) {
                        int dist = hamming(sub, valids);
                        if(dist != -1) {
                            dp[i] = Math.min(dp[i], dist +  (j - 1 >= 0 ? dp[j - 1] : 0 ));
                        }
                    }
                }
            }
        }
        return dp[sentence.length() - 1] == Integer.MAX_VALUE ? -1 : dp[sentence.length() - 1];
    }
}

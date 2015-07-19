package Tasks;

public class LongWordsDiv2 {
    public String find(String word) {
        String ret[] = new String[]{"Dislikes", "Likes"};
        for (int i = 0; i < word.length() - 1; i++) {
            if (word.charAt(i) == word.charAt(i + 1)) return ret[0];
        }
        int cnt[] = new int[26];
        for (int i = 0; i < word.length(); i++) {
            cnt[word.charAt(i) - 'A']++;
            if (cnt[word.charAt(i) - 'A'] == 4) return ret[0];
        }
        int res = 1;
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                String temp = "";
                for (int k = 0; k < word.length(); k++) {
                    if (word.charAt(k) == (char) (i + 'A') || (word.charAt(k) == (char) (j + 'A')))
                        temp += word.charAt(k);
                }
                //System.out.println(temp);
                String temp2 = "";
                int cur = -1;
                for (int k = 0; k < temp.length(); k++) {
                    if (cur == -1 || temp2.charAt(cur) != temp.charAt(k)) {
                        temp2 += temp.charAt(k);
                        cur++;
                    }
                }
                if (cur >= 3) return ret[0];
            }
        }
        return ret[1];
    }
}

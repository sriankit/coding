package javaUtils;

public class StringProcess {
    int[] cntr = new int[26];

    public StringProcess(String s) {
        for (int i = 0; i < s.length(); i++) {
            cntr[s.charAt(i) - 'a']++;
        }
    }

    public String getCharset() {
        String ret = new String();
        for (int i = 0; i < 26; i++) {
            if (this.cntr[i] > 0) ret += i + 'a';
        }
        return ret;
    }

    public String getSorted() {
        String ret = new String();
        for (int i = 0; i < 26; i++) {
            while (this.cntr[i]-- != 0) ret += i + 'a';
        }
        return ret;
    }

    public int getFrequency(char c) {
        return this.cntr[c - 'a'];
    }
}

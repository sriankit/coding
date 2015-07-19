package Tasks;

import java.util.HashSet;

public class AlienAndPassword {
    public int getNumber(String S) {
        HashSet<String> set = new HashSet<String>();
        for (int i = 0; i < S.length(); i++) {
            String st = S.substring(0, i);
            st += S.substring(i + 1);
            set.add(st);
        }
        return set.size();
    }
}

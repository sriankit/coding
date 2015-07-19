package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.HashMap;

public class TwoRepetitionsBrute {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        String s = in.readString();
        int n = s.length();
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                String sub = s.substring(i, j + 1);
                int k = map.containsKey(sub) ? map.get(sub) : 0;
                map.put(sub, 1 + k);
            }
        }
        long ans = 0;
        for (String key : map.keySet()) {
            int count = map.get(key);
            if(count >= 2) ans++;
        }
        out.printLine(ans);
    }
}

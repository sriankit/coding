package Tasks;

import java.util.HashMap;

public class CubeStickers {
    public String isPossible(String[] sticker) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (String st : sticker) {
            if (!map.containsKey(st)) map.put(st, 1);
            else map.put(st, 2);
        }
        int cnt = 0;
        for (String st : map.keySet()) {
            cnt += map.get(st);
        }
        return cnt >= 6 ? "YES" : "NO";
    }
}

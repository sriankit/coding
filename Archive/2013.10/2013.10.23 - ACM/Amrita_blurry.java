package Tasks;

import javaUtils.IOUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;
import javaUtils.Pair;

import java.util.TreeMap;

public class Amrita_blurry {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int t = in.readInt();
        while (t-- > 0) {
            int r, c;
            r = in.readInt();
            c = in.readInt();
            char[][] table = IOUtils.readTable(in, r, c);
            TreeMap<Pair<String, Integer>, Integer> map = new TreeMap<Pair<String, Integer>, Integer>();
            String[][][] array = new String[r][c][c + 1];
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    StringBuilder build = new StringBuilder();
                    for (int len = 1; j + len - 1 < c; len++) {
                        build.append(table[i][j + len - 1]);
                        array[i][j][len] = build.toString();
                        //System.out.println(i + " " + j + " " + len + " " + array[i][j][len] );
                    }
                }
            }

            for (int len = 1; len <= c; len++) {
                for (int i = 0; i < r; i++) {
                    for (int j = 0; j + len - 1 < c; j++) {
                        StringBuilder build = new StringBuilder();
                        for (int k = i; k < r; k++) {
                            build.append(array[k][j][len]);
                            String tmp = build.toString();
                            /*if(tmp.contains("null")) {
                                System.out.println("debug" + " " + i + "  " + j + "  " + len);
                                continue;
                            } */
                            Pair<String, Integer> inst = Pair.makePair(tmp, len);
                            int cnt = 0;
                            if (map.containsKey(inst)) cnt = map.get(inst);
                            map.put(inst, cnt + 1);
                        }
                    }
                }
            }

            int ans = -1;

            for (Pair<String, Integer> p : map.keySet()) {
                if (map.get(p) > 1) {
                    ans = Math.max(ans, p.first.length());
                    //System.out.println(p.first);
                }

            }
            if (ans == -1) out.printLine(0);
            else out.printLine(ans);
        }
    }
}

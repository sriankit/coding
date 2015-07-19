package Tasks;

import javaUtils.BIT;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.TreeMap;

public class ORDERSET {
    final static int INSERT = 1;
    final static int DELETE = 2;
    final static int KTH = 3;
    final static int COUNT = 4;

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        int[][] data = new int[n][2];
        TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
        for (int i = 0; i < n; i++) {
            char c = in.readCharacter();
            switch (c) {
                case 'I':
                    data[i][0] = INSERT;
                    data[i][1] = in.readInt();
                    map.put(data[i][1], 1);
                    break;
                case 'D':
                    data[i][0] = DELETE;
                    data[i][1] = in.readInt();
                    map.put(data[i][1], 1);
                    break;
                case 'K':
                    data[i][0] = KTH;
                    data[i][1] = in.readInt();
                    break;
                case 'C':
                    data[i][0] = COUNT;
                    data[i][1] = in.readInt();
                    map.put(data[i][1], 1);
                    break;
                default:
                    throw new RuntimeException();
            }
        }
        int cnt = 2;
        int[] real = new int[n];
        for (int x : map.keySet()) {
            real[cnt] = x;
            map.put(x, cnt++);
        }

        for (int i = 0; i < n; i++) {
            if (data[i][0] != KTH) data[i][1] = map.get(data[i][1]);
        }
        BIT tree = new BIT(cnt + 1);
        boolean present[] = new boolean[cnt + 1];
        //TreeSet<Integer> presents = new TreeSet<Integer>();
        int total = 0;
        for (int i = 0; i < n; i++) {
            int num = data[i][1];
            switch (data[i][0]) {
                case INSERT:
                    if (!present[num]) {
                        tree.update(num, 1);
                        present[num] = true;
                        total++;
                        //presents.add(num);
                    }
                    break;
                case DELETE:
                    if (present[num]) {
                        tree.update(num, -1);
                        present[num] = false;
                        total--;
                        //presents.remove(num);
                    }
                    break;
                case COUNT:
                    out.printLine(tree.freqTo(num - 1));
                    break;
                case KTH:
                    if (num > total) {
                        out.printLine("invalid");
                        break;
                    }
                    int lo = 1, hi = cnt, ans = cnt;
                    while (lo <= hi) {
                        int mid = lo + hi >> 1;
                        int less = (int) tree.freqTo(mid);
                        if (less < num) lo = mid + 1;
                        else {
                            ans = mid;
                            hi = mid - 1;
                        }
                    }
                    out.printLine(real[ans]);
                    break;
                default:
                    throw new RuntimeException();
            }
        }
    }
}

package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;
import javaUtils.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class BonAppetit {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        HashMap<Integer, ArrayList<Pair<Integer, Integer>>> save;
        save = new HashMap<Integer, ArrayList<Pair<Integer, Integer>>>();
        int n = in.readInt();
        int k = in.readInt();
        for (int i = 0; i < n; i++) {
            int s = in.readInt();
            int f = in.readInt();
            int choice = in.readInt();
            ArrayList<Pair<Integer, Integer>> l = new ArrayList<Pair<Integer, Integer>>();
            if (save.containsKey(choice)) l = save.get(choice);
            l.add(Pair.makePair(f, s));
            save.put(choice, l);
        }
        int taken = 0;
        for (int room : save.keySet()) {
            ArrayList<Pair<Integer, Integer>> list = save.get(room);
            Collections.sort(list);
            int last = 0;
            for (Pair<Integer, Integer> p : list) {
                if (p.second >= last) {
                    taken++;
                    last = p.first;
                }
            }
        }
        out.printLine(taken);
    }
}

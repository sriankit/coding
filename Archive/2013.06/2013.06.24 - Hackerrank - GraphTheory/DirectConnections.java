package Tasks;

import javaUtils.BIT;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;
import java.util.Comparator;

public class DirectConnections {
    long MOD = 1000000007;

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        City[] cities = new City[n];
        for (int i = 0; i < n; i++) {
            cities[i] = new City(0, 0);
            cities[i].dist = in.readInt();
        }
        for (int i = 0; i < n; i++) {
            cities[i].pop = in.readInt();
        }

        Arrays.sort(cities, new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                if (o1.pop == o2.pop) return o1.dist - o2.dist;
                else return o1.pop - o2.pop;
            }
        });

        long pre[] = new long[n];
        pre[0] = cities[0].dist;
        cities[0].ind = 0;
        for (int i = 1; i < n; i++) {
            pre[i] = pre[i - 1] + cities[i].dist;
            if (pre[i] >= MOD) pre[i] %= MOD;
            cities[i].ind = i;
        }

        City[] arr = new City[n];
        System.arraycopy(cities, 0, arr, 0, cities.length);
        Arrays.sort(arr, new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                return o1.dist - o2.dist;
            }
        });

        for (int i = 0; i < n; i++) {
            cities[arr[i].ind].hash = i + 1;
        }

        /*for (int i = 0; i < n; i++) {
            System.out.println(cities[i]);
        } */

        BIT tree = new BIT(n + 1, MOD);
        BIT cntTree = new BIT(n + 1);
        cntTree.update(cities[0].hash, 1);
        tree.update(cities[0].hash, cities[0].dist);
        long fin = 0;
        for (int i = 1; i < n; i++) {
            int popu = cities[i].pop;
            long lessSm = tree.freqTo(cities[i].hash);
            long grtSum = pre[i - 1] - lessSm;
            long lessCnt = cntTree.freqTo(cities[i].hash);
            long ans = grtSum - lessSm;
            long cnt = i - lessCnt - lessCnt;
            ans -= (cnt * cities[i].dist) % MOD;
            ans = (ans * popu) % MOD;
            fin += ans;
            tree.update(cities[i].hash, cities[i].dist);
            cntTree.update(cities[i].hash, 1);
            //System.out.println(ans);
            if (Math.abs(fin) >= MOD) fin %= MOD;
            if (fin < 0) fin += MOD;

        }
        out.printLine(fin);
    }

    class City {
        int dist, pop;
        int ind, hash;

        City(int dist, int pop) {
            this.dist = dist;
            this.pop = pop;
        }

        @Override
        public String toString() {
            return "[ " + " pop = " + this.pop + " dist =  " + this.dist + " ind = " + this.ind + " hash = " + hash;
        }
    }
}

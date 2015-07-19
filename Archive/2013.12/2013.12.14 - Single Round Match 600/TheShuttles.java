package Tasks;

public class TheShuttles {
    public int getLeastCost(int[] cnt, int baseCost, int seatCost) {
        int ans = Integer.MAX_VALUE;
        for (int cap = 1; cap < 101; cap++) {
            int cost1 = baseCost + seatCost * cap;
            int num = 0;
            for (int i = 0; i < cnt.length; i++) {
                num += cnt[i] / cap + 1;
                if (cnt[i] % cap == 0) num--;
            }
            ans = Math.min(ans, num * cost1);
        }
        return ans;
    }
}

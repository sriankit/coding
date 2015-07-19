package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class Digits {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int t = in.readInt();
        while (t-- > 0) {
            String num = in.readString();
            //System.out.println(num);
            char[] digs = num.toCharArray();
            int cnt[] = new int[10];
            for (char c : digs) cnt[c - 48]++;
            int ans = 0;
            for (int i = 1; i < 10; i++) {
                if (!num.contains("" + i)) continue;
                boolean divides = true;
                int number = 0;
                for (int j = 0; j < digs.length; j++) {
                    //System.out.println("number = " + number + " for i = " + i);
                    number = (number * 10) + digs[j] - 48;
                    //System.out.println("number = " + number + " for i = " + i);
                    number %= i;
                }
                //System.out.println("number = " + number + " for i = " + i);
                divides = number == 0;
                if (divides) ans += cnt[i];
            }
            out.printLine(ans);
            //System.out.println("");
        }
    }
}

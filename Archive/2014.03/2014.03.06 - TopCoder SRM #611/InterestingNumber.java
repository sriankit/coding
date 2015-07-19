package Tasks;

public class InterestingNumber {
    public String isInteresting(String x) {
        boolean fail = false;
        for (char c : "0123456789".toCharArray()) {
            int i = x.indexOf(c);
            if (i != -1) {
                int i2 = -1;
                int t = i + 1;
                while (t < x.length()) {
                    if (x.charAt(t) == c) {
                        if (i2 != -1) {
                            fail = true;
                            break;
                        } else i2 = t;
                    }
                    t++;
                }
                if (i2 == -1) fail = true;
                else if (i2 - i - 1 != (c - '0')) fail = true;
            }
        }
        if (fail) return "Not interesting";
        else return "Interesting";
    }
}

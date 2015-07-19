package Tasks;

public class PowerOfThreeEasy {
    public String ableToGet(int x, int y) {
        String xs = "";
        while (x > 0) {
            xs += (x % 3);
            x /= 3;
        }
        String ys = "";
        while (y > 0) {
            ys += (y % 3);
            y /= 3;
        }
        int len = Math.max(xs.length(), ys.length());
        while (xs.length() < len) xs += "0";
        while (ys.length() < len) ys += "0";

        boolean fail = false;

        for (int i = 0; i < len; i++) {
            if (xs.charAt(i) - '0' + ys.charAt(i) - '0' != 1) fail = true;
        }
        if (fail) return "Impossible";
        else return "Possible";
    }
}

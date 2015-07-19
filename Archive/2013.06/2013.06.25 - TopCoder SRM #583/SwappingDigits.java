package Tasks;

public class SwappingDigits {
    public String minNumber(String num) {
        boolean dn = false;
        int a = -1, b = -1;
        for (int i = 0; i < num.length() && !dn; i++) {
            for (int j = i == 0 ? 1 : 0; j < 10 && !dn; j++) {
                if (num.charAt(i) - '0' <= j) {
                    //System.out.println(num.charAt(i));
                    break;
                }
                for (int k = num.length() - 1; k > i; k--) {
                    if (num.charAt(k) - '0' == j) {
                        a = i;
                        b = k;
                        dn = true;
                        break;
                    }
                }

            }
        }
        if (!dn) return num;
        String ret = "";
        for (int i = 0; i < num.length(); i++) {
            if (i == a) ret += num.charAt(b);
            else if (i == b) ret += num.charAt(a);
            else ret += num.charAt(i);
        }
        return ret;
    }
}

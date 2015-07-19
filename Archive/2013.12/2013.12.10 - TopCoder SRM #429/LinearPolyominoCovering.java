package Tasks;

public class LinearPolyominoCovering {
    public String findCovering(String region) {
        String res = "";
        for (int i = 0; i < region.length(); ) {
            if (region.charAt(i) == 'X') {
                try {
                    if (i + 4 <= region.length() && region.substring(i, i + 4).equals("XXXX")) {
                        for (int j = 0; j < 4; j++) {
                            res += 'A';
                        }
                        i += 4;
                    } else if (i + 2 <= region.length() && region.substring(i, i + 2).equals("XX")) {
                        res += "BB";
                        i += 2;
                    } else return "impossible";
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("dnd");
                    return "impossible";
                }
            } else {
                res += '.';
                i++;
            }
        }
        return res;
    }
}

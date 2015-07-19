package Tasks;

public class SpamChecker {
    public String spamCheck(String judgeLog, int good, int bad) {
        int sco = 0;
        for (int i = 0; i < judgeLog.length(); i++) {
            if (judgeLog.charAt(i) == 'o') sco += good;
            else {
                sco -= bad;
                if (sco < 0) return "SPAM";
            }
        }
        return "NOT SPAM";
    }
}

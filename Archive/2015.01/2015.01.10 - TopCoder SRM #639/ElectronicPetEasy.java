package Tasks;

public class ElectronicPetEasy {
    public String isDifficult(int st1, int p1, int t1, int st2, int p2, int t2) {
        int feed[] = new int[1000006];
        for (int i = 0; i < t1; i++) {
            feed[st1 + p1 * i] += 1;
        }
        for (int i = 0; i < t2; i++) {
            feed[st2 + p2 * i] += 2;
        }
        int res = 1;
        for (int i = 0; i < feed.length; i++) {
            if(feed[i] == 3) res = 0;
        }
        if(res == 1) return "Easy";
        else return "Difficult";
    }
}

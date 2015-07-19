package Tasks;

public class FoxSequence {
    public String isValid(int[] seq) {
        if (seq.length == 1) return "NO";
        int da = seq[1] - seq[0];
        if (da <= 0) return "NO";
        int i = 2;
        int n = seq.length;
        while (i < n) {
            if (seq[i] > seq[i - 1]) {
                if (seq[i] - seq[i - 1] != da) return "NO";
                i++;
            } else break;
        }
        if (i == n) return "NO";
        System.out.println("i = " + i);
        int db = seq[i] - seq[i - 1];
        if (db >= 0) return "NO";
        while (i < n) {
            if (seq[i] < seq[i - 1]) {
                if (seq[i] - seq[i - 1] != db) return "NO";
                i++;
            } else break;
        }
        if (i == n) return "NO";
        System.out.println("i = " + i);
        while (i < n && seq[i] == seq[i - 1]) i++;
        if (i == n) return "NO";
        System.out.println("i = " + i);
        int dc = seq[i] - seq[i - 1];
        if (dc <= 0) return "NO";
        while (i < n) {
            if (seq[i] > seq[i - 1]) {
                if (seq[i] - seq[i - 1] != dc) return "NO";
                i++;
            } else break;
        }
        if (i == n) return "NO";
        System.out.println("i = " + i);
        int dd = seq[i] - seq[i - 1];
        if (dd >= 0) return "NO";
        while (i < n) {
            if (seq[i] < seq[i - 1]) {
                if (seq[i] - seq[i - 1] != dd) return "NO";
                i++;
            } else break;
        }
        if (i == n) return "YES";
        return "NO";
    }
}

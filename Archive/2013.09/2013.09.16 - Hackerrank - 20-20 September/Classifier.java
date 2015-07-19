package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.io.PrintWriter;

public class Classifier {
    public void solve(int testNumber, InReader in, OutputWriter out) throws IllegalArgumentException {
        int n = in.readInt();
        PrintWriter pw = new PrintWriter(System.out);

        String[] topics = {"electronics", "mathematica", "android", "security", "gis", "photo", "scifi"};
        boolean next = false;
        for (int i = 0; i < n; i++) {
            String s = in.readLine();
            if (s.charAt(0) == '{') {
                if (next) out.printLine(topics[(int) (Math.random() * 7)]);
                next = true;
            }
            for (String check : topics) {
                if (s.contains(check)) {
                    out.printLine(check);
                    next = false;
                    break;
                }
            }

        }

    }
}

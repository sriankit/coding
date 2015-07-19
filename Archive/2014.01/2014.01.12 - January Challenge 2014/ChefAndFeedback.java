package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class ChefAndFeedback {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        String s = in.readString();
        if (s.contains("010") || s.contains("101")) out.printLine("Good");
        else out.printLine("Bad");
    }
}

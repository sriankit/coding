package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLtags {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        String pat = "<\\s*\\w+(\\s*\\w+(\\s*=(\\s*['\"].*?['\"]|\\S+))?)*";
        String rgx = "\\w+";
        int n = in.readInt();
        String doc = "";
        for (int i = 0; i < n; i++) {
            doc += in.readLine();
        }
        //System.out.println(doc);
        TreeSet<String> set = new TreeSet<String>();
        Pattern pattern = Pattern.compile(pat);
        Pattern tagPat = Pattern.compile(rgx);
        Matcher matcher = pattern.matcher(doc);
        while (matcher.find()) {
            String s = matcher.group();
            //System.out.println(s);
            Matcher tagMatcher = tagPat.matcher(s);
            if (tagMatcher.find()) set.add(tagMatcher.group());
        }
        String ans = "";
        for (String s : set)
            ans += (s + ";");
        for (int i = 0; i < ans.length() - 1; i++) {
            out.print(ans.charAt(i));
        }
    }
}

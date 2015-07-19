package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLAttributes {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        String pat = "<\\s*\\w+(\\s*\\w+(\\s*=(\\s*['\"].*?['\"]|\\S+))?)*";
        String rgx = "\\w+";
        int n = in.readInt();
        String doc = "";
        for (int i = 0; i < n; i++) {
            doc += in.readLine();
        }
        //System.out.println(doc);
        TreeMap<String, TreeSet<String>> map = new TreeMap<String, TreeSet<String>>();
        Pattern pattern = Pattern.compile(pat);
        Pattern tagPat = Pattern.compile(rgx);
        Matcher matcher = pattern.matcher(doc);
        while (matcher.find()) {
            String s = matcher.group();
            System.out.println(s);
            Matcher tagMatcher = tagPat.matcher(s);
            if (tagMatcher.find()) {
                String tag = tagMatcher.group();
                TreeSet<String> attr = map.get(tag);
                if (tagMatcher.find()) {
                    if (attr == null) attr = new TreeSet<String>();
                    attr.add(tagMatcher.group());
                }
                map.put(tag, attr);
            }

        }
        for (String tag : map.keySet()) {
            String ans = tag + ':';
            TreeSet<String> att = map.get(tag);
            if (att == null) out.printLine(ans);
            else {
                for (String s : att)
                    ans += s + ',';
                for (int i = 0; i < ans.length() - 1; i++) {
                    out.print(ans.charAt(i));
                }
                out.print('\n');
            }
        }
    }
}

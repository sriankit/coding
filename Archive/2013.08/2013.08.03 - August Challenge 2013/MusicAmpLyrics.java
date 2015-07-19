package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeMap;

public class MusicAmpLyrics {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int wordCount = in.readInt();
        HashSet<String> set = new HashSet<String>();
        FullAhoCorasick ds = new FullAhoCorasick(80, '-');
        String[] strings = new String[wordCount];
        for (int i = 0; i < wordCount; i++) {
            strings[i] = in.readString();
            if (set.contains(strings[i])) continue;
            set.add(strings[i]);
            //ds.addString(strings[i], i);
        }
        int id = 0;
        for (String s : set) {
            ds.addString(s, id);
            id++;
        }
        ds.build();
        int N = in.readInt();
        for (int i = 0; i < N; i++) {
            String s = in.readString();
            for (int j = 0; j < s.length(); j++) {
                ds.next(s.charAt(j));
            }
        }
        TreeMap<String, Long> map = new TreeMap<String, Long>();
        id = 0;
        for (String s : set) {
            map.put(s, ds.counts[id]);
            id++;
        }
        for (int i = 0; i < wordCount; i++) {
            out.printLine(map.get(strings[i]));
        }
    }
}

class FullAhoCorasick {
    static Node root;
    static Node NODE;
    int alphabetSize;
    char zero;
    long[] counts;
    int strs;

    public FullAhoCorasick(int size, char zero) {
        this.zero = zero;
        alphabetSize = size;
        root = new Node(-1);
        NODE = root;
        strs = 0;
    }

    void addString(String str, int id) {
        Node node = root;
        for (int i = 0; i < str.length(); i++) {
            int c = str.charAt(i) - zero;
            if (node.children[c] == null) node.children[c] = new Node(c);
            node.children[c].parent = node;
            node = node.children[c];
        }
        node.endID = id;
        strs++;
    }

    void build() {
        counts = new long[strs];
        Queue<Node> queue = new LinkedList<Node>();
        queue.offer(root);
        root.fall = root;
        while (queue.size() > 0) {
            Node node = queue.poll();
            for (int i = 0; i < alphabetSize; i++) {
                if (node.children[i] != null) queue.offer(node.children[i]);
            }
            if (node == root) continue;
            //Now we calculate fall for node
            Node par = node.parent.fall;
            while (par.children[node.c] == null && par != root) par = par.fall;
            if (par.children[node.c] != null && par.children[node.c] != node) node.fall = par.children[node.c];
            else node.fall = root;
        }
    }

    void next(char c) {
        int ind = c - zero;
        while (NODE != root && NODE.children[ind] == null) NODE = NODE.fall;
        if (NODE.children[ind] == null) NODE = root;
        else {
            NODE = NODE.children[ind];
            Node res = NODE;
            while (res != root) {
                if (res.endID != -1) counts[res.endID]++;
                res = res.fall;
                //System.out.println("going" + res + "   " + root);
            }
        }
    }

    class Node {
        int c;
        Node[] children;
        int endID;
        Node parent;
        Node fall;

        Node(int c) {
            this.c = c;
            children = new Node[alphabetSize];
            endID = -1;
        }

        @Override
        public String toString() {
            return " " + c + ((endID != -1) ? "_" : " ");
        }
    }
}

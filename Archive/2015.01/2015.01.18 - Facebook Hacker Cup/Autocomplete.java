package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

public class Autocomplete {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        Trie trie = new Trie();
        long ans = 0;
        for (int i = 0; i < n; i++) {
            String s = in.readString();
            ans += trie.search(s);
            trie.insert(s);
        }
        out.printf("Case #%d: %d\n", testNumber, ans);
    }

    class Trie {
        Node root;
        Trie() {
            root = new Node(' ');
        }

        void insert(String s) {
            Node cur = root;
            for (char c : s.toCharArray()) {
                int id = c - 'a';
                if(cur.children[id] == null) {
                    cur.children[id] = new Node(c);
                }
                cur = cur.children[id];
            }
            cur.leaf = true;
        }

        int search(String s) {
            Node cur = root;
            int cnt = 0;
            for (char c : s.toCharArray()) {
                int id = c - 'a';
                if(cur.children[id] == null) return cnt + 1;
                else cnt++;
            }
            return cnt + 1;
        }

    }

    class Node {
        char c;
        Node[] children;
        boolean leaf;

        Node(char c) {
            this.c = c;
            children = new Node[26];
            leaf = false;
        }
    }
}

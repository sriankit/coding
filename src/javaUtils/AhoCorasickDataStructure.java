package javaUtils;

import java.util.LinkedList;
import java.util.Queue;

/*

Maintains a trie structure for these strings and fail functions for every nodes
MOre info --> http://blog.ivank.net/aho-corasick-algorithm-in-as3.html
 */
public class AhoCorasickDataStructure {
    public int[][] trans;
    public boolean[] found;
    public int[] counts;
    Node root;
    int stateCount;
    int alphabetSize;
    char zero;
    int strCount;
    Node[] getNode;

    public AhoCorasickDataStructure(int size, char zero) {
        this.alphabetSize = size;
        this.zero = zero;
        root = new Node(' ');
        strCount = 0;
    }

    public void add(String s, int id) {
        Node node = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int ind = c - zero;
            if (node.child[ind] == null) node.child[ind] = new Node(c);
            node.child[ind].parent = node;
            node = node.child[ind];
        }
        node.isEnd = true;
        node.endId = id;
        strCount++;
        //System.out.println("Add success");
    }

    public long getCloseMatch(String s) {
        Node node = root;
        long res = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = (s.charAt(i) == '0') ? '1' : '0';
            int ind = c - zero;
            if (node.child[ind] == null) ind = (ind == 0) ? 1 : 0;
            res <<= 1;
            res += ind;
            node = node.child[ind];
            //System.out.println("res = " + res);
        }
        return res;
    }

    public void bfs() {
        counts = new int[strCount];
        root.fall = root;
        Queue<Node> queue = new LinkedList<Node>();
        stateCount = 0;
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node v = queue.poll();

            v.state = stateCount++;
            for (Node child : v.child)
                if (child != null) queue.offer(child);
            if (v == root) continue;
            Node fall = v.parent.fall;
            while (fall.child[v.c - zero] == null && fall != root) fall = fall.fall;
            v.fall = fall.child[v.c - zero];
            if (v.fall == null || v.fall == v) v.fall = root;
            else {
                Node temp = fall.child[v.c - zero];
                if (temp.isEnd) {
                    v.isEnd |= temp.isEnd;
                    //v.endId = temp.endId;
                }
            }
        }
        //System.out.println("bfs success");
    }

    public void makeTrans() {
        getNode = new Node[stateCount];
        trans = new int[stateCount][alphabetSize];
        found = new boolean[stateCount];
        Queue<Node> queue = new LinkedList<Node>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node v = queue.poll();
            getNode[v.state] = v;
            if (v.isEnd) found[v.state] = true;
            for (int i = 0; i < v.child.length; i++) {
                Node node = v;
                if (v.child[i] != null) queue.offer(v.child[i]);
                while (node.child[i] == null && node != root) node = node.fall;
                if (node.child[i] != null) {
                    trans[v.state][i] = node.child[i].state;
                } else trans[v.state][i] = root.state;
            }
        }


    }

    public void getCount(int state) {
        Node no = getNode[state];
        while (no != root) {
            if (no.isEnd && no.endId != -1) counts[no.endId]++;
            no = no.fall;
        }

    }


    class Node {
        char c;
        Node[] child;
        boolean isEnd;
        Node parent;
        Node fall;
        int state;
        int endId;

        Node(char c) {
            this.c = c;
            child = new Node[alphabetSize];
            isEnd = false;
            parent = null;
            fall = null;
            endId = -1;
        }
    }
}
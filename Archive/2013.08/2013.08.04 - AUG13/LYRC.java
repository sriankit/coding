package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.LinkedList;
import java.util.Queue;

public class LYRC {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int w = in.readInt();

        MyOptimizedAhoCorasick ds = new MyOptimizedAhoCorasick(w);
        for (int i = 0; i < w; i++) {
            ds.addString(in.readString());
        }

        ds.build();
        int N = in.readInt();
        for (int i = 0; i < N; i++) {
            String s = in.readString();
            myNode node = MyOptimizedAhoCorasick.root;
            int mul = 1;
            for (int j = 0; j < s.length(); j++) {
                myNode pNode = node;
                node = node.next[ds.getInt[s.charAt(j)]];
                if (node == pNode && j != s.length() - 1) {
                    mul++;
                    continue;
                }
                for (myNode res = pNode; res != MyOptimizedAhoCorasick.root && res != null; res = res.jump)
                    if (res.endId != -1) ds.count[res.endId] += mul - 1;
                for (myNode res = node; res != MyOptimizedAhoCorasick.root && res != null; res = res.jump)
                    if (res.endId != -1) ds.count[res.endId]++;
                mul = 1;
                //while(res != null)    ;
                //for(int idx : node.accept) ds.count[idx]++;
            }
        }

        for (int c : ds.countIdx) {
            out.printLine(ds.count[c]);
        }
    }


}

class MyOptimizedAhoCorasick {
    static myNode root;
    int[] count; //will maintain count of occurrences of sting at index idx
    int[] countIdx; //will redirect count query to count on previous occurrence of that string
    //we will keep a stable count - means the count array will reflect exactly with entry of strings
    int strCount;
    int getInt[];
    int id;

    public MyOptimizedAhoCorasick(int strCount) {
        this.strCount = strCount;
        count = new int[strCount];
        countIdx = new int[strCount];
        root = new myNode(-1);
        genInts();
        id = 0;
    }

    private void genInts() {
        getInt = new int[130];
        getInt['-'] = 0;
        int id = 1;
        for (int i = '0'; i <= '9'; i++) {
            getInt[i] = id++;
        }
        for (int i = 'a'; i <= 'z'; i++) {
            getInt[i] = id++;
        }

        for (int i = 'A'; i <= 'Z'; i++) {
            getInt[i] = id++;
        }
        assert (id == 64);
    }

    void addString(String s) {
        myNode node = root;
        for (int i = 0; i < s.length(); i++) {
            int c = getInt[s.charAt(i)];
            if (node.next[c] == null) {
                node.next[c] = new myNode(c);
            }
            node = node.next[c];
        }
        if (node.endId == -1) {
            countIdx[id] = id;
            node.endId = id;
            //node.accept.add(id);
        } else {
            countIdx[id] = node.endId;
        }
        id++;
    }

    //same build code as earlier
    void build() {
        Queue<myNode> q = new LinkedList<myNode>();
        root.failureLink = root;
        for (int i = 0; i < root.next.length; i++) {
            if (root.next[i] == null) {
                root.next[i] = root;
            } else {
                root.next[i].failureLink = root;
                q.add(root.next[i]);
            }
        }

        while (!q.isEmpty()) {
            final myNode p = q.poll();
            for (int i = 0; i < p.next.length; i++) {
                if (p.next[i] == null) {
                    p.next[i] = p.failureLink.next[i];
                } else {
                    for (myNode fail = p.failureLink; ; fail = fail.failureLink) {
                        if (fail.next[i] != null) {
                            p.next[i].failureLink = fail.next[i];

                            //p.next[i].accept.addAll(fail.next[i].accept);
                            q.add(p.next[i]);
                            break;
                        }
                    }
                    myNode jump = p.next[i].failureLink;
                    while (jump != root && jump.endId == -1) jump = jump.failureLink;
                    p.next[i].jump = jump;
                    //System.out.println("c  = " + jump.c + "  " + jump.endId);
                }
            }
        }
    }
}

class myNode {
    int c;
    //ArrayList<Integer> accept;
    myNode[] next;
    myNode failureLink;
    int endId;
    myNode jump;

    myNode(int c) {
        this.c = c;
        //accept = new ArrayList<Integer>();
        next = new myNode[64];
        failureLink = null;
        endId = -1;
    }
}
package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

public class QTREE {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            int n = in.readInt();

            shared.edges = new edge[n - 1];
            int degree[] = new int[n];
            int adjList[][] = new int[n][];
            shared.map = new TreeMap[n];
            shared.hash = new int[n - 1];
            shared.endHash = new int[n];

            for (int i = 0; i < n - 1; i++) {
                int a = in.readInt() - 1;
                int b = in.readInt() - 1;
                int c = in.readInt();

                degree[a]++;
                degree[b]++;
                shared.edges[i] = new edge(i, a, b, c);
            }
            for (int i = 0; i < n; i++) {
                adjList[i] = new int[degree[i]];
                shared.map[i] = new TreeMap<Integer, edge>();
            }
            int cntr[] = new int[n];

            for (edge edge : shared.edges) {
                int a = edge.frm;
                int b = edge.to;
                adjList[a][cntr[a]++] = b;
                adjList[b][cntr[b]++] = a;

                shared.map[a].put(b, edge);
                shared.map[b].put(a, edge);
            }

            HeavyLightDecomposition decomposition = new HeavyLightDecomposition(adjList);

            String s = "DONE";
            String read = in.readString();
            while (!read.equals(s)) {
                if (read.equals("CHANGE")) {
                    int i = in.readInt();
                    int ti = in.readInt();
                    decomposition.update(i - 1, ti);
                } else if (read.equals("QUERY")) {
                    int a = in.readInt();
                    int b = in.readInt();
                    out.printLine(decomposition.query(a - 1, b - 1));
                }
                read = in.readString();
            }
        }
    }

}

class shared {
    static edge[] edges;
    static TreeMap<Integer, edge> map[];
    static int hash[];
    static int endHash[];
}

class edge {
    int frm, to, cost, id;

    edge(int id, int frm, int to, int cost) {
        this.id = id;
        this.cost = cost;
        this.frm = frm;
        this.to = to;
    }
}

class HeavyLightDecomposition {
    int n;
    int adjList[][];
    int subtreeSize[];
    int parent[];
    int tym;
    Chain[] chains;
    int chainCount;
    int[] chainID;
    int[] depth;

    HeavyLightDecomposition(int adjList[][]) {
        n = adjList.length;

        depth = new int[n];
        this.adjList = adjList;
        subtreeSize = new int[n];
        parent = new int[n];
        chainID = new int[n];
        chains = new Chain[n];
        chainCount = 0;

        dfs(0, -1, 0);
        process(0, null, -1);
        for (int i = 0; i < chainCount; i++) {
            chains[i].buildTree();

        }

    }

    private int findLCA(int a, int b) {
        while (chainID[a] != chainID[b]) {
            if (depth[chains[chainID[a]].head] < depth[chains[chainID[b]].head]) {
                b = parent[chains[chainID[b]].head];
            } else a = parent[chains[chainID[a]].head];
        }

        if (depth[a] < depth[b])
            return a;
        return b;
    }

    private void process(int v, Chain chain, int parent) {
        if (chain == null) {
            chain = new Chain(v, chainCount);
            chainID[v] = chain.id;
            chains[chainCount++] = chain;
        }
        for (int child : adjList[v]) {
            if (child == parent) continue;
            if (subtreeSize[child] > subtreeSize[v] / 2) {       //found heavy child
                chain.add(child);
                chainID[child] = chain.id;
                process(child, chain, v);
            } else {
                process(child, null, v);
            }
        }
    }

    private void dfs(int v, int par, int d) {
        depth[v] = d;
        parent[v] = par;
        int tymIn = tym++;
        for (int i : adjList[v]) {
            if (i != par) dfs(i, v, d + 1);
        }
        subtreeSize[v] = tym - tymIn;
    }

    void update(int edgeNo, int c) {
        edge e = shared.edges[edgeNo];
        if (chainID[e.frm] == chainID[e.to]) {
            chains[chainID[e.frm]].update(edgeNo, c);
        } else {
            e.cost = c;
        }
    }

    long query(int a, int b) {
        int l = findLCA(a, b);

        Node Bnode = queryA(b, l);
        Node Anode = queryA(a, l);


        return RMQ.combine(Anode, Bnode).num;
    }

    Node queryA(int a, int l) {
        Chain chain = chains[chainID[a]];
        if (chainID[a] == chainID[l]) {
            return chain.query(l, a);
        }
        int frm = chain.head;
        int to = parent[frm];
        int cost = shared.map[frm].get(to).cost;
        Node node = RMQ.combine(chain.queryToHead(a), new Node(cost));
        return RMQ.combine(node, queryA(parent[chain.head], l));
    }

    class Chain {
        int head, tail;
        ArrayList<Integer> vertices;
        int id;
        boolean heavy;
        int[] data;
        RMQ segTree;
        int n;

        Chain(int head, int id) {
            heavy = true;
            this.head = head;
            this.tail = head;
            this.id = id;
            vertices = new ArrayList<Integer>();
            vertices.add(head);
        }

        void add(int v) {
            vertices.add(v);
            tail = v;
        }

        void buildTree() {
            int j = 0;
            n = vertices.size() - 1;
            data = new int[vertices.size() - 1];
            Iterator it = vertices.iterator();
            int a = (Integer) it.next();
            shared.endHash[a] = -1;
            while (it.hasNext()) {
                int b = (Integer) it.next();
                edge edgeAB = shared.map[a].get(b);
                data[j] = edgeAB.cost;
                shared.endHash[b] = j;
                shared.hash[edgeAB.id] = j++;
                a = b;
            }
            if (vertices.size() < 2) return;

            segTree = new RMQ(data);
        }

        void update(int edgeNo, int c) {
            int ind = shared.hash[edgeNo];
            segTree.update(1, 0, n - 1, ind, c);

        }

        Node query(int l, int r) {
            int lh = shared.endHash[l] + 1;
            int rh = shared.endHash[r];
            if (lh > rh) {
                return new Node(0);
            }
            return segTree.query(1, 0, n - 1, lh, rh);
        }

        Node queryToHead(int a) {
            int ah = shared.endHash[a];
            if (vertices.size() < 2) return new Node(0);
            return segTree.query(1, 0, n - 1, 0, ah);
        }

        @Override
        public String toString() {
            String s = "CHAIN ";
            for (int i : vertices) {
                s += i;
                s += ' ';
            }
            return s;
        }
    }
}

class RMQ {
    int data[];
    Node tree[];
    int n;

    RMQ(int[] data) {
        this.data = data;
        n = data.length;
        tree = new Node[4 * n];

        build(1, 0, n - 1);
    }

    static Node combine(Node left, Node ryt) {
        return new Node(Math.max(left.num, ryt.num));
    }

    void build(int v, int tl, int tr) {
        assert (tl <= tr);
        if (tl == tr) {
            tree[v] = new Node(data[tl]);
            return;
        }
        int mid = tl + tr >> 1;
        build(v << 1, tl, mid);
        build(v << 1 | 1, mid + 1, tr);
        tree[v] = combine(tree[v << 1], tree[v << 1 | 1]);
    }

    void update(int v, int tl, int tr, int ind, int val) {
        if (tl == tr) {
            tree[v] = new Node(val);
            return;
        }
        int mid = tl + tr >> 1;
        if (ind > mid) update(v << 1 | 1, mid + 1, tr, ind, val);
        else update(v << 1, tl, mid, ind, val);
        tree[v] = combine(tree[v << 1], tree[v << 1 | 1]);
    }

    Node query(int v, int tl, int tr, int l, int r) {
        assert (l <= r);
        if (tl == l && tr == r) {
            return tree[v];
        }
        int mid = tl + tr >> 1;
        if (r <= mid) return query(v << 1, tl, mid, l, r);
        else if (l > mid) return query(v << 1 | 1, mid + 1, tr, l, r);
        else {
            Node a = query(v << 1, tl, mid, l, mid);
            Node b = query(v << 1 | 1, mid + 1, tr, mid + 1, r);
            return combine(a, b);
        }
    }
}

class Node {
    int num;

    Node(int num) {
        this.num = num;
    }
}

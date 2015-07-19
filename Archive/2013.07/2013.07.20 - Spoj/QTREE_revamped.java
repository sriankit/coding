package Tasks;

import javaUtils.CollectionUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;
import java.util.HashMap;

public class QTREE_revamped {
    int adj[][];
    int vertexCount;
    Edge edges[];
    int[] degree;

    public void solve(int testNumber, final InReader in, final OutputWriter out) {
        Thread t = new Thread(null, new Runnable() {
            @Override
            public void run() {
                solve(in, out);
            }
        }, "oh yeah", 1 << 24);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void solve(InReader in, OutputWriter out) {
        vertexCount = in.readInt();
        if (vertexCount == 0) {
            in.readString();
            return;
        }
        edges = new Edge[vertexCount - 1];
        degree = new int[vertexCount];
        adj = new int[vertexCount][];

        for (int i = 0; i < vertexCount - 1; i++) {
            int u = in.readInt() - 1;
            int v = in.readInt() - 1;
            edges[i] = new Edge(i + 1, u, v, in.readInt());
            degree[u]++;
            degree[v]++;
        }

        for (int i = 0; i < vertexCount; i++) {
            adj[i] = new int[degree[i]];
        }

        int counter[] = new int[vertexCount];

        for (Edge e : edges) {
            adj[e.u][counter[e.u]++] = e.id - 1;
            adj[e.v][counter[e.v]++] = e.id - 1;
        }

        //System.out.println(Arrays.deepToString(adj));

        HeavyLightDecomposition hld = new HeavyLightDecomposition();
        //for(Chain chain : hld.chains) //System.out.println(chain);

        String s = in.readString();
        while (!s.equals("DONE")) {
            if (s.equals("CHANGE")) {
                int edgeID = in.readInt() - 1;
                edges[edgeID].cost = in.readInt();
                hld.updateEdge(edgeID);
            } else {
                int a = in.readInt() - 1;
                int b = in.readInt() - 1;
                out.printLine(hld.query(a, b));
            }
            s = in.readString();
        }

    }

    class HeavyLightDecomposition {
        int[] subtreeSize, depth, pars, chainID, parw;
        int tym = 0;
        ArrayList<Chain> chains = new ArrayList<Chain>();
        int chainCount;

        HeavyLightDecomposition() {
            subtreeSize = new int[vertexCount];
            depth = new int[vertexCount];
            pars = new int[vertexCount];
            chainID = new int[vertexCount];
            parw = new int[vertexCount];

            dfs(0, -1, 0);
            //System.out.println(Arrays.toString(subtreeSize));
            process(0, null, -1);
            //for(Chain chain : chains) //System.out.println(chain);
            for (Chain chain : chains) chain.buildTree();
        }

        void process(int v, Chain chain, int parent) {
            if (chain == null) {
                chain = new Chain(v, chainCount);
                chains.add(chain);
                chainID[v] = chain.no;
                chainCount++;
            }

            for (int edgeID : adj[v]) {
                int child = edges[edgeID].v == v ? edges[edgeID].u : edges[edgeID].v;
                if (child == parent) continue;
                if (subtreeSize[child] > subtreeSize[v] / 2) {       //found heavy child
                    chain.add(edgeID, edges[edgeID].v == v);
                    chainID[child] = chain.no;
                    process(child, chain, v);
                } else {
                    process(child, null, v);
                }
            }
        }

        void dfs(int v, int par, int d) {
            //System.out.println("v = " + v);
            pars[v] = par;
            int tymIn = tym++;
            depth[v] = d;
            for (int i = 0; i < adj[v].length; i++) {
                int edgeID = adj[v][i];
                int end = edges[edgeID].v == v ? edges[edgeID].u : edges[edgeID].v;
                if (end != par) {
                    parw[end] = edges[edgeID].cost;
                    dfs(end, v, d + 1);
                }
            }

            subtreeSize[v] = tym - tymIn;
        }

        int findLCA(int a, int b) {
            while (chainID[a] != chainID[b]) {
                if (depth[chains.get(chainID[a]).start] < depth[chains.get(chainID[b]).start]) {
                    b = pars[chains.get(chainID[b]).start];
                } else a = pars[chains.get(chainID[a]).start];
            }

            if (depth[a] < depth[b])
                return a;
            return b;
        }

        int query(int st, int end) {
            int l = findLCA(st, end);
            //System.out.println("l = " + l);
            //if(l == st || l == end) return chains.get(chainID[st]).rangeMax(st, end);
            int lft = queryL(st, l);
            int ryt = queryR(end, l);
            //System.out.println("lft = " + lft);
            //System.out.println("ryt = " + ryt);
            return Math.max(lft, ryt);
        }

        int queryL(int a, int l) {
            if (chainID[a] == chainID[l]) return chains.get(chainID[a]).rangeMax(l, a);
            return Math.max(Math.max(chains.get(chainID[a]).startingFrom(a), parw[a]), queryL(pars[a], l));
        }

        int queryR(int b, int l) {
            if (chainID[b] == chainID[l]) return chains.get(chainID[b]).rangeMax(l, b);
            return Math.max(Math.max(chains.get(chainID[b]).endingAt(b), parw[b]), queryR(pars[b], l));
        }

        void updateEdge(int edgeID) {
            Edge edge = edges[edgeID];
            int a = edge.u;
            int b = edge.v;
            if (pars[a] == b) parw[a] = edge.cost;
            else parw[b] = edge.cost;
            if (chainID[a] != chainID[b]) return;
            //System.out.println("Going to update " + (edgeID + 1) + "  to  " + edge.cost);
            chains.get(chainID[a]).updateEdge(a, edge.cost);
        }

    }

    class Chain {
        int verticesNumber;
        int edgeCount;
        ArrayList<Integer> vertices;
        int start;
        RMQ tree;
        boolean single = false;
        HashMap<Integer, Integer> startIndex = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> endIndex = new HashMap<Integer, Integer>();
        int end, no;

        ArrayList<Integer> weights;

        Chain(int start, int chainID) {
            no = chainID;
            this.start = start;
            vertices = new ArrayList<Integer>();
            weights = new ArrayList<Integer>();
            vertices.add(start);
            verticesNumber = 1;
            edgeCount = 0;
        }

        void add(int edgeId, boolean st) {
            end = st ? edges[edgeId].u : edges[edgeId].v;
            vertices.add(end);
            weights.add(edges[edgeId].cost);
            verticesNumber++;
            edgeCount++;
        }

        void buildTree() {
            if (edgeCount == 0) {
                single = true;
                return;
            }
            tree = new RMQ(CollectionUtils.toArray(weights));
            for (int i = 0; i < vertices.size(); i++) {
                startIndex.put(vertices.get(i), i);
                endIndex.put(vertices.get(i), i - 1);
            }
        }

        int startingFrom(int v) {
            if (single) return 0;
            if (v == end) return 0;
            return tree.query(1, 0, weights.size() - 1, startIndex.get(v), weights.size() - 1);
        }

        int endingAt(int v) {
            if (single || v == start) return 0;
            return tree.query(1, 0, weights.size() - 1, 0, endIndex.get(v));
        }

        int rangeMax(int u, int v) {
            if (u == v) return 0;
            //System.out.println(Arrays.toString(tree.tree));
            return tree.query(1, 0, weights.size() - 1, startIndex.get(u), endIndex.get(v));
        }

        void updateEdge(int startInd, int val) {
            assert (startInd != end);
            //System.out.println("startIndex.get(startInd) = " + startIndex.get(startInd));
            tree.update(1, 0, weights.size() - 1, startIndex.get(startInd), val);
        }

        public String toString() {
            return "CHAIN #" + no + " [ " + vertices.toString() + " ]";
        }
    }
}

class RMQ {
    int[] data;
    int[] tree;
    int n;

    RMQ(int[] data) {
        this.data = data;
        n = data.length;
        //System.out.println("n = " + n);
        tree = new int[4 * n];
        build(1, 0, n - 1);
    }

    void build(int v, int tl, int tr) {
        if (tl == tr) {
            tree[v] = data[tl];
            return;
        }

        int mid = tl + tr >> 1;
        build(v << 1, tl, mid);
        build(v << 1 | 1, mid + 1, tr);

        tree[v] = Math.max(tree[v << 1], tree[v << 1 | 1]);
    }

    void update(int v, int tl, int tr, int ind, int val) {
        if (tl == tr) {
            tree[v] = val;
            return;
        }
        int mid = tl + tr >> 1;

        if (ind <= mid) update(v << 1, tl, mid, ind, val);
        else update(v << 1 | 1, mid + 1, tr, ind, val);

        tree[v] = Math.max(tree[v << 1], tree[v << 1 | 1]);
    }

    int query(int v, int tl, int tr, int l, int r) {
        if (tl == l && tr == r) {
            return tree[v];
        }
        int mid = tl + tr >> 1;

        if (r <= mid) return query(v << 1, tl, mid, l, r);
        else if (l > mid) return query(v << 1, mid + 1, tr, l, r);
        else return Math.max(query(v << 1, tl, mid, l, mid), query(v << 1 | 1, mid + 1, tr, mid + 1, r));
    }
}

class Edge {
    int u, v, cost, id;

    Edge(int id, int u, int v, int cost) {
        this.cost = cost;
        this.id = id;
        this.u = u;
        this.v = v;
    }
}

package Tasks;


import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;
import java.util.TreeMap;

public class QTREE {

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int t = in.readInt();
        while (t-- > 0) {
            //initialize fields

            int n = in.readInt();

            edgeSet[] adjList = new edgeSet[n];
            for (int i = 0; i < n; i++) {
                adjList[i] = new edgeSet();
            }

            edge edges[] = new edge[n - 1];
            //parse input
            for (int i = 0; i < n - 1; i++) {
                int a = in.readInt();
                int b = in.readInt();
                int c = in.readInt();
                adjList[a].add(b, c, i);
                adjList[b].add(a, c, i);
                edges[i] = new edge(i, a, b, c);
            }
            for (int i = 0; i < n; i++) {
                adjList[i].done();
            }

            HeavyLightDecomposition decomposition = new HeavyLightDecomposition(adjList, edges);

            String s = in.readString();
            while (!s.equals("DONE")) {
                if (s.equals("QUERY")) {
                    int a = in.readInt();
                    int b = in.readInt();
                    //out.printLine(decomposition.query(a, b));
                    //TODO find lca int lca = decomposition.findLCA(a, b);

                } else if (s.equals("UPDATE")) {
                    int i = in.readInt();
                    int ti = in.readInt();
                    edges[i].cost = ti;
                    if (decomposition.chainEdgeID[i] == 0) {         //light edge
                        adjList[edges[i].st].updateCost(edges[i].end, ti);
                        adjList[edges[i].end].updateCost(edges[i].st, ti);
                    } else {
                        int chainID = decomposition.chainEdgeID[i];
                        decomposition.chains.get(chainID).tree.update(i, ti);
                    }
                }
            }
        }
    }
}

class HeavyLightDecomposition {
    private final int[] size;
    edgeSet[] adjList;
    int n;
    ArrayList<Chain> chains;
    int[] chainEdgeID;
    edge[] edges;
    int[] pars;
    private int tym;
    private boolean[] visited;
    private int chainNo;
    private int[] chainID;

    HeavyLightDecomposition(edgeSet[] adjList, edge[] edges) {
        this.adjList = adjList;
        this.edges = edges;
        n = adjList.length;
        chainNo = 0;
        pars = new int[n];
        chains = new ArrayList<Chain>();
        chainID = new int[n];
        chainEdgeID = new int[n - 1];

        tym = 0;
        visited = new boolean[n];
        size = new int[n];

        // ready to decompose
        dfs(0);
        process(0, null, -1);
        for (Chain cha : chains) cha.end();
    }

    //int query(int a, int b) {
    //TODO int l = lca(a, b);
    //return Math.max(queryl(a, l), queryl(b, l));
    //}

    int queryl(int a, int l) {
        if (chainID[a] != 0 && chainID[a] == chainID[l]) {
            int chainN = chainID[a];
            return chains.get(chainN).tree.query(a, l);
        }
        return a;
    }

    private void dfs(int v) {
        int tymIn = tym++;

        visited[v] = true;
        for (int i : adjList[v].keys) {
            if (!visited[i]) dfs(i);
        }
        size[v] = tym - tymIn;  //size of subtree
    }

    private void process(int v, Chain chain, int parent) {
        pars[v] = parent;
        for (int child : adjList[v].keys) {
            if (child == parent) continue;
            if (size[child] > size[v] / 2) {       //found heavy child
                if (chain == null) {
                    chainNo++;
                    chain = new Chain(v, chainNo);
                    chains.add(chain);
                    chainID[v] = chain.no;
                }
                int edgeNo = adjList[v].getNo(child);
                chainEdgeID[edgeNo] = chain.no;
                chain.addEdge(edgeNo);
                chain.add(child);
                chainID[v] = chain.no;
                process(child, chain, v);
            } else {
                process(child, null, v);
            }
        }
    }

    class Chain {
        final int no;
        int st;
        ArrayList<Integer> vertices;
        ArrayList<Integer> edges;
        RMQ tree;

        int countEdges = 0;

        Chain(int st, int no) {
            this.st = st;
            this.no = no;
            vertices = new ArrayList<Integer>();
            vertices.add(st);
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

        void add(int v) {
            vertices.add(v);
        }

        void addEdge(int e) {
            edges.add(e);
            Hasher.hash[e] = countEdges;
            countEdges++;
        }

        void end() {
            // segment tree for each heavy chain
            tree = new RMQ(edges);
        }


    }

    class RMQ {
        int data[];
        int tree[];
        int n;

        RMQ(ArrayList<Integer> data) {
            this.data = new int[data.size()];
            int i = 0;
            for (int k : data) {
                this.data[Hasher.hash[k]] = edges[k].cost;
            }
            n = this.data.length;
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

        void update(int ind, int val) {
            update(1, 0, n - 1, val, Hasher.hash[ind]);
        }

        private void update(int v, int tl, int tr, int val, int ind) {
            if (tl == tr) {
                tree[v] = val;
                return;
            }
            int mid = tl + tr >> 1;
            if (ind <= mid) update(v << 1, tl, mid, ind, val);
            else update(v << 1 | 1, mid + 1, tr, ind, val);
            tree[v] = Math.max(tree[v << 1], tree[v << 1 | 1]);
        }

        int query(int l, int r) {
            int lh = Hasher.hash[l];
            int rh = Hasher.hash[r];
            if (lh > rh) lh = rh + (rh = lh) * 0;
            return query(1, 0, n - 1, lh, rh);
        }

        private int query(int v, int tl, int tr, int l, int r) {
            if (tl == l && tr == r) {
                return tree[v];
            }
            int mid = tl + tr >> 1;
            if (r <= mid) return query(v << 1, tl, mid, l, r);
            else if (l > mid) return query(v << 1 | 1, mid + 1, tr, l, r);
            else return Math.max(query(v << 1, tl, mid, l, mid), query(v << 1 | 1, mid + 1, tr, mid + 1, r));
        }

    }
}

class Hasher {      //hash sharing class for different classes
    static int hash[];
    static int n;

    static void setN(int ne) {
        n = ne;
        hash = new int[n];
    }

}

class edgeSet {
    TreeMap<Integer, int[]> tm = new TreeMap<Integer, int[]>();
    int[] keys;

    void add(int b, int c, int no) {
        tm.put(b, new int[]{c, no});
    }

    void done() {
        keys = new int[tm.size()];
        int j = 0;
        for (int key : tm.keySet())
            keys[j++] = key;
    }

    int getCost(int b) {
        return tm.get(b)[0];
    }

    int getNo(int b) {
        return tm.get(b)[1];
    }

    int[] getBoth(int b) {
        return tm.get(b);
    }

    void updateCost(int b, int c) {
        tm.put(b, new int[]{c, getNo(b)});
    }
}

class edge {
    int no, st, end, cost;

    edge(int cost, int end, int no, int st) {
        this.cost = cost;
        this.end = end;
        this.no = no;
        this.st = st;
    }
}


package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;

public class GSS7 {
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
        int n = in.readInt();
        shared.n = n;
        int arr[] = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = in.readInt();
        }
        shared.arr = arr;
        shared.hash = new int[n];
        int[][] adjList = new int[n][];
        int[] degree = new int[n];
        edge[] edges = new edge[n - 1];
        for (int i = 0; i < n - 1; i++) {
            int a = in.readInt() - 1;
            int b = in.readInt() - 1;
            degree[a]++;
            degree[b]++;
            edges[i] = new edge(a, b);
        }
        for (int i = 0; i < n; i++) {
            adjList[i] = new int[degree[i]];
        }
        int cntr[] = new int[n];
        for (edge edge : edges) {
            int a = edge.st;
            int b = edge.end;
            adjList[a][cntr[a]++] = b;
            adjList[b][cntr[b]++] = a;
        }

        HeavyLightDecomposition decomposition = new HeavyLightDecomposition(adjList);

        int q = in.readInt();
        while (q-- > 0) {
            int ch = in.readInt();
            int l = in.readInt();
            int r = in.readInt();
            try {
                if (ch == 1) { //query
                    //////System.out.println("Querying for " + l + " to  " + r);
                    out.printLine(decomposition.query(l - 1, r - 1));
                } else {
                    decomposition.update(l - 1, r - 1, in.readInt());
                }
            } catch (Exception e) {
                out.printLine(5);
            }
        }

    }
}

class shared {
    static int[] arr;
    static int n;
    static int[] hash;
}

class edge {
    int st, end;

    edge(int end, int st) {
        this.end = end;
        this.st = st;
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
        //////System.out.println(n);
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

    void update(int a, int b, int c) {
        int l = findLCA(a, b);
        updateL(a, l, c);
        updateL(b, l, c);
    }

    void updateL(int a, int l, int c) {
        Chain chain = chains[chainID[a]];
        if (chainID[a] == chainID[l]) {
            chains[chainID[a]].update(a, l, c);
            return;
        }

        chains[chainID[a]].updateToHead(a, c);
        updateL(parent[chain.head], l, c);
    }

    long query(int a, int b) {
        int l = findLCA(a, b);
        ////System.out.println("lca = " + l);
        Node Bnode = Node.flip(queryB(b, l));
        Node Anode = queryA(a, l);
        ////System.out.println(Anode);
        ////System.out.println(Bnode);
        return ContiguousSegTree.combine(Anode, Bnode).maxSum;
    }

    Node queryA(int a, int l) {
        Chain chain = chains[chainID[a]];
        if (chainID[a] == chainID[l]) {
            return chain.query(a, l, 0);
        }
        return ContiguousSegTree.combine(chain.queryToHead(a), queryA(parent[chain.head], l));
    }

    Node queryB(int a, int l) {
        Chain chain = chains[chainID[a]];
        if (chainID[a] == chainID[l]) {
            return chain.query(a, l, -1);
        }
        Node node1 = chain.queryToHead(a);
        Node node2 = queryB(parent[chain.head], l);
        ////System.out.println("queryToHead " + (a+1) +"  " + node1);
        ////System.out.println("queryB " + (parent[chain.head] + 1) + "  " + node2);
        return ContiguousSegTree.combine(node1, node2);
    }


    class Chain {
        int head, tail;
        ArrayList<Integer> vertices;
        int id;
        boolean heavy;
        long[] data;
        ContiguousSegTree segTree;
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
            n = vertices.size();
            data = new long[vertices.size()];
            for (int vertex : vertices) {
                data[j] = shared.arr[vertex];
                shared.hash[vertex] = j++;
            }
            segTree = new ContiguousSegTree(data);
        }

        void update(int l, int r, long c) {
            {
                int lh = shared.hash[l];
                int rh = shared.hash[r];
                if (lh > rh) rh = lh + (lh = rh) * 0;    //swap
                segTree.update(1, 0, n - 1, lh, rh, c);
            }
        }

        void updateToHead(int a, long c) {
            int ah = shared.hash[a];
            segTree.update(1, 0, n - 1, 0, ah, c);
        }

        Node query(int l, int r, int correction) {
            int lh = shared.hash[l];
            int rh = shared.hash[r];
            boolean swapped = false;
            if (lh > rh) {
                rh = lh + (lh = rh) * 0;    //swap
                swapped = true;
            }
            if (correction == -1) {
                if (lh == rh) return new Node(0);
                else lh++;
            }
            ////System.out.println("querying for " + lh + "  " + rh);
            Node node = segTree.query(1, 0, n - 1, lh, rh);
            if (swapped) return Node.flip(node);
            return node;
        }

        Node queryToHead(int a) {
            int ah = shared.hash[a];
            Node node = segTree.query(1, 0, n - 1, 0, ah);
            return Node.flip(node);
        }
    }
}


class ContiguousSegTree {
    long data[];
    int n;
    Node tree[];
    long flagged[];

    ContiguousSegTree(long[] data) {
        this.data = data;
        n = data.length;
        flagged = new long[4 * n];
        tree = new Node[4 * n];
        this.build(1, 0, n - 1);
    }

    static Node combine(Node left, Node right) {
        Node ret = new Node();
        ret.maxLeftSum = Math.max(left.maxLeftSum, left.sum + right.maxLeftSum);
        ret.minLeftSum = Math.min(left.minLeftSum, left.sum + right.minLeftSum);
        ret.maxRightSum = Math.max(right.maxRightSum, right.sum + left.maxRightSum);
        ret.minRightSum = Math.min(right.minRightSum, right.sum + left.minRightSum);
        ret.sum = left.sum + right.sum;
        ret.maxSum = Math.max(Math.max(right.maxSum, left.maxSum), left.maxRightSum + right.maxLeftSum);
        ret.minSum = Math.min(Math.min(right.minSum, left.minSum), left.minRightSum + right.minLeftSum);
        ret.size = left.size + right.size;
        return ret;
    }

    void build(int v, int tl, int tr) {
        if (tl == tr) {
            tree[v] = new Node(data[tl]);
            tree[v].size = 1;
            return;
        }
        int mid = tl + tr >> 1;
        build(v << 1, tl, mid);
        build(v << 1 | 1, mid + 1, tr);
        tree[v] = combine(tree[v << 1], tree[v << 1 | 1]);
        //tree[v].size = tr - tl + 1;
    }

    void refresh(int v, long c) {
        Node node = tree[v];
        long num = c * node.size;
        ////System.out.println("refreshin node " + v + " to " + num + " for " + "c = " + c + " for size = " + node.size);

        node.sum = num;

        if (num > 0) {
            node.maxSum = node.maxLeftSum = node.maxRightSum = num;
            node.minSum = node.minLeftSum = node.minRightSum = 0;
        } else {
            node.maxSum = node.maxRightSum = node.maxLeftSum = 0;
            node.minSum = node.minRightSum = node.minLeftSum = num;
        }

        flagged[v] = c;
    }

    void update(int v, int tl, int tr, int l, int r, long c) {
        assert (l <= r);
        if (tl == l && tr == r) {
            refresh(v, c);
            return;
        }
        push(v);
        int mid = tl + tr >> 1;
        if (r <= mid) update(v << 1, tl, mid, l, r, c);
        else if (l > mid) update(v << 1 | 1, mid + 1, tr, l, r, c);
        else {
            update(v << 1, tl, mid, l, mid, c);
            update(v << 1 | 1, mid + 1, tr, mid + 1, r, c);
        }
        tree[v] = combine(tree[v << 1], tree[v << 1 | 1]);
    }

    Node query(int v, int tl, int tr, int l, int r) {
        assert (l <= r);
        if (tl == l && tr == r) {
            return tree[v];
        }
        push(v);
        int mid = tl + tr >> 1;
        if (r <= mid) return query(v << 1, tl, mid, l, r);
        else if (l > mid) return query(v << 1 | 1, mid + 1, tr, l, r);
        else {
            Node a = query(v << 1, tl, mid, l, mid);
            ////System.out.println("query " + tl + "  " + mid + "  " + l + "  " + mid + "  " + a);
            Node b = query(v << 1 | 1, mid + 1, tr, mid + 1, r);
            ////System.out.println("query " + mid + 1 + "  " + tr + "  " + mid + 1 + "  " + r + "  " + b);
            return combine(a, b);
        }
    }

    void push(int v) {
        if (flagged[v] != 0) {
            if (v << 1 >= 4 * n) return;           //we're on a leaf
            refresh(v << 1, flagged[v]);
            refresh(v << 1 | 1, flagged[v]);
            flagged[v] = 0;
        }
    }
}

class Node {
    long maxSum, maxLeftSum, maxRightSum, sum;
    long minSum, minLeftSum, minRightSum;
    int size;

    Node(long num) {
        sum = num;
        if (num > 0) {
            maxSum = maxLeftSum = maxRightSum = num;
            minSum = minLeftSum = minRightSum = 0;
        } else {
            maxSum = maxRightSum = maxLeftSum = 0;
            minSum = minRightSum = minLeftSum = num;
        }
    }

    Node() {
    }

    static Node flip(Node node) {
        Node retNode = new Node();
        retNode.maxRightSum = node.maxLeftSum;
        retNode.maxLeftSum = node.maxRightSum;
        retNode.minLeftSum = node.minRightSum;
        retNode.minRightSum = node.minLeftSum;
        retNode.sum = node.sum;
        retNode.maxSum = node.maxSum;
        retNode.minSum = node.minSum;
        retNode.size = node.size;
        return retNode;
    }

    @Override
    public String toString() {
        return "[" + sum + ", " + maxSum + ", " + maxLeftSum + ", " + maxRightSum + ", " + minSum + ", " + minLeftSum + ", " + minRightSum + ", " + "]";
    }
}
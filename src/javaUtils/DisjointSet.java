package javaUtils;

public class DisjointSet {
    int[] parent, rank;
    int sz;

    public DisjointSet(int size) {
        sz = size;
        parent = new int[size];
        rank = new int[size];
    }

    public void makeSet(int x) {
        parent[x] = x;
        rank[x] = 0;
    }

    public int find(int x) {
        if (parent[x] != x) parent[x] = find(parent[x]);
        return parent[x];
    }

    public void union(int x, int y) {
        int xParent = find(x);
        int yParent = find(y);
        if (xParent == yParent) return;
        if (rank[xParent] > rank[yParent]) parent[yParent] = xParent;
        else {
            parent[xParent] = parent[yParent];
            if (rank[xParent] == rank[yParent]) rank[yParent]++;
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        super.clone();
        DisjointSet ds = new DisjointSet(this.sz);
        ds.parent = this.parent.clone();
        ds.rank = this.rank.clone();
        return ds;
    }
}

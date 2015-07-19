package javaUtils;

public class Tree {
    private int nodeCount;
    private node[] nodes;
    private int[] degree;
    private int edges[][];
    private int edgesAdded;

    public Tree(int nodeCount) {
        this.nodeCount = nodeCount;
        nodes = new node[nodeCount];
        degree = new int[nodeCount];
        edges = new int[nodeCount - 1][2];
    }

    public void bufferedAddEdge(int u, int v) {
        degree[u]++;
        degree[v]++;
        edges[edgesAdded][0] = u;
        edges[edgesAdded][1] = v;
        edgesAdded++;
        if (edgesAdded == nodeCount - 1) this.manage();
    }

    private void manage() {
        int[][] adjList = new int[nodeCount][];
        for (int i = 0; i < nodeCount; i++) {
            adjList[i] = new int[degree[i]];
        }
        int[] temp = new int[nodeCount];
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            adjList[u][temp[u]++] = v;
            adjList[v][temp[v]++] = u;
        }
        this.resolve(-1, 0, adjList);
    }

    private void resolve(int parent, int root, int[][] adjList) {
        nodes[root] = new node();
        nodes[root].parent = parent;
        if (parent == -1) nodes[root].children = new int[degree[root]];
        else nodes[root].children = new int[degree[root] - 1];
        nodes[root].childrenCount = nodes[root].children.length;
        int index = 0;
        boolean flag = true;
        for (int j : adjList[root]) {
            if (j != parent) {
                nodes[root].children[index++] = j;
                flag = false;
                resolve(root, j, adjList);
            }
        }
        nodes[root].isLeaf = flag;
    }

    public int[] children(int root) {
        return nodes[root].children;
    }

}

class node {
    public long weight;
    public int parent;
    public int[] children;
    public boolean isLeaf;
    public int childrenCount;

    public node() {
        weight = 0;
        parent = 0;
        children = null;
        isLeaf = false;
        childrenCount = 0;
    }
}

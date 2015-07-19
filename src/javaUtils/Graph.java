package javaUtils;

import java.util.HashMap;
import java.util.Map;

interface Edge<V> {
    public V getSource();

    public long getWeight();
}

class Graph<V> {
    public V[] vertices;
    public int[] firstOutbound;
    public int[] firstInbound;
    public Edge<V>[] edges;
    public int[] nextInbound;
    public int[] nextOutbound;
    public int[] from;
    public int[] to;
    public long[] weight;
    public long[] capacity;
    public int[] reverseEdge;
    public boolean[] removed;
    protected int vertexCount;
    protected int edgeCount;
    protected Map<V, Integer> map = new HashMap<V, Integer>();

    public Graph() {
        this(10);
    }

    public Graph(int vertexCapacity) {
        this(vertexCapacity, vertexCapacity);
    }

    public Graph(int vertexCapacity, int edgeCapacity) {
        //noinspection unchecked
        vertices = (V[]) new Object[vertexCapacity];
        firstOutbound = new int[vertexCapacity];
        firstInbound = new int[vertexCapacity];

        //noinspection unchecked
        edges = new Edge[edgeCapacity];
        from = new int[edgeCapacity];
        to = new int[edgeCapacity];
        nextInbound = new int[edgeCapacity];
        nextOutbound = new int[edgeCapacity];
        weight = new long[edgeCapacity];
        capacity = new long[edgeCapacity];
        reverseEdge = new int[edgeCapacity];
        removed = new boolean[edgeCapacity];
    }

    public int addEdge(int fromID, int toID, long weight, long capacity, int reverseEdge) {
        ensureEdgeCapacity(edgeCount + 1);
        if (firstOutbound[fromID] != -1)
            nextOutbound[edgeCount] = firstOutbound[fromID];
        else
            nextOutbound[edgeCount] = -1;
        firstOutbound[fromID] = edgeCount;
        if (firstInbound[toID] != -1)
            nextInbound[edgeCount] = firstInbound[toID];
        else
            nextInbound[edgeCount] = -1;
        firstInbound[toID] = edgeCount;
        this.from[edgeCount] = fromID;
        this.to[edgeCount] = toID;
        this.weight[edgeCount] = weight;
        this.capacity[edgeCount] = capacity;
        this.reverseEdge[edgeCount] = reverseEdge;
        edges[edgeCount] = createEdge(edgeCount);
        return edgeCount++;
    }

    protected GraphEdge createEdge(int id) {
        return new GraphEdge(id);
    }

    public Edge<V> addFlowWeightedEdge(V from, V to, long weight, long capacity) {
        int fromID = resolveOrAdd(from);
        int toID = resolveOrAdd(to);
        if (capacity == 0) {
            int result = addEdge(fromID, toID, weight, 0, -1);
            return edges[result];
        } else {
            int lastEdgeCount = edgeCount;
            addEdge(toID, fromID, -weight, 0, lastEdgeCount + entriesPerEdge());
            int result = addEdge(fromID, toID, weight, capacity, lastEdgeCount);
            return edges[result];
        }
    }

    protected int entriesPerEdge() {
        return 1;
    }

    public Edge<V> addWeightedEdge(V from, V to, long weight) {
        return addFlowWeightedEdge(from, to, weight, 0);
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public int resolve(V vertex) {
        if (map.containsKey(vertex))
            return map.get(vertex);
        throw new IllegalArgumentException();
    }

    public V getVertex(int id) {
        return vertices[id];
    }

    public Edge<V> getEdge(int id) {
        return edges[id];
    }

    private int resolveOrAdd(V vertex) {
        if (map.containsKey(vertex))
            return map.get(vertex);
        ensureVertexCapacity(vertexCount + 1);
        map.put(vertex, vertexCount);
        vertices[vertexCount] = vertex;
        firstInbound[vertexCount] = firstOutbound[vertexCount] = -1;
        return vertexCount++;
    }

    protected void ensureEdgeCapacity(int size) {
        if (from.length < size) {
            int newSize = Math.max(size, 2 * from.length);
            edges = resize(edges, newSize);
            from = resize(from, newSize);
            to = resize(to, newSize);
            nextInbound = resize(nextInbound, newSize);
            nextOutbound = resize(nextOutbound, newSize);
            weight = resize(weight, newSize);
            capacity = resize(capacity, newSize);
            reverseEdge = resize(reverseEdge, newSize);
            removed = resize(removed, newSize);
        }
    }

    protected void ensureVertexCapacity(int size) {
        if (firstInbound.length < size) {
            int newSize = Math.max(size, 2 * firstInbound.length);
            vertices = resize(vertices, newSize);
            firstInbound = resize(firstInbound, newSize);
            firstOutbound = resize(firstOutbound, newSize);
        }
    }

    protected int[] resize(int[] array, int size) {
        int[] newArray = new int[size];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }

    protected boolean[] resize(boolean[] array, int size) {
        boolean[] newArray = new boolean[size];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }

    private long[] resize(long[] array, int size) {
        long[] newArray = new long[size];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }

    private Edge<V>[] resize(Edge<V>[] array, int size) {
        @SuppressWarnings("unchecked")
        Edge<V>[] newArray = new Edge[size];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }

    private V[] resize(V[] array, int size) {
        @SuppressWarnings("unchecked")
        V[] newArray = (V[]) new Object[size];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }

    public boolean isSparse() {
        return vertexCount == 0 || edgeCount * 20 / vertexCount <= vertexCount;
    }

    protected class GraphEdge implements Edge<V> {
        protected int id;

        protected GraphEdge(int id) {
            this.id = id;
        }

        public V getSource() {
            return vertices[from[id]];
        }

        public long getWeight() {
            return weight[id];
        }

    }

}
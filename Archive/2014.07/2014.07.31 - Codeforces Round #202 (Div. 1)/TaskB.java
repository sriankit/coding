package Tasks;

import com.sun.javafx.geom.transform.BaseTransform;
import javaUtils.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TaskB {

    /**
     * The idea for 
     */

    ArrayList<Integer> adj[];
    int[] weights;
    ArrayList<NodeElement> problem;
    void go(int v, int prod, int parent) {
        int childCount = adj[v].size() - 1;
        if(childCount == 0) {
            problem.add(new NodeElement(weights[v], prod));
            return;
        }
        adj[v].stream().filter(child -> child != parent).forEach(child -> go(child, prod * childCount, v));
    }
    public void solve(int testNumber, InReader in, OutputWriter out) {
        problem = new ArrayList<>();
        int n = in.readInt();
        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        weights = IOUtils.readIntArray(in, n);
        for (int i = 0; i < n; i++) {
            int a = in.readInt();
            int b = in.readInt();
            --a;
            --b;
            adj[a].add(b);
            adj[b].add(a);
        }
        go(0, 1, -1);
        //TODO solve problem by treating same steps seperately and solving the resulting subproblem by considering lcm :)
        Collections.sort(problem, (o1, o2) -> o1.step - o2.step);
    }
    class NodeElement {
        int number, step;

        NodeElement(int number, int step) {
            this.number = number;
            this.step = step;
        }
    }
}

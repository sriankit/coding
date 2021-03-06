package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class TaskScheduling {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        Task[] tasks = new Task[n];
        for (int i = 0; i < n; i++) {
            tasks[i] = new Task(in.readInt(), in.readInt(), i);
        }
        Task[] sorted = tasks.clone();
        Arrays.sort(sorted);
        for (int i = 0; i < n; i++) {
            tasks[sorted[i].index].index = i;
        }
        //for(Task task : tasks) System.out.println(task);
        SegTree tree = new SegTree(sorted);
        //System.out.println(tree.query());

        for (Task task : tasks) {
            tree.modify(1, 0, n - 1, task.index, task.time);
            //System.out.println(tree.query());
            tree.maintain(task.index, task.time);
            int x = Math.max(tree.query(), 0);
            out.printLine(x);
        }
    }
}

class SegTree {
    Node[] tree;
    Task[] data;
    int flagged[];
    int n;

    SegTree(Task[] data) {
        this.data = data;
        n = data.length;
        tree = new Node[4 * n];
        flagged = new int[4 * n];
        build(1, 0, n - 1);
    }

    void build(int v, int tl, int tr) {
        if (tl == tr) {
            tree[v] = new Node(-data[tl].deadline, false);
            return;
        }
        int mid = tl + tr >> 1;
        build(v << 1, tl, mid);
        build(v << 1 | 1, mid + 1, tr);
        tree[v] = new Node(Math.max(tree[v << 1].getOverShoot(), tree[v << 1 | 1].getOverShoot()), false);
    }

    void refresh(int v, int val) {
        tree[v].upd(val);
        flagged[v] += val;
    }

    int query() {
        return tree[1].getOverShoot();
    }

    void modify(int v, int tl, int tr, int ind, int val) {
        if (tl == tr) {
            tree[v].upd(val);
            tree[v].done = true;
            return;
        }
        push(v);
        int mid = tl + tr >> 1;
        if (ind <= mid) modify(v << 1, tl, mid, ind, val);
        else modify(v << 1 | 1, mid + 1, tr, ind, val);
        tree[v].setOverShoot(Math.max(tree[v << 1].getOverShoot(), tree[v << 1 | 1].getOverShoot()));
        tree[v].done = true;
        //System.out.println("tree[" + v + "] = " + tree[v].getOverShoot());
    }

    void maintain(int ind, int val) {
        update(1, 0, n - 1, ind + 1, n - 1, val);
    }

    void update(int v, int tl, int tr, int l, int r, int c) {
        if (l > r) return;
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
        tree[v].setOverShoot(Math.max(tree[v << 1].getOverShoot(), tree[v << 1 | 1].getOverShoot()));
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
    boolean done;
    private int overShoot;

    Node(int overShoot, boolean done) {
        this.setOverShoot(overShoot);
        this.done = done;
    }

    public int getOverShoot() {
        if (!done) return 0;
        return overShoot;
    }

    public void setOverShoot(int overShoot) {
        this.overShoot = overShoot;
    }

    public void upd(int val) {
        overShoot += val;
    }
}

class Task implements Comparable<Task> {
    int deadline, time, index;

    Task(int deadline, int time, int index) {
        this.deadline = deadline;
        this.time = time;
        this.index = index;
    }

    @Override
    public String toString() {
        return "[ " + index + " " + deadline + "  " + time + " ]";
    }

    @Override
    public int compareTo(Task o) {
        if (this.deadline == o.deadline) {
            return this.time - o.time;
        } else return this.deadline - o.deadline;
    }
}

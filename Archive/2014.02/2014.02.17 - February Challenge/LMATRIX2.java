package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.util.ArrayList;

public class LMATRIX2 {
    int p[][];
    int arr[][];

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n, m;
        n = in.readInt();
        m = in.readInt();
        p = new int[n + 2][m + 2];
        arr = new int[n + 2][m + 2];

        ArrayList<cell> cells = new ArrayList<cell>();
        ArrayList<cell> ans = new ArrayList<cell>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                p[i][j] = in.readInt();
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                arr[i][j] = in.readInt();
            }
        }
        int a[][] = arr.clone();
        int i, j;
        for (i = 0; i < n; i++) {
            for (j = 0; j < m; j++) {
                if (a[i][j] != 0) {
                    int i1 = i + 1, j1, i11, j11;
                    while (a[i1][j] != 0 && i1 < n)
                        i1++;

                    for (j1 = j; j1 < m; j1++) {
                        for (i11 = i; i11 < i1; i11++)
                            if (a[i11][j1] == 0) break;
                        if (i11 == i) break;
                        if (i11 < i1) i1 = i11;
                    }
                    cell c = new cell(p[i][j] - (a[i][j] % p[i][j]), i, i1 - 1, j, j1 - 1);
                    cells.add(c);
                    for (i11 = i; i11 < i1; i11++)
                        for (j11 = j; j11 < j1; j11++)
                            a[i11][j11] = (a[i11][j11] + c.k) % p[i11][j11];
                }
            }
        }
        ans = (ArrayList<cell>) cells.clone();
        a = arr.clone();
        for (i = n - 1; i >= 0; i--) {
            for (j = 0; j < m; j++) {
                if (a[i][j] != 0) {
                    int i1 = i + 1, j1, i11, j11;
                    while (a[i1][j] != 0 && i1 < n)
                        i1++;

                    for (j1 = j; j1 < m; j1++) {
                        for (i11 = i; i11 < i1; i11++)
                            if (a[i11][j1] == 0) break;
                        if (i11 == i) break;
                        if (i11 < i1) i1 = i11;
                    }
                    cell c = new cell(p[i][j] - (a[i][j] % p[i][j]), i, i1 - 1, j, j1 - 1);
                    cells.add(c);
                    for (i11 = i; i11 < i1; i11++)
                        for (j11 = j; j11 < j1; j11++)
                            a[i11][j11] = (a[i11][j11] + c.k) % p[i11][j11];
                }
            }
        }
        if (cells.size() < ans.size()) ans = (ArrayList<cell>) cells.clone();

        a = arr.clone();
        for (i = n - 1; i >= 0; i--) {
            for (j = m - 1; j >= 0; j--) {
                if (a[i][j] != 0) {
                    int i1 = i + 1, j1, i11, j11;
                    while (a[i1][j] != 0 && i1 < n)
                        i1++;

                    for (j1 = j; j1 < m; j1++) {
                        for (i11 = i; i11 < i1; i11++)
                            if (a[i11][j1] == 0) break;
                        if (i11 == i) break;
                        if (i11 < i1) i1 = i11;
                    }
                    cell c = new cell(p[i][j] - (a[i][j] % p[i][j]), i, i1 - 1, j, j1 - 1);
                    cells.add(c);
                    for (i11 = i; i11 < i1; i11++)
                        for (j11 = j; j11 < j1; j11++)
                            a[i11][j11] = (a[i11][j11] + c.k) % p[i11][j11];
                }
            }
        }
        if (cells.size() < ans.size()) ans = (ArrayList<cell>) cells.clone();

        a = arr.clone();
        for (i = 0; i < n; i++) {
            for (j = m - 1; j >= 0; j--) {
                if (a[i][j] != 0) {
                    int i1 = i + 1, j1, i11, j11;
                    while (a[i1][j] != 0 && i1 < n)
                        i1++;

                    for (j1 = j; j1 < m; j1++) {
                        for (i11 = i; i11 < i1; i11++)
                            if (a[i11][j1] == 0) break;
                        if (i11 == i) break;
                        if (i11 < i1) i1 = i11;
                    }
                    cell c = new cell(p[i][j] - (a[i][j] % p[i][j]), i, i1 - 1, j, j1 - 1);
                    cells.add(c);
                    for (i11 = i; i11 < i1; i11++)
                        for (j11 = j; j11 < j1; j11++)
                            a[i11][j11] = (a[i11][j11] + c.k) % p[i11][j11];
                }
            }
        }
        if (cells.size() < ans.size()) ans = (ArrayList<cell>) cells.clone();


        out.printLine(ans.size());
        for (cell ce : ans) {
            out.printLine(ce.toString());
        }
    }

    class cell {
        int x1, x2, y1, y2, k;

        cell(int k, int x1, int x2, int y1, int y2) {
            this.k = k;
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
        }

        @Override
        public String toString() {
            return String.format("%d %d %d %d %d", x1 + 1, y1 + 1, x2 + 1, y2 + 1, k);
        }
    }
}

package Tasks;

import javaUtils.ArrayUtils;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.awt.*;

public class Quadrilateral {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        testNumber = in.readInt();
        while (testNumber-- > 0) {
            Point[] points = new Point[4];
            for (int i = 0; i < 4; i++) {
                int x = in.readInt();
                int y = in.readInt();
                points[i] = new Point(x, y);
            }
            int ans = -2;
            /*Arrays.sort(points, new Comparator<Point>() {
                @Override
                public int compare(Point o1, Point o2) {
                    return o1.x - o2.x;
                }
            });*/
            for (int i = 0; i < 4; i++) {
                for (int j = i + 1; j < 4; j++) {
                    if (points[i].equals(points[j])) ans = -1;
                }
            }
            if (ans == -1) {
                out.printLine("NONE");
                continue;
            }
            double d[] = new double[3];
            d[0] = points[0].distanceSq(points[1]);
            d[1] = points[0].distanceSq(points[2]);
            d[2] = points[0].distanceSq(points[3]);
            if (d[0] != d[1] && d[1] != d[2] && d[0] != d[2]) {
                int i = ArrayUtils.maxPosition(d);
                double diag = 0;
                switch (i) {
                    case 0:
                        diag = points[3].distanceSq(points[2]);
                        double d12 = points[1].distanceSq(points[2]);
                        double d03 = points[0].distanceSq(points[3]);
                        double d02 = points[0].distanceSq(points[2]);
                        double d13 = points[1].distanceSq(points[3]);
                        if (d12 == d03 && d02 == d13) {
                            if (diag == d[i]) {
                                out.printLine("RECTANGLE");
                            } else out.printLine("PARALLELOGRAM");
                        } else out.printLine("QUADRILATERAL");

                        break;
                    case 1:
                        diag = points[3].distanceSq(points[1]);
                        d12 = points[1].distanceSq(points[0]);
                        d03 = points[2].distanceSq(points[3]);

                        d02 = points[0].distanceSq(points[3]);
                        d13 = points[1].distanceSq(points[2]);
                        if (d12 == d03 && d02 == d13) {
                            if (diag == d[i]) {
                                out.printLine("RECTANGLE");
                            } else out.printLine("PARALLELOGRAM");
                        } else out.printLine("QUADRILATERAL");

                        break;
                    case 2:
                        diag = points[1].distanceSq(points[2]);
                        d12 = points[1].distanceSq(points[0]);
                        d03 = points[2].distanceSq(points[3]);

                        d02 = points[0].distanceSq(points[2]);
                        d13 = points[1].distanceSq(points[3]);
                        if (d12 == d03 && d02 == d13) {
                            if (diag == d[i]) {
                                out.printLine("RECTANGLE");
                            } else out.printLine("PARALLELOGRAM");
                        } else out.printLine("QUADRILATERAL");

                        break;

                }

            } else if (d[0] == d[1] && d[1] == d[2]) out.printLine("QUADRILATERAL");

            else {

                if (d[0] == d[1]) {
                    double diag = points[1].distanceSq(points[2]);
                    if (diag == d[2]) out.printLine("SQUARE");
                    else {
                        double d1 = points[0].distanceSq(points[1]);
                        double d2 = points[1].distanceSq(points[3]);
                        double d3 = points[3].distanceSq(points[2]);
                        double d4 = points[2].distanceSq(points[0]);
                        if (d1 == d2 && d2 == d3 && d3 == d4 && d4 == d1) out.printLine("RHOMBUS");
                        else out.printLine("QUADRILATERAL");
                    }
                } else if (d[1] == d[2]) {
                    double diag = points[1].distanceSq(points[0]);
                    if (diag == d[0]) out.printLine("SQUARE");
                    else {
                        double d1 = points[0].distanceSq(points[2]);
                        double d2 = points[2].distanceSq(points[1]);
                        double d3 = points[1].distanceSq(points[3]);
                        double d4 = points[3].distanceSq(points[0]);
                        if (d1 == d2 && d2 == d3 && d3 == d4 && d4 == d1) out.printLine("RHOMBUS");
                        else out.printLine("QUADRILATERAL");
                    }
                } else {
                    double diag = points[1].distanceSq(points[3]);
                    if (diag == d[1]) out.printLine("SQUARE");
                    else {
                        double d1 = points[0].distanceSq(points[1]);
                        double d2 = points[1].distanceSq(points[2]);
                        double d3 = points[2].distanceSq(points[3]);
                        double d4 = points[3].distanceSq(points[0]);
                        if (d1 == d2 && d2 == d3 && d3 == d4 && d4 == d1) out.printLine("RHOMBUS");
                        else out.printLine("QUADRILATERAL");
                    }
                }
            }
        }
    }
}

package Tasks;

import javaUtils.GaussianElimination;
import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.awt.geom.Point2D;

//eq of circle x^2 + y^2 + 2gx + 2fy + c = 0

public class CircleOfDeath {

    private double eval(Point2D p, double g, double f, double c) {
        double px = p.getX();
        double py = p.getY();
        return px * px + py * py + 2 * g * px + 2 * f * py + c;
    }

    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n = in.readInt();
        Point2D[] points = new Point2D[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point2D.Double(x, y);
        }
        double prob = 0;
        double cnt = 0.0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {

                    double[][] A = new double[3][3];
                    double[] B = new double[3];

                    A[0][0] = 2 * points[i].getX();
                    A[0][1] = 2 * points[i].getY();
                    B[0] = -(points[i].getX() * points[i].getX() + points[i].getY() * points[i].getY());
                    A[1][0] = 2 * points[j].getX();
                    A[1][1] = 2 * points[j].getY();
                    B[1] = -(points[j].getX() * points[j].getX() + points[j].getY() * points[j].getY());
                    A[2][0] = 2 * points[k].getX();
                    A[2][1] = 2 * points[k].getY();
                    B[2] = -(points[k].getX() * points[k].getX() + points[k].getY() * points[k].getY());

                    A[0][2] = A[1][2] = A[2][2] = 1;

                    try {
                        double[] x = GaussianElimination.lsolve(A, B);
                        double g = x[0];
                        double f = x[1];
                        double c = x[2];
                        /*System.out.println("d " + eval(points[i], g, f, c));
                        System.out.println("d " + eval(points[j], g, f, c));
                        System.out.println("d " + eval(points[k], g, f, c));*/
                        for (int p = 0; p < n; p++) {
                            if (p != i && p != j && p != k) {
                                if (eval(points[p], g, f, c) <= 0 || eval(points[p], g, f, c) <= 1e-9) cnt++;
                            }
                        }
                    } catch (Exception e) {
                    }
                    //prob += cnt/(double)(n-3);
                }
            }
        }
        int tot = n * (n - 1) * (n - 2) * (n - 3);
        tot /= 6;
        out.printLine(cnt / tot);
    }
}

package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class SerejaAndSnake {

    LinkedList<Point> queue;
    int n, m;

    public void solve(int testNumber, InReader in, OutputWriter out) {
        n = in.readInt();
        m = in.readInt();
        int q = n * m - 1;
        int hx = 0, hy = 0;
        ArrayList<Integer> moves = new ArrayList<Integer>();
        int len = 1;
        queue = new LinkedList<Point>();
        queue.offer(new Point(0, 0));

        while (q-- > 0) {
            int destx = in.readInt();
            int desty = in.readInt();
            boolean frst = true;

            if (n % 2 == 0)
                while (true) {
                    if (hx == destx && hy == desty) {
                        len++;
                        assert (!frst);
                        queue.offer(new Point(hx, hy));
                        break;
                    } else if (hx == n - 1 && hy == 0) {
                        //out.printLine(1);
                        if (!frst) {
                            queue.pollLast();
                            queue.offer(new Point(hx, hy));
                        }
                        moves.add(1);
                        hx = 0;
                        frst = false;
                    } else if (hy == m - 1 && hx % 2 == 0) {
                        //out.printLine(1);
                        if (!frst) {
                            queue.pollLast();
                            queue.offer(new Point(hx, hy));
                        }
                        moves.add(1);
                        hx++;
                        frst = false;
                    } else if (hy == 0 && hx % 2 == 1) {
                        //out.printLine(1);
                        if (!frst) {
                            queue.pollLast();
                            queue.offer(new Point(hx, hy));
                        }
                        moves.add(1);
                        hx++;
                        frst = false;
                    } else {
                        if (hx % 2 == 0) {
                            //out.printLine(3);
                            if (!frst) {
                                queue.pollLast();
                                queue.offer(new Point(hx, hy));
                            }
                            moves.add(3);
                            hy++;
                            frst = false;
                        } else {
                            //out.printLine(2);
                            if (!frst) {
                                queue.pollLast();
                                queue.offer(new Point(hx, hy));
                            }
                            moves.add(2);
                            hy--;
                            frst = false;
                        }
                    }
                }
            else {
                if (destx > hx && mapAndTest(hx, hy, destx, desty)) {
                    //optimization
                    while (hx < destx - 1) {
                        if (!frst) {
                            queue.pollLast();
                            queue.offer(new Point(hx, hy));
                        }
                        moves.add(3);
                        hx++;
                        frst = false;
                    }

                    if (hy < desty) {
                        if (hx % 2 == 1) {
                            if (!frst) {
                                queue.pollLast();
                                queue.offer(new Point(hx, hy));
                            }
                            moves.add(3);
                            hx++;
                            frst = false;
                        }
                        while (hy < desty) {
                            if (!frst) {
                                queue.pollLast();
                                queue.offer(new Point(hx, hy));
                            }
                            moves.add(1);
                            hy++;
                            frst = false;
                        }
                        if (hx < destx) {
                            if (!frst) {
                                queue.pollLast();
                                queue.offer(new Point(hx, hy));
                            }
                            moves.add(3);
                            hx++;
                            frst = false;
                        }
                    } else {
                        if (hx % 2 == 0) {
                            if (!frst) {
                                queue.pollLast();
                                queue.offer(new Point(hx, hy));
                            }
                            moves.add(3);
                            hx++;
                            frst = false;
                        }
                        while (hy > desty) {
                            if (!frst) {
                                queue.pollLast();
                                queue.offer(new Point(hx, hy));
                            }
                            moves.add(0);
                            hy--;
                            frst = false;
                        }
                        if (hx < destx) {
                            if (!frst) {
                                queue.pollLast();
                                queue.offer(new Point(hx, hy));
                            }
                            moves.add(3);
                            hx++;
                            frst = false;
                        }
                    }

                }
                while (true) {
                    if (hx == destx && hy == desty) {
                        len++;
                        assert (!frst);
                        queue.offer(new Point(hx, hy));
                        break;
                    } else if (hy == m - 1 && hx == 0) {
                        //out.printLine(3);
                        if (!frst) {
                            queue.pollLast();
                            queue.offer(new Point(hx, hy));
                        }
                        moves.add(3);
                        hy = 0;
                        frst = false;
                    } else if (hx == n - 1 && hy % 2 == 0) {
                        //out.printLine(3);
                        if (!frst) {
                            queue.pollLast();
                            queue.offer(new Point(hx, hy));
                        }
                        moves.add(3);
                        hy++;
                        frst = false;
                    } else if (hx == 0 && hy % 2 == 1) {
                        //out.printLine(3);
                        if (!frst) {
                            queue.pollLast();
                            queue.offer(new Point(hx, hy));
                        }
                        moves.add(3);
                        hy++;
                        frst = false;
                    } else {
                        if (hy % 2 == 0) {
                            //out.printLine(1);
                            if (!frst) {
                                queue.pollLast();
                                queue.offer(new Point(hx, hy));
                            }
                            moves.add(1);
                            hx++;
                            frst = false;
                        } else {
                            //out.printLine(0);
                            if (!frst) {
                                queue.pollLast();
                                queue.offer(new Point(hx, hy));
                            }
                            moves.add(0);
                            hx--;
                            frst = false;
                        }
                    }
                }
            }
        }
        out.printLine(moves.size());
        for (int move : moves)
            out.print(move + " ");
        assert (queue.size() == len);
        //System.out.println("len = " + len);
        //System.out.println("queue = " + queue.size());
        //System.out.println(Arrays.toString(queue.toArray()));
    }

    boolean mapAndTest(int hx, int hy, int destx, int desty) {
        LinkedList<Point> clone = (LinkedList<Point>) queue.clone();
        boolean vis[][] = new boolean[n][m];
        while (!clone.isEmpty()) {
            Point p = clone.pollFirst();
            assert (!vis[p.x][p.y]);
            vis[p.x][p.y] = true;
        }

        while (hx < destx - 1) {
            hx++;
            if (vis[hx][hy]) return false;
        }

        if (hy < desty) {
            if (hx % 2 == 1) {
                hx++;
                if (vis[hx][hy]) return false;
            }
            while (hy < desty) {
                hy++;
                if (vis[hx][hy]) return false;
            }
            if (hx < destx) {
                hx++;
                if (vis[hx][hy]) return false;
            }
        } else {
            if (hx % 2 == 0) {
                hx++;
                if (vis[hx][hy]) return false;
            }
            while (hy > desty) {
                hy--;
                if (vis[hx][hy]) return false;
            }
            if (hx < destx) {
                hx++;
                if (vis[hx][hy]) return false;
            }
        }
        return true;

    }

    /*
    0 - UP
    1 - DOWN
    2 - LEFT
    3 - RYT
     */
    enum MOVE {
        UP,
        DOWN,
        LEFT,
        RYT
    }
}

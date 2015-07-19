package Tasks;

import javaUtils.InReader;
import javaUtils.OutputWriter;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class SEASNAKE_revamped {
    /*
    0 - UP
    1 - DOWN
    2 - LEFT
    3 - RYT
     */

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

            /*if(n > 10 && len < Math.min(n, m) / 5 && smallMove(hx, hy, destx, desty)) {
                while(hy < desty) {
                    if(!frst) {
                        queue.pollLast();
                        queue.offer(new Point(hx, hy));
                    }
                    frst = false;
                    moves.add(3);
                    hy++;
                }
                while (hy > desty) {
                    if(!frst) {
                        queue.pollLast();
                        queue.offer(new Point(hx, hy));
                    }
                    frst = false;
                    moves.add(2);
                    hy--;
                }
                while(hx < destx) {
                    if(!frst) {
                        queue.pollLast();
                        queue.offer(new Point(hx, hy));
                    }
                    frst = false;
                    moves.add(1);
                    hx++;
                }
                while(hx > destx) {
                    if(!frst) {
                        queue.pollLast();
                        queue.offer(new Point(hx, hy));
                    }
                    frst = false;
                    moves.add(0);
                    hx--;
                }
            } */

            if (n % 2 == 0) {
                if (isValid1(hx, hy, destx, desty)) {
                    while (hx < destx - 1) {
                        if (!frst) {
                            queue.pollLast();
                            queue.offer(new Point(hx, hy));
                        }
                        frst = false;
                        moves.add(1);
                        hx++;
                    }
                    if (hx % 2 == 1) {
                        if (!frst) {
                            queue.pollLast();
                            queue.offer(new Point(hx, hy));
                        }
                        frst = false;
                        moves.add(1);
                        hx++;
                    }
                    while (hy < desty) {
                        if (!frst) {
                            queue.pollLast();
                            queue.offer(new Point(hx, hy));
                        }
                        frst = false;
                        moves.add(3);
                        hy++;
                    }
                    if (hx != destx) {
                        if (!frst) {
                            queue.pollLast();
                            queue.offer(new Point(hx, hy));
                        }
                        frst = false;
                        moves.add(1);
                        hx++;
                    }
                }
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
                        frst = false;
                        moves.add(1);
                        hx = 0;
                    } else if (hy == m - 1 && hx % 2 == 0) {
                        //out.printLine(1);
                        if (!frst) {
                            queue.pollLast();
                            queue.offer(new Point(hx, hy));
                        }
                        frst = false;
                        moves.add(1);
                        hx++;
                    } else if (hy == 0 && hx % 2 == 1) {
                        //out.printLine(1);
                        if (!frst) {
                            queue.pollLast();
                            queue.offer(new Point(hx, hy));
                        }
                        frst = false;
                        moves.add(1);
                        hx++;
                    } else {
                        if (hx % 2 == 0) {
                            //out.printLine(3);
                            if (!frst) {
                                queue.pollLast();
                                queue.offer(new Point(hx, hy));
                            }
                            frst = false;
                            moves.add(3);
                            hy++;
                        } else {
                            //out.printLine(2);
                            if (!frst) {
                                queue.pollLast();
                                queue.offer(new Point(hx, hy));
                            }
                            frst = false;
                            moves.add(2);
                            hy--;
                        }
                    }
                }
            } else {
                if (isValid2(hx, hy, destx, desty)) {

                    while (!(hy < desty)) {
                        if (!frst) {
                            queue.pollLast();
                            queue.offer(new Point(hx, hy));
                        }
                        hy--;
                        moves.add(2);
                    }

                    if (hx < destx) {
                        while (hy < desty - 1) {
                            if (!frst) {
                                queue.pollLast();
                                queue.offer(new Point(hx, hy));
                            }
                            frst = false;
                            moves.add(3);
                            hy++;
                        }

                        if (hy % 2 == 1) {
                            if (!frst) {
                                queue.pollLast();
                                queue.offer(new Point(hx, hy));
                            }
                            frst = false;
                            hy++;
                            moves.add(3);
                        }
                        while (hx < destx) {
                            if (!frst) {
                                queue.pollLast();
                                queue.offer(new Point(hx, hy));
                            }
                            frst = false;
                            hx++;
                            moves.add(1);
                        }
                        if (hy != desty) {
                            if (!frst) {
                                queue.pollLast();
                                queue.offer(new Point(hx, hy));
                            }
                            frst = false;
                            hy++;
                            moves.add(3);
                        }
                    } else {
                        while (hy < desty - 1) {
                            if (!frst) {
                                queue.pollLast();
                                queue.offer(new Point(hx, hy));
                            }
                            frst = false;
                            hy++;
                            moves.add(3);
                        }

                        if (hy % 2 == 0) {
                            if (!frst) {
                                queue.pollLast();
                                queue.offer(new Point(hx, hy));
                            }
                            frst = false;
                            hy++;
                            moves.add(3);
                        }
                        while (hx > destx) {
                            if (!frst) {
                                queue.pollLast();
                                queue.offer(new Point(hx, hy));
                            }
                            frst = false;
                            hx--;
                            moves.add(0);
                        }
                        if (hy != desty) {
                            if (!frst) {
                                queue.pollLast();
                                queue.offer(new Point(hx, hy));
                            }
                            frst = false;
                            hy++;
                            moves.add(3);
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
                        frst = false;
                        moves.add(3);
                        hy = 0;
                    } else if (hx == n - 1 && hy % 2 == 0) {
                        //out.printLine(3);
                        if (!frst) {
                            queue.pollLast();
                            queue.offer(new Point(hx, hy));
                        }
                        frst = false;
                        moves.add(3);
                        hy++;
                    } else if (hx == 0 && hy % 2 == 1) {
                        //out.printLine(3);
                        if (!frst) {
                            queue.pollLast();
                            queue.offer(new Point(hx, hy));
                        }
                        frst = false;
                        moves.add(3);
                        hy++;
                    } else {
                        if (hy % 2 == 0) {
                            //out.printLine(1);
                            if (!frst) {
                                queue.pollLast();
                                queue.offer(new Point(hx, hy));
                            }
                            frst = false;
                            moves.add(1);
                            hx++;
                        } else {
                            //out.printLine(0);
                            if (!frst) {
                                queue.pollLast();
                                queue.offer(new Point(hx, hy));
                            }
                            frst = false;
                            moves.add(0);
                            hx--;
                        }
                    }
                }
            }
        }
        out.printLine(moves.size());
        for (int move : moves)
            out.print(move + " ");
        assert (queue.size() == m * n);
    }

    boolean isValid2(int hx, int hy, int destx, int desty) {
        if (desty == 0) return false;
        if (!(desty > hy)) return false;
        LinkedList<Point> clone = (LinkedList<Point>) queue.clone();
        boolean[][] vis = new boolean[n][m];
        while (!clone.isEmpty()) {
            Point p = clone.pollFirst();
            assert (vis[p.x][p.y] == false);
            vis[p.x][p.y] = true;
        }

        while (!(hy < desty)) {
            hy--;
            if (vis[hx][hy]) return false;
        }

        if (hx < destx) {
            while (hy < desty - 1) {
                hy++;
                if (vis[hx][hy]) return false;
            }

            if (hy % 2 == 1) {
                hy++;
                if (vis[hx][hy]) return false;
            }
            while (hx < destx) {
                hx++;
                if (vis[hx][hy]) return false;
            }
            if (hy != desty) {
                hy++;
                if (vis[hx][hy]) return false;
            }
        } else {
            while (hy < desty - 1) {
                hy++;
                if (vis[hx][hy]) return false;
            }

            if (hy % 2 == 0) {
                hy++;
                if (vis[hx][hy]) return false;
            }
            while (hx > destx) {
                hx--;
                if (vis[hx][hy]) return false;
            }
            if (hy != desty) {
                hy++;
                if (vis[hx][hy]) return false;
            }

        }

        return true;
    }

    boolean isValid1(int hx, int hy, int destx, int desty) {
        if (!(destx > hx && desty > hy)) return false;
        LinkedList<Point> clone = (LinkedList<Point>) queue.clone();
        boolean[][] vis = new boolean[n][m];
        while (!clone.isEmpty()) {
            Point p = clone.pollFirst();
            assert (vis[p.x][p.y] == false);
            vis[p.x][p.y] = true;
        }

        while (hy < desty - 1) {
            hy++;
            if (vis[hx][hy]) return false;
        }

        if (hy % 2 == 1) {
            hy++;
            if (vis[hx][hy]) return false;
        }
        while (hx < destx) {
            hx++;
            if (vis[hx][hy]) return false;
        }
        if (hy != desty) {
            hy++;
            if (vis[hx][hy]) return false;
        }

        return true;

    }

    boolean smallMove(int hx, int hy, int destx, int desty) {
        LinkedList<Point> clone = (LinkedList<Point>) queue.clone();
        boolean[][] vis = new boolean[n][m];
        while (!clone.isEmpty()) {
            Point p = clone.pollFirst();
            assert (vis[p.x][p.y] == false);
            vis[p.x][p.y] = true;
        }

        while (hy < desty) {
            hy++;
            if (vis[hx][hy]) return false;
        }
        while (hy > desty) {
            hy--;
            if (vis[hx][hy]) return false;
        }
        while (hx < destx) {
            hx++;
            if (vis[hx][hy]) return false;
        }
        while (hx > destx) {
            hx--;
            if (vis[hx][hy]) return false;
        }
        return true;
    }
}

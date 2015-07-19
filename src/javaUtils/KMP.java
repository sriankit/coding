package javaUtils;

import java.util.ArrayList;

public class KMP {
    private final int R;       // the radix
    private int[][] dfa;       // the KMP automoton

    private char[] pattern;    // either the character array for the pattern
    private String pat;        // or the pattern string
    private int zero, M;

    // create the DFA from a String
    public KMP(String pat) {
        this.R = 256;
        this.pat = pat;

        // build DFA from pattern
        int M = pat.length();
        dfa = new int[R][M];
        dfa[pat.charAt(0)][0] = 1;
        for (int X = 0, j = 1; j < M; j++) {
            for (int c = 0; c < R; c++)
                dfa[c][j] = dfa[c][X];     // Copy mismatch cases.
            dfa[pat.charAt(j)][j] = j + 1;   // Set match case.
            X = dfa[pat.charAt(j)][X];     // Update restart state.
        }
    }

    // create the DFA from a character array over R-character alphabet
    public KMP(char[] pattern, int R, int zero) {
        this.R = R;
        this.pattern = new char[pattern.length];
        this.zero = zero;
        System.arraycopy(pattern, 0, this.pattern, 0, pattern.length);

        // build DFA from pattern
        this.M = pattern.length;
        dfa = new int[R][M + 1];
        dfa[pattern[0] - zero][0] = 1;
        for (int X = 0, j = 1; j <= M; j++) {
            for (int c = 0; c < R; c++)
                dfa[c][j] = dfa[c][X];     // Copy mismatch cases.
            dfa[pattern[j] - zero][j] = j + 1;      // Set match case.
            X = dfa[pattern[j] - zero][X];        // Update restart state.
        }
    }

    // return offset of first match; N if no match
    public ArrayList<Integer> search(String txt) {

        // simulate operation of DFA on text
        ArrayList<Integer> inds = new ArrayList<Integer>();
        int N = txt.length();
        int i, j;
        for (i = 0, j = 0; i < N; i++) {
            j = dfa[txt.charAt(i) - this.zero][j];
            System.out.println("txt.charAt(i) - this.zero = " + (txt.charAt(i) - this.zero));
            if (j == M) inds.add(i - M);
        }
        return inds;
    }


    // return offset of first match; N if no match
    public int search(char[] text) {
        // simulate operation of DFA on text
        int M = pattern.length;
        int N = text.length;
        int i, j;
        for (i = 0, j = 0; i < N && j < M; i++) {
            j = dfa[text[i]][j];
        }
        if (j == M) return i - M;    // found
        return N;                    // not found
    }
}

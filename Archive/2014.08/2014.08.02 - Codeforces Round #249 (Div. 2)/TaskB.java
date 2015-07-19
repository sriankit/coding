package Tasks;

import com.sun.org.apache.bcel.internal.generic.SWAP;
import javaUtils.InReader;
import javaUtils.MiscUtils;
import javaUtils.OutputWriter;

import java.util.Arrays;

public class TaskB {
    public void solve(int testNumber, InReader in, OutputWriter out) {
        int n;
        char[] num = in.readString().toCharArray();
        int k = in.readInt();
        n = num.length;
        int rep = 0;
        while(k > 0 && rep < n) {
            int maxi = rep;
            for (int i = rep; i < rep + k + 1 && i < n; i++) {
                if (num[maxi] < num[i]) maxi = i;
            }
            //System.out.println(Arrays.toString(num) + " " + maxi + " " + rep + " " + k);
            if(maxi == rep) rep++;
            else {
                while(maxi > rep) {
                    char t = num[maxi];
                    num[maxi] = num[maxi - 1];
                    num[maxi - 1] = t;
                    k--;
                    maxi--;
                }
            }
        }
        for(char c : num) {
            out.print(c);
        }
    }
}

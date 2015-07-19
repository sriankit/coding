package Tasks;

public class TheNumberGame {

    public String determineOutcome(int A, int B) {
        String loss = "Manao loses";
        String win = "Manao wins";
        String a = "" + A;
        String b = "" + B;
        String revB = new StringBuilder(b).reverse().toString();
        if (a.contains(b) || a.contains(revB)) return win;
        else return loss;

    }
}

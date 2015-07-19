package Tasks;

public class RowAndCoins {
    public String getWinner(String cells) {
        if (cells.charAt(0) == 'B' && cells.charAt(cells.length() - 1) == 'B') return "Bob";
        else return "Alice";
    }
}

public class Coin {
    private String coinOption = "";
    private String[] coinValue = {"Head", "Tails"};

    Coin(){
        int randNum = Math.random() < 0.5 ? 0 : 1;
        coinOption = coinValue[randNum];
    }

    public String getCoinOption(){
        return coinOption;
    }

    public static void main(String[] args) {


    }
}

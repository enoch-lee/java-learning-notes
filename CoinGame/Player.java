public class Player {
    private String name = "";
    private String coinOption = "";
    private String[] coinValue = {"Head", "Tails"};

    Player(String newname){
        name = newname;
    }

    public String getCoinOption(){
        return coinOption;
    }

    public void setCoinOption(String opponentFlip){
        coinOption = opponentFlip == "Head" ? "Tails" : "Head";
    }

    public String getRandCoinOption(){
        int randNum = Math.random() < 0.5 ? 0 : 1;
        coinOption = coinValue[randNum];
        return coinOption;
    }

    public void didPlayerWin(String winningFlip){
        if(coinOption == winningFlip){
            System.out.println(name + " won");
        }else{
            System.out.println(name + " lost");
        }
    }
}

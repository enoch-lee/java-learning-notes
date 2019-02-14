import java.util.Scanner;

public class CoinGame {
    private Player[] players = new Player[2];
    private Coin theCoin = new Coin();

    CoinGame(String player1name, String player2name){
        players[0] = new Player(player1name);
        players[1] = new Player(player2name);
    }

    public void startGame(){
        int randNum = Math.random() < 0.5 ? 0 : 1;
        String playePick = players[randNum].getRandCoinOption();
        players[1 - randNum].setCoinOption(playePick);
        String winningFlip = theCoin.getCoinOption();
        for(Player player : players){
            player.didPlayerWin(winningFlip);
        }
    }

    public static void main(String[] args){
        CoinGame coinGame = new CoinGame("Mark", "Rose");
        coinGame.startGame();
    }
}

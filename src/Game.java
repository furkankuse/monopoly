import java.util.Scanner;

public class Game {

    private Game() {

    }

    public void startTheGame() {
        Scanner input = new Scanner(System.in);
        System.out.print("Welcome User!\nPlease enter the number of the player(2 to 8) : ");
        int numberOfPlayers = input.nextInt();
        int numberOfTurns = 0;//This will be incremented at the end of the last player's turn

        while (numberOfPlayers < 2 || numberOfPlayers > 8) {
            System.out.println("Number of player is not valid, please enter a number between 2 and 8 : ");
            numberOfPlayers = input.nextInt();
        }

        Board board = new Board();
        Dice dice = new Dice();
        Player[] players = new Player[numberOfPlayers];

        for(int i = 0; i < numberOfPlayers; i++)
            players[i] = new Player(i, "Player" + i);

        while(numberOfPlayers > 1) {
            for(int i = 0; i < 6; i++) {
                if(!players[i].isBankrupt()) {
                    int size = board.getSize();
                    int sumOfFaces = players[i].tossDie(dice);
                    if((sumOfFaces + players[i].getPosition()) / size == 1);
                    //TODO add money adding function to here for passing from start

                    //positions starts from 1 we need to change it to 0
                    players[i].setPosition((players[i].getPosition() + sumOfFaces) % size);
                    int position = players[i].getPosition();
                    if(board.getBoard()[position] instanceof TaxSquare)
                        players[i].getMoney().subtractMoney(((TaxSquare)(board.getBoard()[position])).getTax());

                    players[i].nextTurn();

                    if(players[i].getMoney().isBankrupt()) {
                        players[i].setBankrupt(true);
                        numberOfPlayers--;
                    }
                }
            }
            numberOfTurns++;
        }

        int i;

        for(i = 0; i < numberOfPlayers; i++)
            if(players[i].isBankrupt()) break;

        System.out.println("Winner is " + players[i].getName());

        //TODO i will add a function to sort players by rolling a dice
        //TODO This might be added in second iteration

    }

    protected static Game instance() {
        return new Game();
    }

}
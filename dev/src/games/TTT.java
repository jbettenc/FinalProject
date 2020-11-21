package games;

import java.util.Random;

public class TTT extends Game {
    protected int[] board;
    protected String playerX;
    protected String playerO;
    public TTT(String u1) {
        //maybe validate user
        super(u1);
        super.gameType="ttt";
        super.gamePoints=1;
        board=new int[9];
        for(int i=0;i<9;i++){
            board[i]=0;
        }
        playerX="";
        playerO="";
    }
    public boolean addUser(String user2){
        //maybe validate user
        if(state!=0) return false;
        state=1;
        //find which user goes first
        Random rand=new Random();
        int rnum=rand.nextInt(2);
        if(rnum==0){
            playerX=user1;
            playerO=user2;
        }
        else{
            playerX=user2;
            playerO=user1;
        }
        System.err.println("The random number was "+rnum);
        return super.addUser(user2);
    }

    public int move(String user, String move) {
        if(state==5 || state==4) return 401; //HTTP Status Code: Unauthorized - game has already ended
        if(state==0) return 409; //HTTP Status Code: Conflict - game has not started yet, waiting for additional player
        if(state==3) return 409; //HTTP Status Code: Conflict - waiting for game to update moves

        if(user.equals(playerX)){   //user goes first
            //user has moved already
            if (state == 2){
            	return 409; //HTTP Status Code: Conflict - waiting for other user
            }
            //user has not moved, check if input valid
            if (move.length()==2) {
                int row=Integer.parseInt(move.substring(0,1));
                int col=Integer.parseInt(move.substring(1,2));
                if(row>=0 && row<3 && col>=0 && col<3){
                    if(board[(row*3)+col]==0){
                        //input is valid
                        board[(row*3)+col]=1;
                        if(playerX.equals(user1)) user1move = move;
                        else if(playerX.equals(user2)) user2move = move;
                        //update game
                        state=2;
                        updateGame();
                        return 200; //HTTP Status Code: OK
                    }
                }
            }
            return 406; //HTTP Status Code: Not Acceptable - invalid input move
        }
        else if(user.equals(playerO)){  //user goes second
            //first has not moved yet
            if (state == 1){
            	return 409; //HTTP Status Code: Conflict - waiting for other user
            }
            //first has moved, check if input valid
            if (move.length()==2) {
                int row=Integer.parseInt(move.substring(0,1));
                int col=Integer.parseInt(move.substring(1,2));
                if(row>=0 && row<3 && col>=0 && col<3){
                    if(board[(row*3)+col]==0){
                        //input is valid
                        board[(row*3)+col]=2;
                        if(playerO.equals(user1)) user1move = move;
                        else if(playerO.equals(user2)) user2move = move;
                        //update game
                        state=1;
                        updateGame();
                        return 200; //HTTP Status Code: OK
                    }
                }
            }
            return 406; //HTTP Status Code: Not Acceptable - invalid input move

        }
        return 401; //HTTP Status Code: Unauthorized - user is not in this game
    }

    public void updateGame() {
        if(state==1 || state==2){
            //check for win conditions(rows,cols,diagonals)
            if(board[0]==1 && board[1]==1 && board[2]==1){ //row 1
                if(playerX.equals(user1)) winner="0";
                else winner="1";
                state=5;
            }
            else if(board[0]==2 && board[1]==2 && board[2]==2){ //row 1
                if(playerX.equals(user1)) winner="1";
                else winner="0";
                state=5;
            }
            else if(board[3]==1 && board[4]==1 && board[5]==1){    //row 2
                if(playerX.equals(user1)) winner="0";
                else winner="1";
                state=5;
            }
            else if(board[3]==2 && board[4]==2 && board[5]==2){    //row 2
                if(playerX.equals(user1)) winner="1";
                else winner="0";
                state=5;
            }
            else if(board[6]==1 && board[7]==1 && board[8]==1){    //row 3
                if(playerX.equals(user1)) winner="0";
                else winner="1";
                state=5;
            }
            else if(board[6]==2 && board[7]==2 && board[8]==2){    //row 3
                if(playerX.equals(user1)) winner="1";
                else winner="0";
                state=5;
            }
            else if(board[0]==1 && board[3]==1 && board[6]==1){    //col 1
                if(playerX.equals(user1)) winner="0";
                else winner="1";
                state=5;
            }
            else if(board[0]==2 && board[3]==2 && board[6]==2){    //col 1
                if(playerX.equals(user1)) winner="1";
                else winner="0";
                state=5;
            }
            else if(board[1]==1 && board[4]==1 && board[7]==1){    //col 2
                if(playerX.equals(user1)) winner="0";
                else winner="1";
                state=5;
            }
            else if(board[1]==2 && board[4]==2 && board[7]==2){    //col 2
                if(playerX.equals(user1)) winner="1";
                else winner="0";
                state=5;
            }
            else if(board[2]==1 && board[5]==1 && board[8]==1){    //col 3
                if(playerX.equals(user1)) winner="0";
                else winner="1";
                state=5;
            }
            else if(board[2]==2 && board[5]==2 && board[8]==2){    //col 3
                if(playerX.equals(user1)) winner="1";
                else winner="0";
                state=5;
            }
            else if(board[0]==1 && board[4]==1 && board[8]==1){    //diag 1
                if(playerX.equals(user1)) winner="0";
                else winner="1";
                state=5;
            }
            else if(board[0]==2 && board[4]==2 && board[8]==2){    //diag 1
                if(playerX.equals(user1)) winner="1";
                else winner="0";
                state=5;
            }
            else if(board[2]==1 && board[4]==1 && board[6]==1){    //diag 2
                if(playerX.equals(user1)) winner="0";
                else winner="1";
                state=5;
            }
            else if(board[2]==2 && board[4]==2 && board[6]==2){    //diag 2
                if(playerX.equals(user1)) winner="1";
                else winner="0";
                state=5;
            }
            else{
                //no win condition, check if board full
                boolean full=true;
                for(int i=0;i<9;i++) {
                    if(board[i] == 0) {
                        full = false;
                        break;
                    }
                }
                if(full){
                    winner="2";
                    state=5;
                }
            }
        }
    }
}
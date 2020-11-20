package games;

public class RPS extends Game {
    protected int score;

    public RPS(String u1) {
        //maybe validate user
        super(u1);
        super.gameType="rps";
        super.gamePoints=1;
        score=0;
    }

    public boolean addUser(String user2){
        //maybe validate user
        if(state!=0) return false;
        state=1;
        return super.addUser(user2);
    }

    public String move(String user, String move) {
        if(state==5) return "Game has already ended. "+winner+" was the winner";
        if(state==0) return "Game has not started yet. Waiting for additional player...";
        if(state==4) return "Game is updating previous moves. Please wait...";
        if(user.equals(user1)) {    //user is user1
            //user1 has moved already
            if (state == 2) return user+" has already moved.\nWaiting for "+user2+"...";
            //user1 has not moved, check if input valid
            if (move.equals("0") || move.equals("1") || move.equals("2")) {
                //input is valid
                user1move = move;
                //update game
                if (state == 1) state = 2;
                else if (state == 3) state = 4;
                updateGame();
                return "Processed Successfully!";
            }
            return move+" is an invalid input";
        }
        else if(user.equals(user2)){    //user is user2
            //user 2 has moved already
            if (state == 3) return user+" has already moved...\nWaiting for "+user1+"...";
            //user2 has not moved, check if input valid
            if (move.equals("0") || move.equals("1") || move.equals("2")) {
                //input is valid
                user2move = move;
                //update game
                if(state==1) state=3;
                else if(state==2) state=4;
                updateGame();
                return "Processed Successfully!";
            }
            return move+" is an invalid input";
        }
        return user+" is not in this game";
    }

    public void updateGame() {
        if(state==4){
            //if user1 wins increment by 10
            //if user2 wins increment by 1
            if(user1move.equals("0") && user2move.equals("1")){
                winner="1";
                score++;
            }
            else if(user1move.equals("0") && user2move.equals("2")){
                winner="0";
                score+=10;
            }
            else if(user1move.equals("1") && user2move.equals("2")){
                winner="1";
                score++;
            }
            else if(user1move.equals("1") && user2move.equals("0")){
                winner="0";
                score+=10;
            }
            else if(user1move.equals("2") && user2move.equals("0")){
                winner="1";
                score++;
            }
            else if(user1move.equals("2") && user2move.equals("1")){
                winner="0";
                score+=10;
            }
            else winner="2";
            if((score/10)==2 || (score%10)==2){
                if(score/10==2) winner="0";
                else winner="1";
                state=5;
            }
            else state=1;
        }
    }
}
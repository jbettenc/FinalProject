package games;

import java.util.UUID;

public abstract class Game{
    //general information
    protected UUID gameID;
    protected String gameType;
    protected int gamePoints;
    protected String user1;
    protected String user2;
    //game specific info
    protected int state;
    protected String user1move;
    protected String user2move;
    protected String winner;

    protected Game(String u1){//constructor
        this.user1=u1;
        this.user2="";
        this.gameID=UID.getID();
        this.state=0;       //0-wait, 1-ready, 2-waiting for user2, 3-waiting for user1, 4-update, 5-end
        this.user1move="";
        this.user2move="";
        this.winner="";     //0-user1 won, 1-user2 won, 2-tie
    }
    protected boolean addUser(String u2){//triggers game start
        this.user2=u2;
        return true;
    }

    //main logic methods
    protected abstract String move(String user, String move);
    protected abstract void updateGame();
}

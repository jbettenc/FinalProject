package games;

import java.io.*;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GameTest{
    public static final String GREEN="\033[0;32m";
    public static final String RED="\033[0;31m";
    public static final String RESET="\033[0m";
    public static final String BOLD="\033[1;30m";
    public static void main(String[] args) throws IOException {
        Scanner in=new Scanner(System.in);
        Gson gson=new GsonBuilder().setPrettyPrinting().create();

        //front end request certain game
        boolean valid=false;
        String gameType="";
        Game g;
        while(!valid){
            System.out.print("Game Type (rps,ttt): ");
            gameType=in.nextLine();
            if(gameType.equals("rps") || gameType.equals("ttt")){
                valid=true;
                System.out.println(GREEN+"Accepted!"+RESET);
            }
            else{
                System.out.println(RED+"Invalid game type."+RESET);
            }
        }

        //user identification to be obtained from frontend
        //realistically, users would be added to games non simultaneously
        System.out.print("User 1: ");
        String user1=in.nextLine();
        //user 1 joins game
        if(gameType.equals("rps")){
            g=new RPS(user1);
        }
        else{
            g=new TTT(user1);
        }
        //display waiting JSON
        System.out.println(BOLD+"\nJSON returned:\n"+gson.toJson(g)+"\n"+RESET);
        //add user 2
        System.out.print("User 2: ");
        String user2=in.nextLine();
        g.addUser(user2);
        //display ready game JSON
        System.out.println(BOLD+"\nJSON returned:\n"+gson.toJson(g)+"\n"+RESET);

        //user that wants to move
        String u;

        //loop while game is ongoing
        while(g.state!=5){
            //frontend sends move request
            System.out.print("Enter user requesting a selection: ");
            u=in.nextLine();
            if(gameType.equals("rps")) System.out.print("Enter selection for "+u+" (0,1,2): ");
            else if(gameType.equals("ttt")) System.out.print("Enter move for "+u+" (rowcol): ");
            String move=in.nextLine();
            //attempt move request
            int out=g.move(u,move);
            if(out==200) System.out.println(GREEN+out+" - OK"+RESET);
            else System.out.println(RED+out+RESET);
            System.out.println(BOLD+"\nJSON returned:\n"+gson.toJson(g)+"\n"+RESET);
        }
        //Display final JSON
        System.out.println(BOLD+"\nGAMEOVER\nJSON returned:\n"+gson.toJson(g)+"\n"+RESET);
        in.close();
    }
}
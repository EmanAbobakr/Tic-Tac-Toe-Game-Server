/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import tictactoelibrary.LoginModel;
import tictactoelibrary.SignUpModel;
import java.lang.Thread;
import requests.*;

/**
 *
 * @author MOHAMED ADEL
 */
public class ServerHandler implements Runnable {

    private ObjectInputStream dis;
    private ObjectOutputStream ps;
    private Socket sc;

    static Vector<ServerHandler> clientsVector = new Vector<ServerHandler>();
    private String username;

    public ServerHandler(Socket cs) {

        System.out.println("ServerHandler fun ");
        sc = cs;
        try {
            ps = new ObjectOutputStream(sc.getOutputStream());
            dis = new ObjectInputStream(sc.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        ServerHandler.clientsVector.add(this);
        Thread thread = new Thread(this);
        thread.start();
        System.out.println("after start function ");

    }

    public ServerHandler() {
    }

    @Override
    public void run() {

        System.out.println("run function ");

        DatabaseManager.getInstance().connection();
        //move to constructor
        while (true) {
            Object obj = null;
            try {
                obj = dis.readObject();
                if (obj instanceof SignUpModel) {
                    System.out.println("User is trying to sign up");
                    SignUpModel player = (SignUpModel) obj;
                    Boolean checkSaving = new Boolean(DatabaseManager.getInstance().signUPUser(player));
                    System.out.println(checkSaving);
                    //Friday
                    if(checkSaving){
                        String str = new String("signup");
                        ps.writeObject(str);
                    }
                    else{
                        String str = new String("notSignup");
                        ps.writeObject(str);
                    }
                    /////ps.writeObject(checkSaving);
                } else if (obj instanceof LoginModel) {
                    System.out.println("User is trying to login");
                    LoginModel player = (LoginModel) obj;
                    System.out.println(player.getUsername());
                    System.out.println(player.getPassword());
                    boolean testData = DatabaseManager.getInstance().loginUser(player);
                    Boolean checkUserData = new Boolean(testData);
                    System.out.println(checkUserData);
                    if(checkUserData){
                        String str = new String("login");
                        ps.writeObject(str);
                    }else{
                        String str = new String("notLogin");
                        ps.writeObject(str);
                    }
                    //ps.writeObject(checkUserData);

                    if (checkUserData) {
                        username = player.getUsername();
                    }
                } else if (obj instanceof String) {
                    String s = (String) obj;
                    if (s.equals("getOnlineUser")) {
                        System.out.println("A user asked to get online users");
                        sendOnlineUsersToAll();
                    }
                }
                else if(obj instanceof GameRequest)
                {
                    GameRequest gameRequest = (GameRequest) obj;
                    String recieverPlayer = gameRequest.getRecieverPlayer();
                    System.out.println("server print player1: " + gameRequest.getStartingPlayer());
                    System.out.println("server print player1: " + gameRequest.getRecieverPlayer());
                    sendRequestToPlayer(recieverPlayer,gameRequest);
                }
                
                else if(obj instanceof AcceptancePlayingRequest)
                {
                    AcceptancePlayingRequest accept = (AcceptancePlayingRequest) obj;
                    sendToGame(accept);
                    sendOnlineUsersToAll();
                    
                }
                
                else if(obj instanceof GameMove)
                {
                    GameMove move = (GameMove) obj;
                    sendMoveToPlayer(move);
                    
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SocketException ex) {

                try {
                    ps.close();
                    dis.close();
                } catch (IOException ex1) {

                    Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex1);
                }

            } catch (EOFException ex) {
                System.out.println("EOF1");
            } catch (IOException ex) {
                System.out.println("ioexp");
                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    void sendOnlineUsersToAll() {
        //OnlineUsers ou = new OnlineUsers(DatabaseManager.getInstance().getOnlineUsers());
        OnlineUsersVector ouv = new OnlineUsersVector();
        for (int i = 0; i < OnlineUsersVector.onlineUsersVec.size(); i++) {
            System.out.println(OnlineUsersVector.onlineUsersVec.get(i));
            ouv.bigOnlineUsersVec.add(OnlineUsersVector.onlineUsersVec.get(i));

        }
        for (int i = 0; i < OnlineUsersVector.onlineUsersVec.size(); i++) {
            System.out.println(ouv.bigOnlineUsersVec.get(i));

        }
        for (ServerHandler sh : clientsVector) {
            try {
//                sh.ps.writeObject(OnlineUsersVector.onlineUsersVec);
                sh.ps.writeObject(ouv);
            } catch (IOException ex) {
                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("I send online users to all users");
    }
    
    void sendRequestToPlayer(String player,GameRequest req)
    {
        System.out.println("send request to player:");
        //clientsVector to online only clients
        for (ServerHandler sh : clientsVector) {
            if(sh.username.equals(player))
            {
                System.out.println(sh.username);
                try {
                    sh.ps.writeObject(req);
                } catch (IOException ex) {
                    Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }            
        }        
    }
    
    void sendToGame(AcceptancePlayingRequest acceptRequest)
    {
        System.out.println("send request to player:");
        //clientsVector to online only clients
        ServerHandler playerSocHer1 = null;
        ServerHandler playerSocHer2 = null;
        for (ServerHandler sh : clientsVector) {
            if(sh.username.equals(acceptRequest.getPlayer1()) || sh.username.equals(acceptRequest.getPlayer2()))
            {
                System.out.println(sh.username);
                try {
                    sh.ps.writeObject(acceptRequest);
                    OnlineUsersVector.onlineUsersVec.removeElement(sh.username);
                    if(sh.username.equals(acceptRequest.getPlayer1()))
                    {
                        playerSocHer1 = sh;
                    }
                    else if(sh.username.equals(acceptRequest.getPlayer2()))
                    {
                        playerSocHer2 = sh;
                    }
                    
                } catch (IOException ex) {
                    Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }            
        }
        InGame.inGame.put(acceptRequest.getPlayer1(), playerSocHer2);
//        InGame.inGame.get(acceptRequest.getPlayer1()).dis.readObject();
        InGame.inGame.put(acceptRequest.getPlayer2(), playerSocHer1);
    }
    void sendMoveToPlayer(GameMove move)
    {
        System.out.println("send Move to player:");
        try
        {
            InGame.inGame.get(move.getSenderPlayer()).ps.writeObject(move);
        }catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
}

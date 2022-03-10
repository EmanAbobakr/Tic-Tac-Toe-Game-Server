/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import requests.OnlineUsers;
/**
 *
 * @author EmanAbobakr
 */
public class OnlineUsersManager extends Thread{
    OnlineUsers ou;
    
    public void updateOnlineUsers(){
        //get online users
        ///ou = new OnlineUsers(DatabaseManager.getInstance().getOnlineUsers());
        //send online users to all players
        ///ServerHandler serverHandler = new ServerHandler();
//        ObjectInputStream ois;
//        ObjectOutputStream oos;
//        Thread thread = new Thread(this);
//        thread.start();
//        
        //DatabaseManager.getInstance().getOnlineUsers();
    }
    
//    public void run(){
//        while(true){
//            
//        }
//    }
}

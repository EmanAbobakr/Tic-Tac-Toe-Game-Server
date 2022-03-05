/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver;

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

/**
 *
 * @author MOHAMED ADEL
 */
public class ServerHandler implements Runnable {

    private ObjectInputStream dis;
    private ObjectOutputStream ps;
    private Socket sc;

    static Vector<ServerHandler> clientsVector = new Vector<ServerHandler>();

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

    private ServerHandler() {
    }

    @Override
    public void run() 
    {
        int counter = 0;
         Object obj = null;
        System.out.println("run function ");
        try {
            obj = dis.readObject();
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
         while(true){
           
            try {                
                DatabaseManager.getInstance().connection();               
                
                if(obj instanceof SignUpModel && counter == 0)
                {
                    System.out.println("User is trying to sign up");
                    SignUpModel player = (SignUpModel) obj;
                    Boolean checkSaving = new Boolean(DatabaseManager.getInstance().signUPUser(player));                        
                    System.out.println(checkSaving);
                    Boolean bool  = new Boolean(true);
                    System.out.println(bool);
                    ps.writeObject(checkSaving);
                    counter++;
                }
                else if(obj instanceof LoginModel  && counter == 0)
                {
                    System.out.println("User is trying to login");
                    LoginModel player = (LoginModel) obj;
                    System.out.println(player.getUsername());
                    System.out.println(player.getPassword());
                    Boolean checkUserData = new Boolean(DatabaseManager.getInstance().loginUser(player));                        
                    System.out.println(checkUserData);
                    ps.writeObject(checkUserData);
                    //set online status and avaiability
                    counter++;
                }
            } 
//            catch (ClassNotFoundException ex) {
//                System.out.println("class read object 3");
//                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
//            }
            catch (IOException ex) {
                
                System.out.println("readobject and writeobject 6");
                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
              
                dis.close();   
                ps.close();
                sc.close();
            } catch (IOException ex) {
                 System.out.println("closing connection 5");
                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        
    }

}

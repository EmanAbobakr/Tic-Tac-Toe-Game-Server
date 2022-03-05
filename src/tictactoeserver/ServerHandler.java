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




public class ServerHandler implements Runnable{

    ObjectInputStream dis;
    ObjectOutputStream ps;
    
    
    static Vector<ServerHandler> clientsVector = new Vector<ServerHandler>();
    public ServerHandler(Socket cs) {
        try {
            System.out.println("ServerHandler fun ");            
            ps = new ObjectOutputStream(cs.getOutputStream());
            dis = new ObjectInputStream(cs.getInputStream());
            ServerHandler.clientsVector.add(this);
            Thread thread = new Thread(this);
            thread.start();
             System.out.println("after start function ");
        }
        catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ServerHandler() {
    }
    @Override
    public void run(){
        System.out.println("run function ");
        while(true){
            try {
                Object obj;
                try {
                    obj = dis.readObject();
                    if(obj instanceof SignUpModel)
                    {
                        System.out.println("User is trying to sign up");
                        SignUpModel player = (SignUpModel) obj;
                        Boolean checkSaving = new Boolean(DatabaseManager.getInstance().signUPUser(player));                        
                        System.out.println(checkSaving);
                        Boolean bool  = new Boolean(true);
                        System.out.println(bool);
                        ps.writeObject(checkSaving);
                    }
                    else if(obj instanceof LoginModel)
                    {
                        System.out.println("User is trying to login");
                        LoginModel player = (LoginModel) obj;
                        System.out.println(player.getUsername());
                        System.out.println(player.getPassword());
                        Boolean checkUserData = new Boolean(DatabaseManager.getInstance().loginUser(player));                        
                        System.out.println(checkUserData);
                        ps.writeObject(checkUserData);
                        //set online status and avaiability
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
            } catch(SocketException ex){
                try {
                    dis.close();
                } catch (IOException ex1) {
                    Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }catch (IOException ex) {
                Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
   
    /*
    ObjectInputStream ois;
    ObjectOutputStream oos;
    static Vector<ServerHandler> serverHandlers = new Vector<ServerHandler>();
    public ServerHandler(Socket socket) {
        try {
            System.out.println("entered try2");
            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            ServerHandler.serverHandlers.add(this);
            System.out.println("entered try 2.5");
            start();
            System.out.println("entered try 2.5");
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void run() {
        System.out.println("entered try3");
        while(true){
            
            try {      
                    
                    Object obj = ois.readObject();
                    if(obj instanceof SignUpModel)
                    {
                        System.out.println("User is trying to sign up");
                        SignUpModel player = (SignUpModel) obj;
                        Boolean checkSaving = new Boolean(DatabaseManager.getInstance().signUPUser(player));                        
                        System.out.println(checkSaving);
                        Boolean bool  = new Boolean(true);
                        System.out.println(bool);
                        oos.writeObject(bool);
                    }                    
                } catch (IOException ex) {
                    Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    */
}


package tictactoeserver;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import tictactoelibrary.LoginModel;
import tictactoelibrary.SignUpModel;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLServerSocket;

/**
 *
 * @author MOHAMED ADEL
 */
public class Server {

    ServerSocket serSoc;
    Socket waiter;
//    DataInputStream dis;
//    DataOutputStream dos;
    ObjectOutputStream oos;
    ObjectInputStream ois;

    public Server() {

        try {
            serSoc = new ServerSocket(5005);
            while (true) {
                Socket s = serSoc.accept();
                new ServerHandler(s);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        //
        //serSoc = new ServerSocket(5005);

//            oos = new ObjectOutputStream(waiter.getOutputStream());
//            ois = new ObjectInputStream(waiter.getInputStream());
        /*
            while(true){
                System.out.println("entered try");
                waiter = serSoc.accept();
                new ServerHandler(waiter);
                
//                                       
//                    Object obj = ois.readObject();
//                    if(obj instanceof SignUpModel)
//                    {
//                        System.out.println("User is trying to sign up");
//                        SignUpModel player = (SignUpModel) obj;
//                        Boolean checkSaving = new Boolean(DatabaseManager.getInstance().signUPUser(player));                        
//                        System.out.println(checkSaving);
//                        Boolean bool  = new Boolean(true);
//                        System.out.println(bool);
//                        oos.writeObject(bool);
//                    }
//                    else if(obj instanceof LoginModel)
//                    {
//                        System.out.println("User is trying to login");
//                         LoginModel player = (LoginModel) obj;
//                        System.out.println(player.getUsername());
//                        System.out.println(player.getPassword());
////                        String validData = "1";
////                        oos.writeObject(validData);
//                    }
//                    else{
//                        System.out.println("failed");
//                    }
            }
         */
    }
}

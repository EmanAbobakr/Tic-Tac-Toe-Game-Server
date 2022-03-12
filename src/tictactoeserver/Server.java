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
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javax.net.ssl.SSLServerSocket;

/**
 *
 * @author MOHAMED ADEL
 */
public class Server implements Runnable{

    ServerSocket serSoc;

    public Server() {
        System.out.println("We are in new server");
        Thread thread = new Thread(this);
        thread.start();       
    }
    
    @Override
    public void run() {
        try {
            serSoc = new ServerSocket(5005);
            while (true) {
                Socket s = serSoc.accept();
                new ServerHandler(s);
            }
        }catch(SocketException e){
            System.out.println("We handled it");
            System.out.println("socket exception");    
        }
        catch (IOException ex) {
            
            System.out.println("io exception");
            Logger.getLogger(ServerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            serSoc.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

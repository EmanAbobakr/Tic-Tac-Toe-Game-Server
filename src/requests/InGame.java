/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requests;

import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author MOHAMED ADEL
 */
public class InGame {
    public static HashMap<String, Socket> inGame = new HashMap<String , Socket>();
    public HashMap<String, Socket> publicInGame = new HashMap<String , Socket>();

    public InGame() {
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requests;

import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import tictactoeserver.ServerHandler;

/**
 *
 * @author MOHAMED ADEL
 */
public class InGame {
    public static Map<String, ServerHandler> inGame = Collections.synchronizedMap(new HashMap<>());
    public Map<String, ServerHandler> publicInGame = Collections.synchronizedMap(new HashMap<>());

    public InGame() {
    }
    
}

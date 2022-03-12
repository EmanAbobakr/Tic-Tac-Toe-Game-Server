/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requests;

import java.io.Serializable;

/**
 *
 * @author MOHAMED ADEL
 */
public class GameMove implements Serializable {
    private String senderPlayer;

    public GameMove(String senderPlayer, String recieverPlayer, String index) {
        this.senderPlayer = senderPlayer;
        this.recieverPlayer = recieverPlayer;
        this.index = index;
    }

    public GameMove() {
    }

    public String getSenderPlayer() {
        return senderPlayer;
    }

    public void setSenderPlayer(String senderPlayer) {
        this.senderPlayer = senderPlayer;
    }

    public String getRecieverPlayer() {
        return recieverPlayer;
    }

    public void setRecieverPlayer(String recieverPlayer) {
        this.recieverPlayer = recieverPlayer;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
    private String recieverPlayer;
    private String index;
    
}

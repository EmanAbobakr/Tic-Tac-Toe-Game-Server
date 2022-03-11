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
public class GameRequest implements Serializable{
    private String startingPlayer;
    private String recieverPlayer;

    public GameRequest() {
    }

    public GameRequest(String startingPlayer, String recieverPlayer) {
        this.startingPlayer = startingPlayer;
        this.recieverPlayer = recieverPlayer;
    }

    public String getStartingPlayer() {
        return startingPlayer;
    }

    public void setStartingPlayer(String startingPlayer) {
        this.startingPlayer = startingPlayer;
    }

    public String getRecieverPlayer() {
        return recieverPlayer;
    }

    public void setRecieverPlayer(String recieverPlayer) {
        this.recieverPlayer = recieverPlayer;
    }
    
    
    
}

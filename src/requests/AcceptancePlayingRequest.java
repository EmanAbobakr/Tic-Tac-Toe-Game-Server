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
public class AcceptancePlayingRequest implements Serializable{
    private String player1;
    private String player2;

    public String getPlayer1() {
        return player1;
    }

    public AcceptancePlayingRequest() {
        
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }
    
    
}

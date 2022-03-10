/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requests;

import java.util.ArrayList;

/**
 *
 * @author EmanAbobakr
 */
public class OnlineUsers {
    private ArrayList<String> onlineUsers;

    public OnlineUsers(ArrayList<String> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    public ArrayList<String> getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(ArrayList<String> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }
    
    
}

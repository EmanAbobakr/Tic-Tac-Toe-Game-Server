/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requests;

import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author EmanAbobakr
 */
public class OnlineUsersVector implements Serializable{
    public static Vector<String> onlineUsersVec = new Vector<String>();
    public Vector<String> bigOnlineUsersVec = new Vector<String>();
    
    public OnlineUsersVector() {
    }
       
    
    
}

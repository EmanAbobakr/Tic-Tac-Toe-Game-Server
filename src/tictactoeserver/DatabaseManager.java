/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.derby.jdbc.ClientDriver;
import tictactoelibrary.LoginModel;
import tictactoelibrary.SignUpModel;


/**
 *
 * @author EmanAbobakr
 */
public class DatabaseManager {
    
    Connection con;
    private static DatabaseManager datbaseModel;
            
    public void connection(){
        try{
            DriverManager.registerDriver(new ClientDriver());
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/TicTacToe", "root","root");
        }catch(SQLException ex){
            System.out.print("Connection failed ya java");
        }
    }
    public boolean signUPUser(SignUpModel user)
    {
        
        try {
            PreparedStatement pst = con.prepareStatement("SELECT name FROM UserTable WHERE name = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pst.setString(1, user.getUsername());
            ResultSet rs = pst.executeQuery();
            if(rs.next())
            {
                return false;
            }
            pst = con.prepareStatement("INSERT INTO userTable ( name, pass, totalscore, isOnline, isAvailable) " + "VALUES ( ?, ?, ?, ?, ?)");
            pst.setString(1, user.getUsername());
            pst.setInt(2, user.getPassword());
            pst.setInt(3, 0);
            pst.setBoolean(4, true);
            pst.setBoolean(5, true);
            int checkSaving = pst.executeUpdate();
            if(checkSaving == 1)
            {
                return true;
            }            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    public boolean loginUser(LoginModel user)
    {
         try {
            PreparedStatement pst = con.prepareStatement("SELECT name, pass FROM UserTable WHERE name = ? AND pass = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pst.setString(1, user.getUsername());
            pst.setInt(2, user.getPassword());
            ResultSet rs = pst.executeQuery();
            if(rs.next())
            {
                return true;
            }            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public static DatabaseManager getInstance() {
        if (datbaseModel == null) {
            datbaseModel = new DatabaseManager();
        }
        
        return datbaseModel;
    }

}

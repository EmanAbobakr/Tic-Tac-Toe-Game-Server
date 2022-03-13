/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoeserver;

import requests.OnlineUsersVector;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.jdbc.ClientDriver;
import tictactoelibrary.LoginModel;
import tictactoelibrary.SignUpModel;
import requests.*;
import interfaces.PieChartInterface;
import java.util.HashMap;
import javafx.application.Platform;

/**
 *
 * @author EmanAbobakr
 */
public class DatabaseManager {

    Connection con;
    private static DatabaseManager datbaseModel;
    PieChartInterface piechartRef;

    public void connection() {
        try {
            DriverManager.registerDriver(new ClientDriver());
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/TicTacToe", "root", "root");
            System.out.println("Connection to db done");
        } catch (SQLException ex) {
            System.out.print("Connection failed ya java");
        }
    }

    public boolean signUPUser(SignUpModel user) {
        ResultSet rs = null;
        try {
            PreparedStatement pst = con.prepareStatement("SELECT name FROM UserTable WHERE name = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pst.setString(1, user.getUsername());
            rs = pst.executeQuery();
            if (rs.next()) {
                return false;
            }
            pst = con.prepareStatement("INSERT INTO userTable ( name, pass, totalscore, isOnline, isAvailable) " + "VALUES ( ?, ?, ?, ?, ?)");
            pst.setString(1, user.getUsername());
            pst.setInt(2, user.getPassword());
            pst.setInt(3, 0);
            pst.setBoolean(4, false);
            pst.setBoolean(5, false);
            int checkSaving = pst.executeUpdate();
            if (checkSaving == 1) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean loginUser(LoginModel user) {
        ResultSet rs = null;
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement("SELECT name, pass FROM UserTable WHERE name = ? AND pass = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pst.setString(1, user.getUsername());
            pst.setInt(2, user.getPassword());

            System.out.println(user.getUsername());
            System.out.println(user.getPassword());

            rs = pst.executeQuery();
            if (rs.next()) {
                updateOnline(user.getUsername());
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            rs.close();
            //pst.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public ArrayList<String> getOnlineUsers() {
        ArrayList<String> onlineUsers = new ArrayList<String>();

        try {
            PreparedStatement pst = con.prepareStatement("SELECT name FROM USERTABLE WHERE isonline = true", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                onlineUsers.add(rs.getString(1));
                System.out.println(rs.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println("ops ops db online users");
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return onlineUsers;
    }

    public static DatabaseManager getInstance() {
        if (datbaseModel == null) {
            datbaseModel = new DatabaseManager();
        }

        return datbaseModel;
    }

    public void updateOnline(String username) {
        ResultSet rs = null;
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement("update UserTable set isonline = ? WHERE name = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //PreparedStatement st = db.con.prepareStatement("UPDATE item SET Name = ?, Size = ?, Price = ?, WHERE ItemCode = ?");
            //OnlineUsersVector.getInstance().onlineUsersVec.add(username);
            OnlineUsersVector.onlineUsersVec.add(username);
            pst.setBoolean(1, true);
            pst.setString(2, username);
            System.out.println(username);
            pst.executeUpdate();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    piechartRef.updatePieChart();
                }
            });

            pst.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateOffline(String username) {
        ResultSet rs = null;
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement("update UserTable set isonline = ? WHERE name = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            OnlineUsersVector.onlineUsersVec.add(username);
            OnlineUsersVector.onlineUsersVec.remove(username);
            pst.setBoolean(1, false);
            pst.setString(2, username);
            System.out.println(username);
            pst.executeUpdate();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    piechartRef.updatePieChart();
                }
            });

            pst.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int numberOfOnlineUsers() {
        ResultSet rs = null;
        try {
            PreparedStatement pst = con.prepareStatement("SELECT COUNT(name) FROM UserTable WHERE isOnline = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pst.setBoolean(1, true);
            rs = pst.executeQuery();
//            while (rs.next()) {
//                onlinePlayersCount++;
//            }
            rs.next();
            System.out.println(Integer.parseInt(rs.getString(1)));
            return Integer.parseInt(rs.getString(1));
            //return 10;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int numberOfAllUsers() {
        ResultSet rs = null;
        try {
            PreparedStatement pst = con.prepareStatement("SELECT COUNT(name) FROM UserTable", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
//pst.setBoolean(5, true);
            rs = pst.executeQuery();
//            while (rs.next()) {
//                allPlayersCount++;
//            }
            rs.next();
            return Integer.parseInt(rs.getString(1));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        try {
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public HashMap<String, String> getPlayersWithScores() {
        HashMap<String, String> scores = new HashMap<String, String>();
        ResultSet rs = null;
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement("SELECT name,totalscore FROM UserTable", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = pst.executeQuery();

            while (rs.next()) {
                scores.put(rs.getString(1), rs.getString(2));

            }
            System.out.println(scores);
            pst.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return scores;
    }

    public void updatePlayersScore(String name) {
        ResultSet rs = null;
        PreparedStatement pst = null;
        try {
            pst = con.prepareStatement("UPDATE UserTable SET totalscore = totalscore + 10 WHERE name = ?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pst.setString(1, name);
            pst.executeUpdate();
            pst.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void closeConnection() {
        try {
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}

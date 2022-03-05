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
    
    /*
    public ResultSet fetch(){
        //Model fetchedDataObj = new Model();
        //ArrayList<Model> fetchedData = new ArrayList<Model>();
        try {
            PreparedStatement pst = con.prepareStatement("select * from persontable", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = pst.executeQuery() ;
            
            while(rs.next()){
                
                fetchedDataObj.setId(Integer.parseInt(rs.getString(1)));
                fetchedDataObj.setfName(rs.getString(2));
                fetchedDataObj.setlName(rs.getString(3));
                fetchedDataObj.setmName(rs.getString(4));
                fetchedDataObj.setEmail(rs.getString(5));
                fetchedDataObj.setPhoneNumber(rs.getString(6));
                fetchedData.add(fetchedDataObj);
                
                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
                System.out.println(rs.getString(4));
                System.out.println(rs.getString(5));
                System.out.println(rs.getString(6));
            }
            
            return rs;
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public void insert(Model insertedDate){
        try {
            /*
            stmt = con.createStatement();
            String queryString = new String("insert into pesrontable values()");

            PreparedStatement pst = con.prepareStatement("insert into persontable (id,fname,lname,mname,email,phone) values(?, ?, ?, ?, ?, ?)");
            pst.setInt(1, insertedDate.getId());
            pst.setString(2, insertedDate.getfName());
            pst.setString(3, insertedDate.getlName());
            pst.setString(4, insertedDate.getmName());
            pst.setString(5, insertedDate.getEmail());
            pst.setString(6, insertedDate.getPhoneNumber());
            pst.executeUpdate();
            System.out.println("Successed insertion");
            
        } catch (SQLException ex) {
            System.out.println("Failed insertion");
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void update(Model insertedDate){
        try {
            PreparedStatement pst = con.prepareStatement("update persontable set fname = ?, lname = ?, mname = ?, email = ?, phone = ? where id=?");
            
            pst.setString(1, insertedDate.getfName());
            pst.setString(2, insertedDate.getlName());
            pst.setString(3, insertedDate.getmName());
            pst.setString(4, insertedDate.getEmail());
            pst.setString(5, insertedDate.getPhoneNumber());
            pst.setInt(6, insertedDate.getId());
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete(Model insertedDate){
        try {
            PreparedStatement pst = con.prepareStatement("delete from persontable where id = ?");       
           
            pst.setInt(1, insertedDate.getId());
            pst.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void close(){
        try {
            stmt.close();
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseManager.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }

    private String String(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    */
    public static DatabaseManager getInstance() {
        if (datbaseModel == null) {
            datbaseModel = new DatabaseManager();
        }
        return datbaseModel;
    }

}

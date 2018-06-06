/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testDB;

/**
 *
 * @author EXTmsouto
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;



public class SQLiteManager {

    public static SQLiteManager getInstance() {
        return new SQLiteManager();
    }
    
    Connection connection;
    Statement statement;
    String url;

    private SQLiteManager() {
        this.url = "sample.db";
    }
    
    
    
 
    public void init(){
       
        try {
            connection = getConnection();
            
            statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            
            //statement.executeUpdate("DROP TABLE user");
            //statement.executeUpdate("DROP TABLE feed IF EXISTS");
            
    
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR(32) NOT NULL, password VARCHAR(32) NOT NULL, email VARCHAR(32) NOT NULL)");
            
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS feed (id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR(64) NOT NULL, link VARCHAR(128) NOT NULL, description VARCHAR(128), user_id INTEGER, FOREIGN KEY(user_id) REFERENCES user(id))");
            
//            statement.executeUpdate("insert into user values('leo','1234','wow@hotmail.com')");
//            ResultSet resultSet = statement.executeQuery("select * from user");
//            
//            while (resultSet.next()){
//                
//            //read the result set
//           
//            System.out.println("name = " + resultSet.getString("name"));
//            System.out.println("password = " + resultSet.getString("password"));
//            System.out.println("email = " + resultSet.getString("email"));
            
         //}
            connection.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteManager.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        
    
    
    }
    
    
    
    //Método para crear y devolver la conexión controlando SQL Exception.
    public Connection getConnection(){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+ url);
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;

    }
    //Método para cerrar la conexión controlando SQL Exception.
    public void close(){
        try{
            connection.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}





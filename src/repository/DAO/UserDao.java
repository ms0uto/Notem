/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.DAO;

import domain.entities.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import repository.SQLiteManager;

/**
 *
 * @author EXTmsouto
 */
public class UserDao {

    public User findByID(int id) { //TESTEAR
        User user = null;
        try {
            Connection con = SQLiteManager.getInstance().getConnection();
            String findByIDQuery = "SELECT * FROM user WHERE id =" + id;
            ResultSet resultset = con.createStatement().executeQuery(findByIDQuery);
            if (resultset.next()) {
                user = User.createUserFactory(
                        resultset.getString("username"),
                        resultset.getString("password"),
                        resultset.getString("email"));
                resultset.close();
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;

    }

    public void insertUser(User user) {
        try {
            String insertUserQuery = "insert into user (username, password, email) values('" + user.getName() + "', '" + user.getPass() + "', '" + user.getEmail() + "')";
            SQLiteManager sm = SQLiteManager.getInstance();
            Connection connection = sm.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(insertUserQuery);

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getID(User user) {
        int id= 0;
        try {
            Connection con = SQLiteManager.getInstance().getConnection();
            String getIDQuery = "SELECT id FROM user WHERE username ='" + user.getName() +"'";
            ResultSet resultset = con.createStatement().executeQuery(getIDQuery);
            id = resultset.getInt("id");
            
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
         return id;
    }

    public User findUserByName(String username, String password) {
        User user = null;
        try {
            Connection con = SQLiteManager.getInstance().getConnection();
            String findUserByNameQuery = "SELECT * FROM user WHERE username ='" + username + "' AND password='" + password + "'";
            ResultSet resultset = con.createStatement().executeQuery(findUserByNameQuery);

            if (resultset.next()) {

                user = User.createUserFactory(
                        resultset.getString("username"),
                        resultset.getString("password"),
                        resultset.getString("email"));
                resultset.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return user;
    }

    public boolean deleteUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

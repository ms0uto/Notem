/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domain.entities.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import testDB.SQLiteManager;

/**
 *
 * @author EXTmsouto
 */
public class UserDaoImpl implements UserDao {

    @Override
    public User findByName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void insertUser(User user) {
        try {
            String insertUserQuery = "insert into user (username, password, email) values('" + user.getName() + "', '" + user.getPass() + "', '" + user.getEmail() + "')";
            SQLiteManager sm = SQLiteManager.getInstance();
            Connection connection = sm.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(insertUserQuery);
            

        } catch (SQLException ex) {
            Logger.getLogger(UserDaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean deleteUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

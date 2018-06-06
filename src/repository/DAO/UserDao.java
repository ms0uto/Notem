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

    public User findByID() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public User findUserByName(String username, String password) {
        User user = null;
        try {
            Connection con = SQLiteManager.getInstance().getConnection();
            String loginCheckQuery = "SELECT id FROM user WHERE username ='" + username + "' AND password='" + password + "'";
            ResultSet resultset = con.createStatement().executeQuery(loginCheckQuery);
            if (resultset.next()) {
                String email = resultset.getString("email");
                user = User.createUserFactory(username, password, email);
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.DAO;

import domain.entities.Feed;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import repository.SQLiteManager;

/**
 *
 * @author EXTmsouto
 */
public class FeedDao {

    public void insertFeed(int user_id, Feed feed) {
        try {

            String insertFeedQuery = "insert into feed (title, link, description,user_id) values('" + feed.getTitle() + "', '" + feed.getLink() + "', '" + feed.getDescription() + "', '" + user_id + "')";
            SQLiteManager sm = SQLiteManager.getInstance();
            Connection connection = sm.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(insertFeedQuery);

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Feed> getFeedList(int user_id) {
        List<Feed> feedList = null;
        try {
            Connection con = SQLiteManager.getInstance().getConnection();
            String getFeedListQuery = "SELECT * FROM feed WHERE user_id =" + user_id;
            ResultSet resultset = con.createStatement().executeQuery(getFeedListQuery);
            while (resultset.next()) {
                feedList.add(Feed.createFeedFactory(
                        resultset.getString("title"),
                        resultset.getString("link"),
                        resultset.getString("description")));

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return feedList;
    }

    public boolean deleteUser(Feed feed) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
//title VARCHAR(64) NOT NULL, link VARCHAR(128) NOT NULL, description VARCHAR(128)

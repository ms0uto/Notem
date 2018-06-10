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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import repository.SQLiteManager;

/**
 *
 * @author EXTmsouto
 */
public class FeedDao {

    private Connection connection;
    private ResultSet resultset;
    private Statement statement;

    public void insertFeed(int user_id, Feed feed) {
        try {

            String insertFeedQuery = "insert into feed (title, link, description,user_id) "
                    + "values('" + feed.getTitle() + "', '" + feed.getLink() + "', '" + feed.getDescription() + "', " + user_id + ")";

            connection = SQLiteManager.getInstance().getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(insertFeedQuery);

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
            }
        }

    }

    public List<Feed> getFeedList(int user_id) {
        List<Feed> feedList = new ArrayList<>();
        try {
            connection = SQLiteManager.getInstance().getConnection();
            String getFeedListQuery = "SELECT * FROM feed WHERE user_id =" + user_id;
            resultset = connection.createStatement().executeQuery(getFeedListQuery);
            while (resultset.next()) {
                feedList.add(Feed.createFeedFactory(
                        resultset.getString("title"),
                        resultset.getString("link"),
                        resultset.getString("description")));
                // TEST QUITAR:
                System.out.print(resultset.getString("link"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                resultset.close();
                connection.close();
            } catch (SQLException e) {
            }
        }
        return feedList;
    }

    public boolean deleteUser(Feed feed) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

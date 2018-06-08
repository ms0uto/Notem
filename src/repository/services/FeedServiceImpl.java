/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.services;

import domain.boundaries.FeedService;
import domain.entities.Feed;
import java.util.List;
import repository.DAO.FeedDao;

/**
 *
 * @author EXTmsouto
 */
public class FeedServiceImpl implements FeedService {

    private FeedDao feedDao;

    @Override
    public void insertFeed(int user_id, Feed feed) {
        feedDao = new FeedDao();
        feedDao.insertFeed(user_id, feed);
    }

    @Override
    public List<Feed> getFeedList(int user_id) {
        feedDao = new FeedDao();
        return feedDao.getFeedList(user_id);
    }

    @Override
    public void deleteFeed(int user_id, Feed feed) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

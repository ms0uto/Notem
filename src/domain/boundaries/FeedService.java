/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.boundaries;

import domain.entities.Feed;
import domain.entities.User;
import java.util.List;

/**
 *
 * @author EXTmsouto
 */
public interface FeedService {
    void insertFeed(User user, String url);
    List<Feed> getFeedsList(User user);
    void deleteFeed(User user,String url);
    
}

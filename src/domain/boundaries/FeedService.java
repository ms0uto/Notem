/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.boundaries;

import domain.entities.Feed;
import java.util.List;

/**
 *
 * @author EXTmsouto
 */
public interface FeedService {
    void insertFeed(int user_id, Feed feed);
    List<Feed> getFeedList(int user_id);
    void deleteFeed(int user_id,Feed feed);
    
    
}

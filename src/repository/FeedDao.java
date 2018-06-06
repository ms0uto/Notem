/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domain.entities.Feed;
import domain.entities.User;

/**
 *
 * @author EXTmsouto
 */
public interface FeedDao {
    boolean inserFeed(Feed feed, User user);
    boolean deleteUser(Feed feed);
}

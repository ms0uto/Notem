/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domain.entities.FeedMessage;

/**
 *
 * @author EXTmsouto
 */
public interface FeedMessageDao {
    boolean insertMessage(FeedMessage feedmessage);
    boolean deleteMessage(FeedMessage feedmessage);
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.boundaries;

import domain.entities.User;

/**
 *
 * @author EXTmsouto
 */
public interface UserService {
    void insertUser(String userName, String password, String email);
    User findUserByName(String username, String password);
    int getID(User user);
    
}

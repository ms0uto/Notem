/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domain.entities.User;

/**
 *
 * @author EXTmsouto
 */
public interface UserDao {
    User findByName();
    void insertUser(User user);
    boolean deleteUser(User user);
    
}

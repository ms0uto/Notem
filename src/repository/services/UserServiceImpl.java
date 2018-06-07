/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.services;

import domain.boundaries.UserService;
import domain.entities.User;
import repository.DAO.UserDao;

/**
 *
 * @author EXTmsouto
 */
public class UserServiceImpl implements UserService {
    
    @Override
    public void insertUser(String userName, String password, String email)  {
        UserDao userDao = new UserDao();
        userDao.insertUser(User.createUserFactory(userName, password, email));
    }
    
    @Override
    public User findUserByName(String username, String password) {
        UserDao userDao = new UserDao();
        return userDao.findUserByName(username, password);
    }
}

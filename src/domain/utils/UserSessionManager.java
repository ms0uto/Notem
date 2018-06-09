/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.utils;

import domain.entities.User;
import repository.services.UserServiceImpl;

/**
 *
 * @author EXTmsouto
 */
public class UserSessionManager {

    UserServiceImpl userService;

    private static UserSessionManager sharedInstance;

    private User loggedUser;

    public static UserSessionManager sharedInstance() {
        if (sharedInstance == null) {
            sharedInstance = new UserSessionManager();

        }
        return sharedInstance;
    }

    private UserSessionManager() {
        loggedUser = null;
    }

    public void userLogin(User user) {
        loggedUser = user;
    }

    public void userLogout() {
        loggedUser = null;
    }

    public boolean isUserLogged() {
        return loggedUser != null;
    }

    public User getUser() {
        return loggedUser;
    }

    public int getLoggedUserID() {
        userService = new UserServiceImpl();
        return userService.getID(loggedUser);
    }
}

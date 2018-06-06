/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.entities;

import java.util.List;

/**
 *
 * @author EXTmsouto
 */
public class User {

    public static User createUserFactory(String name, String pass, String email) {
        return new User(name, pass, email);
    }

    private String name;
    private String pass;
    private String email;
    private List<Feed> feedList;

    
    private User(String name, String pass, String email) {
        this.name = name;
        this.pass = pass;
        this.email = email;
    }
    
    public List<Feed> getFeedList() {
        return feedList;
    }

    public void setFeedList(List<Feed> feedList) {
        this.feedList = feedList;
    }
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
    
}

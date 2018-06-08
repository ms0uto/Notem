/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author EXTmsouto
 */
public class Feed {

    public static Feed createFeedFactory(String title, String link, String description) {
        return new Feed(title, link, description);
    }

    final String title;
    final String link;
    final String description;

    final List<FeedMessage> entries = new ArrayList<>();

    private Feed(String title, String link, String description) {
        this.title = title;
        this.link = link;
        this.description = description;
    }

    public List<FeedMessage> getMessages() {
        return entries;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return (title + "\n" + " , " + description);
    }

}

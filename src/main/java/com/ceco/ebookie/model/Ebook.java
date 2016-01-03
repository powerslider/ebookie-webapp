package com.ceco.ebookie.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Model class representing an ebook containing chapters and table of contents.
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 02-Jan-2016
 */
@Entity
@Table(name = "ebook")
public class Ebook {

    private Integer id;

    private String title;

    private String subtitle;

    private String description;

    private User author;

    private String tableOfContents;

    private Set<Chapter> chapters;

    private Set<User> userLikes;


    public Ebook() {
        this.chapters = new HashSet<>();
        this.userLikes = new HashSet<>();
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ebook_id")
    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "title", nullable = false, length = 150)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "subtitle", nullable = false, length = 200)
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @Column(name = "description", length = 500)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "table_of_contents")
    @Type(type = "text")
    public String getTableOfContents() {
        return tableOfContents;
    }

    public void setTableOfContents(String tableOfContents) {
        this.tableOfContents = tableOfContents;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @OneToMany(mappedBy = "ebook", cascade=CascadeType.ALL)
    public Set<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(Set<Chapter> chapters) {
        this.chapters = chapters;
    }

    public void addNewChapter(Chapter newChapter) {
        this.chapters.add(newChapter);
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "favouriteEbooks")
    public Set<User> getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(Set<User> userLikes) {
        this.userLikes = userLikes;
    }

    public void addNewUserLike(User newUser) {
        this.userLikes.add(newUser);
    }
}

package com.ceco.ebookie.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Model class representing a basic user of the system that can also be an author of new ebooks.
 *
 * @author Tsvetan Dimitrov <tsvetan.dimitrov23@gmail.com>
 * @since 02-Jan-2016
 */
@Entity
@Table(name = "user")
public class User {

    private Integer id;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String email;

    private Set<Ebook> authoredEbooks;

    private Set<Ebook> favouriteEbooks;


    public User() {
        this.authoredEbooks = new HashSet<>();
        this.favouriteEbooks = new HashSet<>();
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "email", nullable = false, unique = true, length = 100)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password", nullable = false, unique = true, length = 50)
    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "first_name", nullable = false, length = 50)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name", nullable = false, length = 50)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "username", nullable = false, unique = true, length = 50)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author", cascade = CascadeType.ALL)
    public Set<Ebook> getAuthoredEbooks() {
        return authoredEbooks;
    }

    public void setAuthoredEbooks(Set<Ebook> authoredEbooks) {
        this.authoredEbooks = authoredEbooks;
    }

    public void addNewBook(Ebook newBook) {
        this.authoredEbooks.add(newBook);
    }

//    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_ebook", joinColumns = {
            @JoinColumn(name = "user_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "ebook_id",
                    nullable = false, updatable = false) })
    public Set<Ebook> getFavouriteEbooks() {
        return favouriteEbooks;
    }

    public void setFavouriteEbooks(Set<Ebook> favouriteEbooks) {
        this.favouriteEbooks = favouriteEbooks;
    }

    public void addFavouriteBook(Ebook newFavouriteBook) {
        this.favouriteEbooks.add(newFavouriteBook);
    }
}

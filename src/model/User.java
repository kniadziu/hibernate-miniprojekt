package model;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

//CREATE TABLE users (id INTEGER PRIMARY KEY, name TEXT, date TEXT, album_id BIGINT REFERENCES albums (id));
//CREATE TABLE users (id INTEGER PRIMARY KEY, username TEXT, joinDate TEXT);
//INSERT INTO users (id, username, joinDate) VALUES (1, "Kowalski", "2020-04-01");
//INSERT INTO users (id, username, joinDate) VALUES (2, "Koziel", "2020-04-20");

@Entity
@Table(name = "users")
public class User implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String username;

    @Column
    private String joinDate;



    @ManyToMany(
            mappedBy="usersLikes",
            cascade={CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Photo> photosLiked = new HashSet<Photo>();


    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="user_id")
    private Set<Album> albums = new HashSet<Album>();


    //KONSTRUKTOR

    public User() {
    }

    public User(String username, String joinDate) {
        this.username = username;
        this.joinDate = joinDate;
    }

    //GETTER AND SETTER

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }


    public void addLikeForPhoto(Photo photo) {
        photosLiked.add(photo);
    }


    public void removeLikeFromPhoto(Photo photo) {
        photosLiked.remove(photo);
    }


    //ALBMUMS


    public void addLikeForAlbum(Album album) {
        albums.add(album);
    }

    public void removeLikeForAlbum(Album album) {
        albums.remove(album);
    }
}

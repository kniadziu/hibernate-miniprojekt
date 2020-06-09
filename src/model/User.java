package model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    @Column(name="user_id")
    private long id;

    @Column
    private String username;

    @Column
    private String joinDate;



    @ManyToMany(fetch = FetchType.LAZY,
            cascade={CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REFRESH})
    @JoinTable(name="user_photo",
                joinColumns = @JoinColumn(name="user_id"),
                inverseJoinColumns = @JoinColumn(name="photo_id"))
    private List<Photo> photosLiked;// = new HashSet<Photo>();


    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="user_id")
    private Set<Album> albums = new HashSet<Album>();


    // uzutkownik moze polubic uzytkownika
  //  @JsonIgnore
   // @ManyToMany(cascade={CascadeType.ALL})
    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name="user1_user2",
            joinColumns=@JoinColumn(name="user1_id"),
            inverseJoinColumns=@JoinColumn(name="user2_id")
    )
    private Set<User> userFriends = new HashSet<>();

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
        if (photo==null){
            photosLiked=new ArrayList<>();
        }
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

    //user Likes another User


    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public Set<User> getUserFriends() {
        return userFriends;
    }

    public void setUserFriends(Set<User> userFriends) {
        this.userFriends = userFriends;
    }


    public void addFriend(User friend) {
        userFriends.add(friend);
    }

    public void removeFriend(User friend) {
        userFriends.remove(friend);
    }

    public List<Photo> getPhotosLiked() {
        return photosLiked;
    }

    public void setPhotosLiked(List<Photo> photosLiked) {
        this.photosLiked = photosLiked;
    }
}

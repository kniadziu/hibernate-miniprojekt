package model;

import javax.persistence.*;
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
    private long id;ś

    @Column
    private String username;

    @Column
    private String joinDate;

    //tworzy tablice lacznikowa "photos_users"
    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="photos_users", joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="photo_id")
    )

    Set<Photo> photos = new HashSet<Photo>();

    @ManyToMany(mappedBy="photos", cascade={CascadeType.PERSIST, CascadeType.MERGE})
    private Set<User> users = new HashSet<User>();



    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="user_id")
    private Set<Album> albums = new HashSet<Album>();


    //GETTER AND SETTER


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    //PHOTOS

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public void addPhoto(Photo photo) {
        photos.add(photo);
    }


    public void removePhoto(Photo photo) {
        photos.remove(photo);
    }
    //ALBMUMS
    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        this.albums = albums;
    }

    public void addAlbum(Album album) {
        albums.add(album);
    }

    public void rmoveAlbum(Album album) {
        albums.remove(album);
    }
}

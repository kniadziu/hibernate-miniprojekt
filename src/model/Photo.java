package model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//CREATE TABLE photos (id INTEGER PRIMARY KEY, name TEXT, date TEXT, album_id BIGINT REFERENCES albums (id));
//        INSERT INTO photos (id, name, date, album_id ) VALUES (1, "Gory noca", "2020-05-01", 1);
//        INSERT INTO photos (id, name, date, album_id ) VALUES (2, "krajobraz jezior", "2020-05-12", 2);

@Entity
@Table(name="photos")
public class Photo implements java.io.Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column
    private String date;



    //tworzy tablice lacznikowa "photos_users"
    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
              name="photos_users",
              joinColumns=@JoinColumn(name="photo_id"),
              inverseJoinColumns=@JoinColumn(name="user_id")
    )
    private Set<User> usersLikes = new HashSet<User>();
   // @ManyToMany(mappedBy="users", cascade={CascadeType.PERSIST, CascadeType.MERGE})

    public void addLike(User user) {
        usersLikes.add(user);
    }

    public void removeLike(User user) {
        usersLikes.remove(user);
    }



    //KONSTRUKTOR

    public Photo() {
    }

    public Photo(String name, String date) {
        this.name = name;
        this.date = date;
    }

//GETTER AND SETTER


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public Set<User> getUsersLikes() {
        return usersLikes;
    }

    public void setUsersLikes(Set<User> usersLikes) {
        this.usersLikes = usersLikes;
    }
}
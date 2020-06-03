package model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//CREATE TABLE users (id INTEGER PRIMARY KEY, username TEXT, joinDate TEXT);
//INSERT INTO users (id, usernme, joinDate) VALUES (1, 'Kowalski', '2020-05-01');
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

    //tworzy tablice lacznikowa "photos_users"
    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="photos_users", joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="photo_id")
    )

    Set<Photo> photos = new HashSet<Photo>();

    @ManyToMany(mappedBy="photos", cascade={CascadeType.PERSIST, CascadeType.MERGE})
    private Set<User> users = new HashSet<User>();
}

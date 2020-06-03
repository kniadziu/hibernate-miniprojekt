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

    private Set<User> users = new HashSet<User>();

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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }


    //TO STRING


    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", users=" + users +
                '}';
    }
}
package model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//CREATE TABLE albums (id INTEGER PRIMARY KEY, name TEXT, description TEXT);
//INSERT INTO albums (id, name, description) VALUES (1, 'Przyroda, 'Kwiaty');
//INSERT INTO albums (id, name, description) VALUES (2, 'Muzyka, 'Koncert');
@Entity
@Table(name="albums")
public class Album implements java.io.Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="album_id")
    private Set<Photo> photos = new HashSet<Photo>();
}

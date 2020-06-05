package model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//CREATE TABLE albums (id INTEGER PRIMARY KEY, name TEXT, description TEXT);
//INSERT INTO albums (id, name, description) VALUES (1, "Przyroda", "Kwiaty");
//INSERT INTO albums (id, name, description) VALUES (2, "Muzyka", "Koncert");
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

   // KONSTRUKTOR

    public Album() {
    }

    public Album(String name, String description) {
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

// to STRING


    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", photos=" + photos +
                '}';
    }
}

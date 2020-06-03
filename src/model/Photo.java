package model;

import javax.persistence.*;

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

}
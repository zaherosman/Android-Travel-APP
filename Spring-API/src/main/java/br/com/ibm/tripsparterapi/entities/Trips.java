package br.com.ibm.tripsparterapi.entities;

import javax.persistence.*;

//criaçao e orientaçao das tabelas
@Entity
@Table(name = "trip")
public class Trips {

    //gerar de forma automatica os IDs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tripId;
    private String name, img, country;


    //constructor
    public Trips() {}

    public Trips(Integer tripId, String name, String img, String country) {
        this.tripId = tripId;
        this.name = name;
        this.img = img;
        this.country = country;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

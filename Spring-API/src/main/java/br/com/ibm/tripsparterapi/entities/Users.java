package br.com.ibm.tripsparterapi.entities;

import javax.persistence.*;

//criaçao e orientaçao da tabela
@Entity
@Table(name = "Users")
public class Users {

    //gerar de forma automatica os IDs
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_user;
    private String name, email, pass;

    //constructor
    public Users() {}

    public Users(Integer id_user, String name, String email, String pass) {
        this.id_user = id_user;
        this.name = name;
        this.email = email;
        this.pass = pass;
    }

    //get && set


    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

}

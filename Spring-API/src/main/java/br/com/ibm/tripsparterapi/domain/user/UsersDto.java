package br.com.ibm.tripsparterapi.domain.user;


public class UsersDto {
    private Integer id_user;
    private String name, email, pass;
    //constructor
    public UsersDto(Integer id_user, String name, String email, String pass) {
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

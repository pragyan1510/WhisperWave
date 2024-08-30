package com.example.whisperwave.Models;

public class Users {
    String profilepic, username , password, mail, userid, lastmesg, status;

    public Users(String profilepic, String username, String password, String mail, String userid, String lastmesg, String status) {
        this.profilepic = profilepic;
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.userid = userid;
        this.lastmesg = lastmesg;
        this.status = status;
    }

    public Users(){}

    //signup constructor
    public Users( String username, String password, String mail) {
        this.username = username;
        this.password = password;
        this.mail = mail;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }



    public String getLastmesg() {
        return lastmesg;
    }

    public void setLastmesg(String lastmesg) {
        this.lastmesg = lastmesg;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}

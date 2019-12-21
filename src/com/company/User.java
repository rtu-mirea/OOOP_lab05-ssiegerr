package com.company;
import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String login;
    private String password;

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public boolean enter(String login, String password) {
        return this.login.equals(login) && this.password.equals(password);
    }

    public String getName() {
        return this.name;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setName(String name) {
        if (!this.name.equals(name)) {
            this.name = name;
        }
    }

    public void setLogin(String login) {
        if (!this.login.equals(login)) {
            this.login = login;
        }
    }

    public void setPassword(String password) {
        if (!this.password.equals(password)) {
            this.password = password;
        }
    }
}

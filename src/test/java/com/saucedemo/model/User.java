package com.saucedemo.model;

/*
  Modelo simple de usuario (username/password).
  Si ya tienes esta clase en tu project, ignora este archivo.
*/
public class User {
    private final String username;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public static User standardUser() {
        return new User("standard_user", "secret_sauce");
    }
}

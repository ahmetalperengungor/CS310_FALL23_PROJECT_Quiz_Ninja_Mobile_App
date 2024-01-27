package com.example.quizninjafrontend;

import java.io.Serializable;

public class comment implements Serializable {
    String username;
    String text;

    public comment() {
    }

    public comment(String username, String text) {
        this.username = username;
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

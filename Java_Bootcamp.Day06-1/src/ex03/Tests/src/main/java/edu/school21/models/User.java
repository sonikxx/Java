package edu.school21.models;

import com.google.common.base.Objects;

public class User {
    private long identifier;
    private String login;
    private String password;
    private boolean authenticationStatus;

    public User(long identifier, String login, String password, boolean authenticationStatus) {
        this.identifier = identifier;
        this.login = login;
        this.password = password;
        this.authenticationStatus = authenticationStatus;
    }

    public long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(long identifier) {
        this.identifier = identifier;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(boolean authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        User user = (User) obj;
        return this.identifier == user.identifier;
    }

    @Override
    public int hashCode() {
        return  Objects.hashCode(identifier, login, password, authenticationStatus);
    }

    @Override
    public String toString() {
        return "User: id=" + identifier + ", login=" + login + ", password=" + password + ", authenticationStatus=" + authenticationStatus;
    }
}

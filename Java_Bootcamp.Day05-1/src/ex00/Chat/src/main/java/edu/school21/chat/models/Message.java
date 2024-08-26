package edu.school21.chat.models;

import java.sql.Timestamp;
import java.util.Objects;

public class Message {
    private Long id;
    private User author;
    private Chatroom room;
    private String text;
    private Timestamp date;

    public Message(Long id, User author, Chatroom room, String text, Timestamp date) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Chatroom getChatRoom() {
        return room;
    }

    public void setChatRoom(Chatroom room) {
        this.room = room;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Message message = (Message) obj;
        return id.equals(message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, room, text, date);
    }

    @Override
    public String toString() {
        return "Message: id=" + id + ", author=" + author.getId() + ", room=" + room.getId() + ", text=" + text + ", date=" + date;
    }

}

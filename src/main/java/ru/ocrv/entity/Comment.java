package ru.ocrv.entity;

import javax.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long num;
    @Column
    private String text;
    @ManyToOne(cascade = CascadeType.ALL)
    private Request request;

    public Comment() {
    }

    public Comment(String text) {
        this.text = text;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "num=" + num +
                ", text='" + text + '\'' +
                ", request=" + request +
                '}';
    }
}

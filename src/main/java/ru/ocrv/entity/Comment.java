package ru.ocrv.entity;

import javax.persistence.*;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
public class Comment {

    private static final AtomicInteger count = new AtomicInteger(0);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String text;
    @ManyToOne(cascade = CascadeType.ALL)
    private Request request;

    public Comment() {
    }

    public Comment(String text) {
        this.text = text;
        this.id = count.incrementAndGet();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
                "id=" + id +
                ", text='" + text + '\'' +
                ", request=" + request +
                '}';
    }
}

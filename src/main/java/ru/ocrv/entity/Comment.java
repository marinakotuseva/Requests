package ru.ocrv.entity;

import javax.persistence.*;

@Entity
public class Comment {
    //private long numCounter = 0;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long num;
    @Column
    private String text;
    @ManyToOne(cascade = CascadeType.ALL)
    private Request request;

    public Comment(String text) {
        //this.num = ++numCounter;
        this.text = text;
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

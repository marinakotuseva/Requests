package ru.ocrv.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Request {
    //private long numCounter = 0;
    @Id
    @GeneratedValue
    private long num;
    @Column
    private String description;
    @Column
    private Status status;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "request")
    private Set<Comment> comments;

    public Request() {
    }

    public Request(String description){
        //this.num = ++numCounter;
        this.description = description;
        this.status = Status.NEW;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment){
        if (comments == null){
            comments = new HashSet<Comment>();
        }
        comments.add(comment);
    }

    @Override
    public String toString() {
        return "Request{" +
                "num=" + num +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", comments=" + comments.toString() +
                '}';
    }
}

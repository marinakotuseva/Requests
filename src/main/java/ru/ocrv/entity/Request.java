package ru.ocrv.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Table(name="requests")
public class Request {

    private static final AtomicInteger count = new AtomicInteger(4);
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String description;
    @Column
    private Status status;
    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "request")
    private Set<Comment> comments;

    public Request() {
    }

    public Request(String description){
        this.description = description;
        this.status = Status.NEW;
        id = count.incrementAndGet();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        String comments;
        if (this.comments == null){
            comments = "";
        } else {
            comments = this.comments.toString();
        }
        return "Request{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", comments=" + comments +
                '}';
    }
}

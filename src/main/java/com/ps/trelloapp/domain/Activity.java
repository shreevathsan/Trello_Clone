package com.ps.trelloapp.domain;

import javax.persistence.*;


@Entity
@Table(name="activity")
public class Activity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name="comment")
    private String comment;


    @Column(name="date")
    private String date;


    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="task_id")
    private Task task;

    public Activity() {
    }

    public Activity(String comment, String date, User userId, Task taskId) {
        this.comment = comment;
        this.date = date;
        this.user = userId;
        this.task = taskId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}

package com.ps.domain;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
@Table(name="activity")
public class Activity {
    @Expose
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Expose
    @Column(name="comment")
    private String comment;

    @Expose
    @Column(name="date")
    private String date;

    @Expose
    @ManyToOne
    @JoinColumn(name="user_id")
    private User userId;

    @ManyToOne
    @JoinColumn(name="task_id")
    private Task taskId;

    public Activity() {
    }

    public Activity(String comment, String date, User userId, Task taskId) {
        this.comment = comment;
        this.date = date;
        this.userId = userId;
        this.taskId = taskId;
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

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Task getTaskId() {
        return taskId;
    }

    public void setTaskId(Task taskId) {
        this.taskId = taskId;
    }
}

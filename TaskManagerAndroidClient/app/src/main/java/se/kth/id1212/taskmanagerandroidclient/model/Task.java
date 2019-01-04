package se.kth.id1212.taskmanagerandroidclient.model;


import java.io.Serializable;

/**
 * Represent a task object that is sent and received to and from the API rest calls.
 */
public class Task implements Serializable {

    private Long id;
    private String title;
    private String description;
    private Boolean doNow = false;
    private String addedDate;
    private String dueDate;
    private Boolean isDone;


    public Task(String title, String description, String addedDate, String dueDate) {
        this.title = title;
        this.description = description;
        //this.doNow = doNow;
        this.addedDate = addedDate;
        this.dueDate = dueDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDoNow() {
        return doNow;
    }

    public void setDoNow(Boolean doNow) {
        this.doNow = doNow;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }
}

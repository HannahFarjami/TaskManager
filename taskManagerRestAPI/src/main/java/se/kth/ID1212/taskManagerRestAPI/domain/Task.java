package se.kth.ID1212.taskManagerRestAPI.domain;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;


/**
 * Represent a table called task in a relationship database, created by JPA.
 */
@Entity
public class Task {

    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String title;
    private String description;
    private Boolean doNow;
    private LocalDate addedDate;
    private LocalDate dueDate;
    private Boolean isDone;
    private Long userId;

    protected Task() {
    }

    public Task(String title, String description, LocalDate addedDate, Boolean isDone, Boolean doNow, LocalDate dueDate,Long userId) {
        this.title = title;
        this.description = description;
        this.addedDate = addedDate;
        this.isDone = false;
        this.doNow = doNow;
        this.dueDate = dueDate;
        this.userId = userId;
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

    public LocalDate getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(LocalDate addedDate) {
        this.addedDate = addedDate;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public Boolean getDoNow() {
        return doNow;
    }

    public void setDoNow(Boolean doNow) {
        this.doNow = doNow;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

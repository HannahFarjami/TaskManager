package se.kth.ID1212.taskManagerRestAPI.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Priority implements Serializable {
    private boolean doNow;
    private LocalDate dueDate;
    static DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Priority(boolean doNow, String dueDate) {
        this.doNow = doNow;
        if(!doNow) this.dueDate = LocalDate.parse(dueDate,formatter);
    }

    public boolean isDoNow() {
        return doNow;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDoNow(boolean doNow) {
        this.doNow = doNow;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}

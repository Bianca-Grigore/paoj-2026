package com.pao.laboratory03.bonus;

public class Task {
    private String title;
    private Status status;
    private Priority priority;
    private String assignee;
    private static int nextId = 1;
    private String id;

    public Task(String title, Priority priority) {
        this.id = String.format("T%03d", nextId++);
        this.title = title;
        this.status = Status.TODO;
        this.priority = priority;
        this.assignee = null;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString(){
        return String.format("Task{id= %s, title '%s', priority= %s, status= %s, assignee= %s)}", id, title, priority.name(), status.name(), assignee);
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status newStatus) {
        this.status = newStatus;
    }

    public String getAssignee() {
        return assignee;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getTitle() {
        return title;
    }
}

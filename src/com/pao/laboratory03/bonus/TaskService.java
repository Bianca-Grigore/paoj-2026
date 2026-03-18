package com.pao.laboratory03.bonus;

import java.util.*;

public class TaskService {
    private Map<String, Task> tasksById;
    private Map<Priority, List<Task>> tasksByPriority;
    private List<String> auditLog;
    private static TaskService instance;

    private TaskService(){
        tasksById = new HashMap<>();
        tasksByPriority = new EnumMap<>(Priority.class);
        auditLog = new ArrayList<>();

        for(Priority p: Priority.values() ){
            tasksByPriority.put(p, new ArrayList<>());
            }
        }

    public static TaskService getInstance(){
        if(instance == null){
            instance = new TaskService();
        }
        return instance;
    }

    public Task addTask(String title, Priority priority){
        Task task = new Task(title, priority);
        tasksById.put(task.getId(), task);
        tasksByPriority.get(priority).add(task);
        String mes = String.format("[ADD] %s: '%s' (%s)", task.getId(), title, priority.name());
        auditLog.add(mes);
        return task;
    }

    public void assignTask(String taskId, String assignee){
        if(!tasksById.containsKey(taskId)){
            throw new TaskNotFoundException("Task-ul cu id-ul " + taskId + " nu a fost gasit.");
        }
        Task task = tasksById.get(taskId);
        task.setAssignee(assignee);

        String mes = String.format("[ASSIGN] %s -> %s", taskId, assignee);
        auditLog.add(mes);
    }

    public void changeStatus(String taskId, Status newStatus){
        if(!tasksById.containsKey(taskId)){
            throw new TaskNotFoundException("Task-ul cu id-ul " + taskId + " nu a fost gasit.");
        }
        Task task = tasksById.get(taskId);
        Status status_curent = task.getStatus();

        if(!status_curent.canTransitionTo(newStatus)){
            throw new InvalidTransitionException(status_curent, newStatus);
        }

        task.setStatus(newStatus);
        String mesaj = String.format("[STATUS] %s: %s -> %s", taskId, status_curent, newStatus);
        auditLog.add(mesaj);
    }

    public List<Task> getTasksByPriority(Priority priority){
        if(priority == null){
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(tasksByPriority.get(priority));
    }

    public Map<Status, Long> getStatusSummary(){
        Map<Status, Long> summary = new HashMap<>();

        for (Status s : Status.values()) {
            long count = tasksById.values().stream()
                    .filter(t -> t.getStatus() == s).count();
            summary.put(s, count);
        }
        return summary;
    }

    public List<Task> getUnassignedTasks(){
        List<Task> unassigned = new ArrayList<>();
        for(Task t: tasksById.values()){
            if(t.getAssignee() == null){
                unassigned.add(t);
            }
        }
        return unassigned;
    }

    public void printAuditLog(){
        if(auditLog.isEmpty()){
            System.out.println("Jurnalul este gol.");
            return;
        }

        for(String s: auditLog){
            System.out.println(s);
        }
    }

    public double getTotalUrgencyScore(int baseDays){
        double suma = 0.0;
        for(Task t: tasksById.values()){
            if(t.getStatus() != Status.DONE && t.getStatus() != Status.CANCELLED){
                suma += t.getPriority().calculateScore(baseDays);
        }
    }
    return suma;
    }
}
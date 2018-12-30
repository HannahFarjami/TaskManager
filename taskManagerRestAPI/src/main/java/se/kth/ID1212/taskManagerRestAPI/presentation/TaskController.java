package se.kth.ID1212.taskManagerRestAPI.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.kth.ID1212.taskManagerRestAPI.application.TaskService;
import se.kth.ID1212.taskManagerRestAPI.domain.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/task")
    public Task createTask(@RequestBody Map <String, String> body) {
        return taskService.createTask(body);
    }



    @GetMapping("/task/{id}")
    public Task viewTask(@PathVariable Long id){
        Task task = null;
        try {
            task = taskService.getTask(id);
        }catch (Exception e){}
        return task;
    }

    @GetMapping("/tasks")
    public List<Task> listAllTasks(@RequestParam(required = false)LocalDate startDate,
                                   @RequestParam(required = false)LocalDate endDate,
                                   @RequestParam(required = false)Boolean isDone){
        return taskService.listTasks(startDate,endDate,isDone);
    }

    @DeleteMapping("/task/{id}")
    public void deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
    }

    @PutMapping("/task")
    public Task updateTask(@RequestBody Task task){
        return taskService.updateTask(task);

    }

    @PutMapping("/task/{id}")
    public Task setTaskAsDone(@PathVariable Long id){
        Task task = null;
        try {
            task = taskService.setTaskAsDone(id);
        }catch (Exception e){}
        return task;
    }


/*
    @GetMapping("/tasks/today")
    public List<Task> listTaskToday(){
        return taskService.listTaskToday();
    }

    @GetMapping("/tasks/upcoming")
    public List<Task> listTaskUpcoming(){
        return taskService.listTaskUpcoming();
    }*/


}

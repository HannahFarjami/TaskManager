package se.kth.ID1212.taskManagerRestAPI.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.kth.ID1212.taskManagerRestAPI.application.TaskService;
import se.kth.ID1212.taskManagerRestAPI.domain.Task;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/task")
    public ResponseEntity<?> createTask(@RequestBody Map <String, String> body) throws URISyntaxException {
        Task task = taskService.createTask(body);
        if (task==null) {
            return new ResponseEntity<>("No title given, add a title", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.created(new URI("/task/"+task.getId())).body(task);
    }

    @PutMapping("/task")
    public ResponseEntity<?> updateTask(@RequestBody Task task){
        Task updatedTask = taskService.updateTask(task);
        if (updatedTask==null) {
            return new ResponseEntity<>("No title given, add a title", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok().body(task);
    }
    
    @PutMapping("/task/{id}")
    public ResponseEntity<?> setTaskAsDone(@PathVariable Long id) throws URISyntaxException {
        Task task = null;
        try {
            task = taskService.setTaskAsDone(id);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(task);
    }


    @GetMapping("/task/{id}")
    public ResponseEntity<?> viewTask(@PathVariable Long id){
        Task task = null;
        try {
            task = taskService.getTask(id);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
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

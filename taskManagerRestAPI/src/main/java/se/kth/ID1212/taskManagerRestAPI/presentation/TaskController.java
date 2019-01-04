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


/**
 * Class that is responsible to receive REST api(http) requests for tasks, and to call the right service in the application layer
 * for processing the request. Implements error handling.
 */
@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/task")
    public ResponseEntity<?> createTask(@RequestHeader(value="Authorization") String authString,@RequestBody Map <String, String> body) throws URISyntaxException {
        Task task = taskService.createTask(authString,body);
        if (task==null) {
            return new ResponseEntity<>("No title given, add a title", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.created(new URI("/task/"+task.getId())).body(task);
    }

    @PutMapping("/task")
    public ResponseEntity<?> updateTask(@RequestHeader(value="Authorization") String authString,@RequestBody Task task){
        Task updatedTask = taskService.updateTask(authString,task);
        if (updatedTask==null) {
            return new ResponseEntity<>("No title given, add a title", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok().body(task);
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<?> setTaskAsDone(@RequestHeader(value="Authorization") String authString,@PathVariable Long id) throws URISyntaxException {
        Task task = null;
        try {
            task = taskService.setTaskAsDone(authString,id);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(task);
    }


    @GetMapping("/task/{id}")
    public ResponseEntity<?> viewTask(@RequestHeader(value="Authorization") String authString,@PathVariable Long id){
        Task task = null;
        try {
            task = taskService.getTask(authString,id);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/tasks")
    public List<Task> listAllTasks(@RequestHeader(value="Authorization") String authString,
                                   @RequestParam(required = false)LocalDate startDate,
                                   @RequestParam(required = false)LocalDate endDate,
                                   @RequestParam(required = false)Boolean isDone){
        return taskService.listTasks(authString,startDate,endDate,isDone);
    }

    @DeleteMapping("/task/{id}")
    public void deleteTask(@RequestHeader(value="Authorization") String authString,@PathVariable Long id){
        taskService.deleteTask(authString,id);
    }
}

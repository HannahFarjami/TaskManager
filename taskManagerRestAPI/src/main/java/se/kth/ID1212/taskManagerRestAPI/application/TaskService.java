package se.kth.ID1212.taskManagerRestAPI.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import se.kth.ID1212.taskManagerRestAPI.domain.Task;

import se.kth.ID1212.taskManagerRestAPI.domain.User;
import se.kth.ID1212.taskManagerRestAPI.repository.TaskRepository;
import se.kth.ID1212.taskManagerRestAPI.repository.UserRepository;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public Task createTask(String authString, Map <String, String> body){
        String title = body.get("title");
        if(title==null||title.length()==0){
            return null;
        }
        String description = body.get("description");
        boolean doNow = Boolean.parseBoolean(body.get("doNow"));
        LocalDate dueDate = LocalDate.parse(body.get("dueDate"),formatter);
        /*
        if(!doNow) dueDate = LocalDate.parse(body.get("dueDate"),formatter);
        else dueDate = LocalDate.now();
        */
        LocalDate addedDate = LocalDate.parse(body.get("addedDate"),formatter);
        User user = userRepository.findByUId(authString);
        Task task = new Task(title, description, addedDate, false, doNow,dueDate,user.getId());
        taskRepository.save(task);
        return task;
    }

    public List<Task> listTasks(String authString,LocalDate startDate, LocalDate endDate, Boolean isDone){
        User user = userRepository.findByUId(authString);
        if(startDate==null && endDate == null && isDone == null){
            return taskRepository.findAll();
        }
        if(startDate==null){
            String date = "1900-01-01";
            startDate = LocalDate.parse(date,formatter);
        }
        if(endDate==null){
            String date = "2100-12-31";
            endDate = LocalDate.parse(date,formatter);
        }
        if(isDone == null){
            return taskRepository.findAllBetweenDueDate(startDate,endDate,user.getId());
        }else{
            return taskRepository.findAllBetweenDueDateAndIsDone(startDate,endDate,isDone,user.getId());
        }
    }

    public void deleteTask(String authString,Long id){
        User user = userRepository.findByUId(authString);
        taskRepository.deleteByIdAndUserId(id,user.getId());
    }

    public Task getTask(String authString,Long id){
        User user = userRepository.findByUId(authString);
        return taskRepository.findByIdAndUserId(id,user.getId());
    }

    public Task updateTask(String authString,Task task){
        User user = userRepository.findByUId(authString);
        if(task.getDone()==null)
            task.setDone(false);
        if(task.getTitle()==null||task.getTitle().length()==0)
            return null;
        task.setUserId(user.getId());
        taskRepository.save(task);
        return task;
    }

    public Task setTaskAsDone(String authString,Long id){
        Task task = getTask(authString,id);
        task.setDone(true);
        taskRepository.save(task);
        return task;
    }
}

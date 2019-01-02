package se.kth.ID1212.taskManagerRestAPI.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.kth.ID1212.taskManagerRestAPI.domain.Task;

import se.kth.ID1212.taskManagerRestAPI.repository.TaskRepository;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public Task createTask(Map <String, String> body){
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
        Task task = new Task(title, description, addedDate, false, doNow,dueDate);
        taskRepository.save(task);
        return task;
    }

    public List<Task> listTasks(LocalDate startDate, LocalDate endDate, Boolean isDone){
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
            return taskRepository.findAllBetweenDueDate(startDate,endDate);
        }else{
            return taskRepository.findAllBetweenDueDateAndIsDone(startDate,endDate,isDone);
        }
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }

    public Task getTask(Long id)throws Exception{
        return taskRepository.findById(id).orElseThrow(()-> new Exception());
    }

    public Task updateTask(Task task){
        if(task.getDone()==null)
            task.setDone(false);
        if(task.getTitle()==null||task.getTitle().length()==0)
            return null;
        taskRepository.save(task);
        return task;
    }

    public Task setTaskAsDone(Long id) throws Exception{
        Task task = getTask(id);
        task.setDone(true);
        taskRepository.save(task);
        return task;
    }

    public List<Task> listTaskToday(){
        return taskRepository.findAllByDueDate(LocalDate.now());
    }

    public List<Task> listTaskUpcoming(){
        return taskRepository.findAllIsNotDueDate(LocalDate.now());
    }
}

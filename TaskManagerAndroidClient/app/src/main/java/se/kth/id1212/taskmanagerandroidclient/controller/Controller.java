package se.kth.id1212.taskmanagerandroidclient.controller;

import java.io.IOException;
import java.time.LocalDate;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.kth.id1212.taskmanagerandroidclient.model.Task;
import se.kth.id1212.taskmanagerandroidclient.net.TaskManagerServiceGenerator;
import se.kth.id1212.taskmanagerandroidclient.net.TaskService;

public class Controller {

    TaskService taskService = TaskManagerServiceGenerator.createService(TaskService.class);


    public void updateTask(String title, String description, String addedDate, String dueDate,long taskId)throws IOException, APIResponseError{
        Task task = new Task(title,description,addedDate,dueDate);
        task.setId(taskId);
        TaskService taskService = TaskManagerServiceGenerator.createService(TaskService.class);
        Call<ResponseBody> call = taskService.updateTask(task);
        try{
            Response<ResponseBody> response = call.execute();
            if(!response.isSuccessful()){
                throw new APIResponseError(response.errorBody().string());
            }
        }catch (IOException e){
            throw e;
        }
    }
    public void addTask(String title, String description, String addedDate, String dueDate)throws IOException,APIResponseError{
        Task task = new Task(title,description,addedDate,dueDate);
        TaskService taskService = TaskManagerServiceGenerator.createService(TaskService.class);
        Call<ResponseBody> call = taskService.addTask(task);
        try{
            Response<ResponseBody> response = call.execute();
            if(!response.isSuccessful()){
                //System.out.println(response.errorBody().string());
                throw new APIResponseError(response.errorBody().string());
            }
        }catch (IOException e){
            throw e;
        }
    }

}

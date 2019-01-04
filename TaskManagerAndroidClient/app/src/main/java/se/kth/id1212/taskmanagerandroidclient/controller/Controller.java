package se.kth.id1212.taskmanagerandroidclient.controller;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import se.kth.id1212.taskmanagerandroidclient.model.User;
import se.kth.id1212.taskmanagerandroidclient.model.Task;
import se.kth.id1212.taskmanagerandroidclient.net.APIResponseErrorException;
import se.kth.id1212.taskmanagerandroidclient.net.TaskManagerServiceGenerator;
import se.kth.id1212.taskmanagerandroidclient.net.TaskService;

/**
 *
 * The APIResponseErrorException is thrown if the retrofit api call is not successful,
 * i.e. something went wrong on the server. The exception will get the message from the server that
 * explains exactly what went wrong.
 * The IOException is thrown if the application cannot reach the API server.
 *
 */
public class Controller {

    TaskService taskService = TaskManagerServiceGenerator.createService(TaskService.class);
    User loggedInUser;

    public Controller(){};
    public Controller(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void updateTask(String title, String description, String addedDate, String dueDate, long taskId)throws IOException, APIResponseErrorException {
        Task task = new Task(title,description,addedDate,dueDate);
        task.setId(taskId);
        Call<ResponseBody> call = taskService.updateTask(loggedInUser.getuId(),task);
        try{
            Response<ResponseBody> response = call.execute();
            if(!response.isSuccessful()){
                throw new APIResponseErrorException(response.errorBody().string());
            }
        }catch (IOException e){
            throw e;
        }
    }
    public void addTask(String title, String description, String addedDate, String dueDate)throws IOException,APIResponseErrorException {
        Task task = new Task(title,description,addedDate,dueDate);
        Call<ResponseBody> call = taskService.addTask(loggedInUser.getuId(),task);
        try{
            Response<ResponseBody> response = call.execute();
            if(!response.isSuccessful()){
                throw new APIResponseErrorException(response.errorBody().string());
            }
        }catch (IOException e){
            throw e;
        }
    }

    public ArrayList<Task> getTasks(LocalDate startDate,LocalDate endDate,Boolean isDone)throws IOException,APIResponseErrorException {
        Call<ArrayList<Task>> call = taskService.getTasks(loggedInUser.getuId(),startDate,endDate,isDone);
        Response<ArrayList<Task>> response = null;
        try{
            response = call.execute();
            if(!response.isSuccessful()){
                throw new APIResponseErrorException(response.errorBody().string());
            }
        }catch (IOException e){
            throw e;
        }
        return response.body();

    }

    public void setTaskAsDone(Long id) throws IOException,APIResponseErrorException {
        Call<ResponseBody> call = taskService.setTaskAsDone(loggedInUser.getuId(),id);
        try{
            Response<ResponseBody> response = call.execute();
            if(!response.isSuccessful()){
                throw new APIResponseErrorException(response.errorBody().string());
            }
        }catch (IOException e){
            throw e;
        }
    }

    public void deleteTask(Long id) throws IOException,APIResponseErrorException {
        Call<ResponseBody> call = taskService.deleteTask(loggedInUser.getuId(),id);
        try{
            Response<ResponseBody> response = call.execute();
            if(!response.isSuccessful()){
                throw new APIResponseErrorException(response.errorBody().string());
            }
        }catch (IOException e){
            throw e;
        }
    }

    public void createAccount(String email, String uId) throws IOException, APIResponseErrorException {
        Call<ResponseBody> call = taskService.createUser(new User(email,uId));
        try{
            Response<ResponseBody> response = call.execute();
            if(!response.isSuccessful()){
                throw new APIResponseErrorException(response.errorBody().string());
            }
        }catch (IOException e){
            throw e;
        }
    }

    public void signOut(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
    }

}

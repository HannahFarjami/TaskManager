package se.kth.id1212.taskmanagerandroidclient.net;

import java.time.LocalDate;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import se.kth.id1212.taskmanagerandroidclient.model.Task;
import se.kth.id1212.taskmanagerandroidclient.model.User;

public interface TaskService {

    @GET("tasks")
    Call<ArrayList<Task>> getTasks(
            @Header("Authorization") String token,
            @Query("startDate")LocalDate startDate,
            @Query("endDate") LocalDate endDate,
            @Query("isDone") Boolean isDone
            );

    @DELETE("task/{id}")
    Call<ResponseBody> deleteTask(@Header("Authorization") String token,@Path("id") Long id);

    @POST("task")
    Call<ResponseBody> addTask(@Header("Authorization") String token,@Body Task task);

    @PUT("task")
    Call<ResponseBody> updateTask(@Header("Authorization") String token,@Body Task task);

    @PUT("task/{id}")
    Call<ResponseBody> setTaskAsDone(@Header("Authorization") String token,@Path("id") Long id);

    @POST("user")
    Call<ResponseBody> createUser(@Body User user);
}

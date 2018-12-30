package se.kth.id1212.taskmanagerandroidclient.model;

import java.time.LocalDate;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TaskService {

    @GET("tasks")
    Call<ArrayList<Task>> getTasks(
            @Query("startDate")LocalDate startDate,
            @Query("endDate") LocalDate endDate,
            @Query("isDone") Boolean isDone
            );

    @DELETE("task/{id}")
    Call<ResponseBody> deleteTask(@Path("id") Long id);

    @POST("task")
    Call<ResponseBody> addTask(@Body Task task);

    @PUT("task")
    Call<ResponseBody> updateTask(@Body Task task);

    @PUT("task/{id}")
    Call<ResponseBody> setTaskAsDone(@Path("id") Long id);

}

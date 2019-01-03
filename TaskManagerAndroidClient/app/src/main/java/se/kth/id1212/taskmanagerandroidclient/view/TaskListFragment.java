package se.kth.id1212.taskmanagerandroidclient.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import se.kth.id1212.taskmanagerandroidclient.R;
import se.kth.id1212.taskmanagerandroidclient.controller.APIResponseError;
import se.kth.id1212.taskmanagerandroidclient.model.Task;

public class TaskListFragment extends Fragment {

    View rootView;
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LocalDate startDate = (LocalDate) getArguments().getSerializable("START_DATE");
        LocalDate endDate = (LocalDate) getArguments().getSerializable("END_DATE");
        Boolean isDone = getArguments().getBoolean("IS_DONE");
        mainActivity = (MainActivity) getContext();

        new GetTaskList(startDate,endDate,isDone).execute();
        rootView =
                inflater.inflate(R.layout.fragment_task_list, container, false);

        return rootView;
    }

    void setList(ArrayList<Task> tasks){
        getActivity().runOnUiThread(()->{
            TaskListAdapter adapter = new TaskListAdapter(tasks,getActivity());
            ListView lView = (ListView)rootView.findViewById(R.id.list);
            lView.setAdapter(adapter);
        });
    }

    private class GetTaskList extends AsyncTask<Void,Void,ArrayList<Task>>{

        LocalDate startDate;
        LocalDate endDate;
        Boolean isDone;

        public GetTaskList(LocalDate startDate, LocalDate endDate, Boolean isDone) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.isDone = isDone;
        }

        @Override
        protected ArrayList<Task> doInBackground(Void... voids) {
            ArrayList<Task> tasks = null;
            try {
                    tasks = mainActivity.getController().getTasks(startDate,endDate,isDone);
            }catch (IOException ex){
                mainActivity.showError("Network problem");
            }catch (APIResponseError apiError){
                mainActivity.showError(apiError.getMsg());
            }
            return tasks;
        }

        @Override
        protected void onPostExecute(ArrayList<Task> tasks) {
            super.onPostExecute(tasks);
            if(tasks==null){
                mainActivity.showError("Network problem, looks like the server is down");
                return;
            }
            setList(tasks);
        }
    }
}

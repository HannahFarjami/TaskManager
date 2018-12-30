package se.kth.id1212.taskmanagerandroidclient;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.time.LocalDate;

import okhttp3.ResponseBody;
import retrofit2.Call;
import se.kth.id1212.taskmanagerandroidclient.model.Task;
import se.kth.id1212.taskmanagerandroidclient.model.TaskManagerServiceGenerator;
import se.kth.id1212.taskmanagerandroidclient.model.TaskService;

public class AddEditTaskFragment extends Fragment {

    private Long taskId;
    private boolean update = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView =
                inflater.inflate(R.layout.fragment_add_task, container, false);
        Bundle arguments = getArguments();

        Button addEditButton = (Button) rootView.findViewById(R.id.createTask);
        Button datePicker = (Button) rootView.findViewById(R.id.datePicker);
        datePicker.setText(LocalDate.now().toString());
        if(arguments!=null){
            setValuesForEdit((Task)arguments.getSerializable("TASK"),rootView);
        }
        addEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTask(view);
            }
        });
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        return rootView;

    }

    private void setValuesForEdit(Task task,View rootView){
        taskId = task.getId();
        update = true;
        TextView title = (TextView) rootView.findViewById(R.id.title);
        TextView description = (TextView) rootView.findViewById(R.id.description);
        Button editBtn = (Button) rootView.findViewById(R.id.createTask);
        Button datePicker = (Button) rootView.findViewById(R.id.datePicker);
        editBtn.setText("Edit");
        title.setText(task.getTitle());
        datePicker.setText(task.getDueDate());
        description.setText(task.getDescription());
    }

    public void createTask(View view){
        new SendAddOrEdit((TextView) getActivity().findViewById(R.id.title),(TextView) getActivity().findViewById(R.id.description), (Button) getActivity().findViewById(R.id.datePicker),false).execute();
        getFragmentManager().popBackStackImmediate();
        /* TextView title = (TextView) findViewById(R.id.title);
        TextView description = (TextView) findViewById(R.id.description);
        RadioButton doNowBtn = (RadioButton) findViewById(R.id.doNow);
        boolean doNow = false;

        if(doNowBtn.isChecked()) {doNow = true;}

        Task task = new Task(title.getText().toString(),description.getText().toString(),true,"",null);*/
    }

    private class SendAddOrEdit extends AsyncTask<Void,Void,Void> {
        TextView title;
        TextView description;
        CheckBox doNowBtn;
        Button dueDate;
        boolean doNow;

        public SendAddOrEdit(TextView title,TextView description,Button dueDate, boolean doNow) {
            this.title = title;
            this.description = description;
            this.dueDate = dueDate;
           //this.doNowBtn = doNowBtm;
            this.doNow = doNow;
        }


        @Override
        protected Void doInBackground(Void ...voids) {

           /* if(this.doNowBtn.isChecked()) {
                this.doNow = true;}
               */
            Task task = new Task(this.title.getText().toString(),this.description.getText().toString(),LocalDate.now().toString(),dueDate.getText().toString());
            TaskService taskService = TaskManagerServiceGenerator.createService(TaskService.class);
            Call<ResponseBody> call;
            if(update){
                task.setId(taskId);
                call = taskService.updateTask(task);
            }else {
                call = taskService.addTask(task);
            }

            try {
                call.execute();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        }
    }
}

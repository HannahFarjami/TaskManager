package se.kth.id1212.taskmanagerandroidclient.view;

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
import android.widget.TextView;

import java.io.IOException;
import java.time.LocalDate;


import se.kth.id1212.taskmanagerandroidclient.R;
import se.kth.id1212.taskmanagerandroidclient.net.APIResponseErrorException;
import se.kth.id1212.taskmanagerandroidclient.model.Task;


/**
 * Fragment for creating and edit tasks.
 */
public class AddEditTaskFragment extends Fragment {

    private Long taskId;
    private boolean update = false;
    MainActivity mainActivity ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView =
                inflater.inflate(R.layout.fragment_add_task, container, false);
        Bundle arguments = getArguments();

        mainActivity = (MainActivity) getContext();
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

    /**
     * If the edit button was clicked the fragment should show the fields for the task
     * as it is before edit.
     *
     * @param task to be edited
     * @param rootView
     */
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
        TextView title = (TextView) getActivity().findViewById(R.id.title);
        new SendAddOrEdit((TextView) getActivity().findViewById(R.id.title), (TextView) getActivity().findViewById(R.id.description), (Button) getActivity().findViewById(R.id.datePicker)).execute();
        getFragmentManager().popBackStackImmediate();
    }

    /**
     * This inner class is responsible for invoke the controller to make api calls for
     * editing or adding tasks. Runs on a separate thread then the UI thread.
     */
    private class SendAddOrEdit extends AsyncTask<Void,Void,Void> {
        TextView title;
        TextView description;
        Button dueDate;

        public SendAddOrEdit(TextView title,TextView description,Button dueDate) {
            this.title = title;
            this.description = description;
            this.dueDate = dueDate;
        }

        @Override
        protected Void doInBackground(Void ...voids) {
            try {
                if(update) {
                    mainActivity.getController().updateTask(this.title.getText().toString(), this.description.getText().toString(), LocalDate.now().toString(), dueDate.getText().toString(), taskId);
                }else{
                    mainActivity.getController().addTask(this.title.getText().toString(), this.description.getText().toString(), LocalDate.now().toString(), dueDate.getText().toString());
                }
            }catch (IOException ex){
                mainActivity.showError("Network problem");
            }catch (APIResponseErrorException apiError){
                mainActivity.showError(apiError.getMsg());
            }
            return null;
        }

    }
}

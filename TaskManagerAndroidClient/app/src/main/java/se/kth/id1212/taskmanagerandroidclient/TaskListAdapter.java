package se.kth.id1212.taskmanagerandroidclient;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import se.kth.id1212.taskmanagerandroidclient.model.Task;
import se.kth.id1212.taskmanagerandroidclient.model.TaskManagerServiceGenerator;
import se.kth.id1212.taskmanagerandroidclient.model.TaskService;

public class TaskListAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Task> list = new ArrayList<Task>();
    private Context context;
    View view;



    public TaskListAdapter(ArrayList<Task> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return list.get(pos).getId();
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.design_task, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position).getTitle());

        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);
        Button addBtn = (Button)view.findViewById(R.id.add_btn);
        CheckBox doneBox = (CheckBox) view.findViewById(R.id.checkBox);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new SendDelete().execute(getItemId(position));
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("TASK",(Serializable) getItem(position));
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AddEditTaskFragment addEditTaskFragment = new AddEditTaskFragment();
                addEditTaskFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.listContainer, addEditTaskFragment);

                fragmentTransaction.addToBackStack(null).commit();
                notifyDataSetChanged();
            }
        });
        doneBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    System.out.println("are we here");
                    new SendUpdate().execute(getItemId(position));
                }
            }
        });

        return view;
    }

    private class SendUpdate extends AsyncTask<Long,Void,Void>{
        @Override
        protected Void doInBackground(Long... longs) {
            Long id = longs[0];
            TaskService taskService = TaskManagerServiceGenerator.createService(TaskService.class);
            Call<ResponseBody> call= taskService.setTaskAsDone(id);
            try {
                call.execute();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        }
    }

    private class SendDelete extends AsyncTask<Long,Void,Void>{
        @Override
        protected Void doInBackground(Long... longs) {
            Long id = longs[0];
            TaskService taskService = TaskManagerServiceGenerator.createService(TaskService.class);
            Call<ResponseBody> call= taskService.deleteTask(id);
            try {
                call.execute();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        }
    }

}
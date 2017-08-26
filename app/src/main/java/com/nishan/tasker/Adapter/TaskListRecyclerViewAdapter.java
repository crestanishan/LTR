package com.nishan.tasker.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nishan.tasker.Database.Task;
import com.nishan.tasker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NaRan on 8/18/17 at 21:06.
 */

public class TaskListRecyclerViewAdapter extends RecyclerView.Adapter<TaskListRecyclerViewAdapter.ViewHolders>{


    private Context context;
    private List<Task> taskList = new ArrayList<>();

    public TaskListRecyclerViewAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @Override
    public TaskListRecyclerViewAdapter.ViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_task_details, parent, false);

        return new TaskListRecyclerViewAdapter.ViewHolders(itemView);

    }

    @Override
    public void onBindViewHolder(TaskListRecyclerViewAdapter.ViewHolders holder, int position) {

        final Task task = taskList.get(position);
        holder.location.setText(task.getLat()+" - "+task.getLog());
        holder.date.setText(task.getDate());
        holder.time.setText(task.getTime());
        holder.task_details.setText(task.getTaskDetails());






    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder{

        TextView location;
        TextView date;
        TextView time;
        TextView task_details;

        public ViewHolders(View itemView) {
            super(itemView);

            location = (TextView) itemView.findViewById(R.id.location);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);
            task_details = (TextView) itemView.findViewById(R.id.task);


        }
    }

}

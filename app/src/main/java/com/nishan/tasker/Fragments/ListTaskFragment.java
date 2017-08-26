package com.nishan.tasker.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nishan.tasker.Adapter.TaskListRecyclerViewAdapter;
import com.nishan.tasker.Database.DatabaseHandler;
import com.nishan.tasker.Database.Task;
import com.nishan.tasker.R;

import java.util.List;


public class ListTaskFragment extends TaskerFragment {


    private RecyclerView recyclerView;
    Task task;

    TaskListRecyclerViewAdapter taskListRecyclerViewAdapter;
    DatabaseHandler dbHandler;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.fragment_list_task, null);
        initiliseView(view);
        initialiseListener();
        setUpRecyclerView();

        return view;
    }


    @Override
    protected void initiliseView(View view) {

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

    }

    @Override
    protected void initialiseListener() {

        dbHandler = new DatabaseHandler(getActivity());



    }


    private void setUpRecyclerView() {
        final List<Task> taskList= dbHandler.getAllInquiries();
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager1);
        taskListRecyclerViewAdapter = new TaskListRecyclerViewAdapter(getContext(), taskList);
        recyclerView.setAdapter(taskListRecyclerViewAdapter);

    }

}

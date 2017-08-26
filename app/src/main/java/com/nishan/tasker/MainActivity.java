package com.nishan.tasker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nishan.tasker.Activity.TaskerActivity;
import com.nishan.tasker.Fragments.AddTaskFragment;
import com.nishan.tasker.Fragments.ListTaskFragment;

public class MainActivity extends TaskerActivity implements View.OnClickListener{


    private Toolbar toolbar;
    private ImageView lastSelectedImageView;
    private LinearLayout bottomNavigation;
    private ImageView add , list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initiliseView();
        initialiseListener();

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        setUpFragment(new AddTaskFragment());
    }

    @Override
    protected void initiliseView() {

        //initialize everything here

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        bottomNavigation = (LinearLayout) findViewById(R.id.bottom_navigation);
        add = (ImageView) findViewById(R.id.add);
        lastSelectedImageView = add;
        list = (ImageView) findViewById(R.id.list);

    }

    @Override
    protected void initialiseListener() {

        bottomNavigation.setOnClickListener(this);
        add.setOnClickListener(this);
        list.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.add:
                setUpBottomNavigation(add);
                setUpFragment(new AddTaskFragment());
                break;
            case R.id.list:
                setUpBottomNavigation(list);
                setUpFragment(new ListTaskFragment());
                break;


        }


    }

    private void setUpFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
    private void setUpBottomNavigation(ImageView view) {
        if (lastSelectedImageView != null) {
            lastSelectedImageView.setColorFilter(Color.parseColor("#FF03071E"));
        }
        view.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        lastSelectedImageView = view;
    }


}

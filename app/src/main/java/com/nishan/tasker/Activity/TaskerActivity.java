package com.nishan.tasker.Activity;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by NaRan on 7/27/17.
 */

abstract public class TaskerActivity extends AppCompatActivity {

    abstract protected void initiliseView();

    abstract protected void initialiseListener();

}

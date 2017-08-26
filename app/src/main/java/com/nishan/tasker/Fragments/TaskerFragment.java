package com.nishan.tasker.Fragments;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by NaRan on 7/27/17.
 */

abstract public class TaskerFragment extends Fragment{

    abstract protected void initiliseView(View view);

    abstract protected void initialiseListener();

}

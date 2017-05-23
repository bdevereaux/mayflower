package com.blackboardtheory.mayflower;

import android.content.Context;
import android.view.View;

/**
 *
 */
public abstract class Screen {

    private Context context;
    private String title;
    private ScreenFragment fragment;

    public Screen(Context context, String title) {
        this.context = context;
        this.title = title;
        fragment = new ScreenFragment();
        fragment.setView(getView());
    }

    public abstract View getView();

    public ScreenFragment getFragment() {
        return this.fragment;
    }

    public String getTitle() {
        return this.title;
    }

    public Context getContext() {
        return this.context;
    }

}

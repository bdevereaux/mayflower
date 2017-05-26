package com.blackboardtheory.mayflower;

import android.content.Context;

/**
 * Created by bdevereaux3 on 5/25/17.
 */

public abstract class Screen<T extends Contract.ViewContract> implements Contract.PresenterContract{

    protected T vContract;
    private String title;
    private ScreenFragment fragment;

    public Screen(T vContract, Context context, String title) {
        this.vContract = vContract;
        this.title = title;
        fragment = new ScreenFragment();
        fragment.setView(createView(context));
    }


    public ScreenFragment getFragment() {
        return this.fragment;
    }

    public String getTitle() {
        return this.title;
    }

    @Override
    public String getPresenterContractTitle() {
        return getClass().getName();
    }
}

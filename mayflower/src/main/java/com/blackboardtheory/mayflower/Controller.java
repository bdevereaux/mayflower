package com.blackboardtheory.mayflower;

import android.content.Context;

import com.blackboardtheory.mayflower.Contract;

/**
 * Created by bdevereaux3 on 5/25/17.
 */

public abstract class Controller<T extends Contract.PresenterContract> implements Contract.ViewContract {

    protected T pContract;
    private Context context;

    public Controller(Context context) {
        this.context = context;
        this.pContract = createContract();
    }

    protected abstract T createContract();

    protected Context getContext() {
        return this.context;
    }

    @Override
    public String getViewContractTitle() {
        return getClass().getName();
    }

    public T getContract() {
        return this.pContract;
    }

}

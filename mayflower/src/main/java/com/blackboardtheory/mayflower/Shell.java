package com.blackboardtheory.mayflower;

import android.content.Context;

/**
 * Created by bdevereaux3 on 5/25/17.
 */

public class Shell<P extends Contract.PresenterContract, V extends Contract.ViewContract> {

    private P pContract;
    private V vContract;

    public Shell(Context context) {

    }
}

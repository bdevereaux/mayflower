package com.blackboardtheory.mayflower;

import android.content.Context;
import android.view.View;

/**
 * Created by bdevereaux3 on 5/25/17.
 */
public interface Contract {

    interface PresenterContract extends Contract {
        String getPresenterContractTitle();

        View createView(Context context);
    }

    interface ViewContract extends Contract {
        String getViewContractTitle();
    }
}

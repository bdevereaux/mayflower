package com.blackboardtheory.mayflower;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by bdevereaux3 on 8/24/17.
 */

public abstract class MayflowerActivity extends AppCompatActivity {

    private  Navigator navigatorInstance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(null == navigatorInstance || navigatorInstance.isDestroyed()) {
            Navigator.NavigatorBuilder navBuilder = new Navigator.NavigatorBuilder(getSupportFragmentManager());
            navigatorInstance = navBuilder
                    .forContainerViewId(getNavigatorContainer())
                    .withEntryPoint(getEntryPoint())
                    .build();
        }
        navigatorInstance.goTo(navigatorInstance.getCurrentController(), true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        navigatorInstance.destroy();
    }

    protected int getLayoutID() {
        return R.layout.mayflower_root;
    }

    protected int getNavigatorContainer() {
        return R.id.mayflower_root_container;
    }

    public abstract Controller getEntryPoint();
}

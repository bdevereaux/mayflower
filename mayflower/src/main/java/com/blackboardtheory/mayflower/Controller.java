package com.blackboardtheory.mayflower;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by bdevereaux3 on 8/21/17.
 */

public abstract class Controller<T extends Screen> extends Fragment implements InteractionContract {

    private Screen screen;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            screen = getScreenClass().newInstance();
            if(null != screen && screen.isValid()) {
                performInteractionContractImplementationCheck();
            }
        } catch (IllegalAccessException e) {
            Log.e("MayflowerException", e.getMessage());
        } catch (java.lang.InstantiationException e) {
            Log.e("MayflowerException", e.getMessage());
        } catch (BadControllerException e) {
            Log.e("MayflowerException", e.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = screen.setupView(inflater);
        screen.setIContract(this);
        return view;
    }

    public abstract String getTitle();

    public abstract Class<? extends Screen> getScreenClass();

    private void performInteractionContractImplementationCheck() throws BadControllerException {
        Class<?>[] interfaces = getClass().getInterfaces();
        String expectedInterfaceName = getScreenClass().getName().concat("$").concat(Screen.CONTRACT_NAME);
        for(Class<?> clazzy : interfaces) {
            if(expectedInterfaceName.equals(clazzy.getName())) {
                return;
            }
        }
        throw new BadControllerException(Screen.BAD_CONTROLLER_MESSAGE);
    }

    public T getScreen() {
        return (T)screen;
    }
}

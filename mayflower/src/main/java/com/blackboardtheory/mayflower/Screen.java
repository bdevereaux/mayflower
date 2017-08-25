package com.blackboardtheory.mayflower;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by bdevereaux3 on 8/21/17.
 */

public abstract class Screen<T extends InteractionContract> {

    public final static String CONTRACT_NAME = "ICONTRACT";
    public final static String BAD_SCREEN_MESSAGE = "All Screens must declare an interface named 'ICONTRACT' that extends from InteractionContract";
    public final static String BAD_CONTROLLER_MESSAGE = "The Controller coupled to this Screen must implement the ICONTRACT declared by this Screen";

    private boolean isValid;
    private InteractionContract iContract;

    public Screen() {
        try {
            performInteractionContractDeclarationCheck();
            isValid = true;
        } catch(BadScreenException e) {
            Log.e("MayflowerException", e.getMessage());
        }
    }

    protected abstract View setupView(LayoutInflater inflater);

    private void performInteractionContractDeclarationCheck() throws BadScreenException {
        Class<?>[] classes = getClass().getDeclaredClasses();
        String expectedInterfaceName = getClass().getName().concat("$").concat(CONTRACT_NAME);
        Class<?> expectedInterface = null;
        for(Class<?> clazz : classes) {
            if(expectedInterfaceName.equals(clazz.getName())) {
                expectedInterface = clazz;
                break;
            }
        }
        if(null == expectedInterface || !InteractionContract.class.isAssignableFrom(expectedInterface)) {
            throw new BadScreenException(BAD_SCREEN_MESSAGE);
        }
    }

    boolean isValid() {
        return isValid;
    }

    void setIContract(InteractionContract iContract) {
        this.iContract = iContract;
    }

    protected T getIContract() {
        return (T)iContract;
    }

}

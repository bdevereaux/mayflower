package com.blackboardtheory.mayflower;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.Stack;

/**
 * Created by bdevereaux3 on 8/21/17.
 */

public class Navigator {

    private Stack<Controller> navigationStack;

    private FragmentManager fm;

    private int containerViewId;

    private boolean isDestroyed;


    private Navigator(FragmentManager fm, int containerViewId, Controller entryPointController) {
        this.fm = fm;
        this.containerViewId = containerViewId;
        this.navigationStack = new Stack<>();
        this.navigationStack.push(entryPointController);
        this.isDestroyed = false;
    }


    public Controller getCurrentController() {
        return this.navigationStack.peek();
    }

    public void destroy() {
        this.isDestroyed = false;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void goTo(Controller controller, boolean keepBackStack) {
        if(!keepBackStack) {
            navigationStack.empty();
        }
        FragmentTransaction transaction;
        if(navigationStack.isEmpty()) {
            transaction = fm.beginTransaction().add(containerViewId, controller, controller.getTitle());
        }
        else {
            transaction = fm.beginTransaction().replace(containerViewId, controller, controller.getTitle());
        }
        transaction.commit();
        navigationStack.push(controller);

    }

    public static class NavigatorBuilder {
        private Controller entryPoint;
        private FragmentManager fm;
        private int containerViewId;

        public NavigatorBuilder(@NonNull FragmentManager fm) {
            this.fm = fm;
        }

        public NavigatorBuilder withEntryPoint(Controller entryPointController) {
            this.entryPoint = entryPointController;
            return this;
        }

        public NavigatorBuilder forContainerViewId(int containerViewId) {
            this.containerViewId = containerViewId;
            return this;
        }

        public Navigator build() {
            return new Navigator(this.fm, this.containerViewId, this.entryPoint);
        }
    }
}

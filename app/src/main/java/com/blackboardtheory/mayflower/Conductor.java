package com.blackboardtheory.mayflower;

import android.support.v4.app.FragmentManager;

/**
 * Created by bdevereaux3 on 5/23/17.
 */

public class Conductor {

    private static Conductor instance;

    private boolean isDestroyed;
    private FragmentManager fm;
    private int containerID;
    private Screen currentScreen;

    /**
     * Method to init our Conductor instance
     *
     * @param containerActivity The {@link MayflowerActivity} through which this Conductor will operate
     * @param containerID The id of the view that will contain our {@link Screen} objects
     * @param entryScreen The initial {@link Screen} to be displayed in our activity
     */
    public static void init(MayflowerActivity containerActivity, int containerID, Screen entryScreen) {
        if(instance == null || instance.isDestroyed) {
            instance = new Conductor(containerActivity, containerID, entryScreen);
            instance.fm.beginTransaction().add(containerID, entryScreen.getFragment(), entryScreen.getTitle()).commitAllowingStateLoss();
        }
    }

    /**
     * Static method to return our singleton instance
     *
     * @return A singleton instance of our Conductor if it has been initialized; otherwise null
     */
    public static Conductor getInstance() {
        return instance;
    }

    /**
     * Private singleton constructor to initialize our Conductor instance
     *
     * @param containerActivity The {@link MayflowerActivity} through which this Conductor will operate
     * @param containerID The id of the view that will contain our {@link Screen} objects
     * @param entryScreen The initial {@link Screen} to be displayed in our activity
     */
    private Conductor(MayflowerActivity containerActivity, int containerID, Screen entryScreen) {
        this.fm = containerActivity.getSupportFragmentManager();
        this.containerID = containerID;
        this.currentScreen = entryScreen;
        this.isDestroyed = false;
    }

    public void goTo(Screen screen) {
        currentScreen = screen;
        fm.beginTransaction().replace(containerID, screen.getFragment(), screen.getTitle()).commitAllowingStateLoss();
    }

    public void destroy() {
        this.isDestroyed = true;
    }
}

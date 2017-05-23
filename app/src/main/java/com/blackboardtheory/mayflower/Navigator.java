/**
 * Copyright 2017 Brandon Devereaux
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.blackboardtheory.mayflower;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Navigator.class
 *
 * The Navigator class aims to simplify Fragment navigation
 */
@Deprecated
public class Navigator {

    private static Navigator instance;

    private boolean isDestroyed;
    private FragmentManager fm;
    private int containerID;
    private FragmentNode curr;

    /**
     * Method to init our Navigator instance
     *
     * @param containerActivity The {@link MayflowerActivity} through which this Navigator will operate
     * @param containerID The id of the view that will contain our {@link FragmentNode} objects
     * @param initialNode The initial {@link FragmentNode} to be displayed in our activity
     */
    public static void init(MayflowerActivity containerActivity, int containerID, FragmentNode initialNode) {
        if(instance == null || instance.isDestroyed) {
            instance = new Navigator(containerActivity, containerID, initialNode);
            instance.fm.beginTransaction().add(containerID, initialNode.current, initialNode.tag).commitAllowingStateLoss();
        }
    }

    /**
     * Static method to return our singleton instance
     *
     * @return A singleton instance of our Navigator if it has been initialized; otherwise null
     */
    public static Navigator getInstance() {
        return instance;
    }

    /**
     * Private singleton constructor to initialize our Navigator instance
     *
     * @param containerActivity The {@link MayflowerActivity} through which this Navigator will operate
     * @param containerID The id of the view that will contain our {@link FragmentNode} objects
     * @param initialNode The initial {@link FragmentNode} to be displayed in our activity
     */
    private Navigator(MayflowerActivity containerActivity, int containerID, FragmentNode initialNode) {
        this.fm = containerActivity.getSupportFragmentManager();
        this.containerID = containerID;
        this.curr = initialNode;
        this.isDestroyed = false;
    }

    /**
     * Method to navigate to a specified {@link FragmentNode} node, with the option to maintain the
     * current back-stack
     *
     * @param node The {@link FragmentNode} node to navigate to
     * @param maintainBackStack Whether or not to keep the current back stack after navigating to
     *                          the new node
     */
    public void goTo(FragmentNode node, boolean maintainBackStack) {
        if(maintainBackStack) {
            node.previous = curr;
        }
        curr = node;
        fm.beginTransaction().replace(containerID, node.current, node.tag).commitAllowingStateLoss();
    }

    /**
     * Attempt to return to the previous node, if the current node has a non-null previous-reference
     */
    public void goBack() {
        if(null != curr.previous) {
            curr = curr.previous;
            fm.beginTransaction().replace(containerID, curr.current, curr.tag).commitAllowingStateLoss();
        }
    }

    /**
     * Set the isDestroyed flag for this Navigator to indicate the Navigator needs to be re-inited
     */
    public void setDestroyed() {
        this.isDestroyed = true;
    }

    /**
     * Our wrapper class for maintaining {@link Fragment} objects and their navigation state
     */
    @Deprecated
    public static class FragmentNode {
        public Fragment current;
        public String tag;
        public FragmentNode previous;

        /**
         * Constructor for our fragment node that allows setting our previous node
         *
         * @param fragment The {@link Fragment} wrapped in this node
         * @param tag The String tag associated with this fragment
         * @param previousNode The optional node to return to which to return
         */
        public FragmentNode(Fragment fragment, String tag, @Nullable FragmentNode previousNode) {
            this.current = fragment;
            this.tag = tag;
            this.previous = previousNode;
        }

        /**
         * Simplified constructor for our fragment node without a previous node
         *
         * @param fragment The {@link Fragment} wrapped in this node
         * @param tag The String tag associated with this fragment
         */
        public FragmentNode(Fragment fragment, String tag) {
            this(fragment, tag, null);
        }
    }
}

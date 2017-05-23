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

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * MayflowerActivity.class
 *
 * Monolithic Activity class that relies on a {@link Conductor} object for navigation through
 * Screen objects
 */
public abstract class MayflowerActivity extends AppCompatActivity {

    /**
     * We set our content view to the root layout id we declare for our application.
     * Override as necessary if a more custom creation is needed at the activity level
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
    }

    /**
     * We attempt to re-init our {@link Conductor} object on each resume, in case it has been
     * destroyed. If it has not been destroyed, it will use the current instance rather than
     * recreating it.
     */
    @Override
    public void onResume() {
        super.onResume();
        Conductor.init(this, getConductorContainer(), getEntryScreen());
    }

    /**
     * We have to destroy our {@link Conductor} Conductor when the activity is destroyed, otherwise
     * we will get bad references when we attempt to recreate
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Conductor.getInstance().destroy();
    }

    /**
     * Method to return the container ID responsible for housing the
     * {@link Screen} objects through which we will navigate
     *
     * @return Root container ID for the application
     */
    protected int getConductorContainer() {
        return R.id.mayflower_root_container;
    }

    /**
     * Method to return the root layout of our activity
     *
     * @return The root layout ID of our activity
     */
    protected int getLayoutId() {
        return R.layout.mayflower_root;
    }

    /**
     * Return the root {@link Screen} Screen for the application
     *
     * @return {@link Screen} the initial Screen
     */
    protected abstract Screen getEntryScreen();
}

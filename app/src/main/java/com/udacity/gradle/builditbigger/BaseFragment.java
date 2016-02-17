/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;

import com.mpayne.android.joke.JokeDisplayActivity;
import com.udacity.gradle.builditbigger.service.EndpointsAsyncTask;

/**
 * A base fragment to be extended by free/paid flavor fragment for common functionality
 * Referenced
 * http://www.tutorialspoint.com/android/android_loading_spinner.htm
 */
public class BaseFragment extends Fragment {

    protected ProgressBar mSpinner;

    protected void launchEndpointsAsyncTask(){
        new EndpointsAsyncTask().setListener(new EndpointsAsyncTask.Callback() {
            @Override
            public void onPreExecute() {
                mSpinner.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPostExecute(String result) {
                mSpinner.setVisibility(View.GONE);
                // Display joke in activity
                Intent intent = new Intent(getActivity(), JokeDisplayActivity.class);
                intent.putExtra(JokeDisplayActivity.JOKE_KEY, result);
                startActivity(intent);
            }
        }).execute(getActivity().getString(R.string.gce_url));
    }

}

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
package com.udacity.gradle.builditbigger.service;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.mpayne.android.joke.api.jokeApi.JokeApi;

import java.io.IOException;

/**
 * Retrieve joke from GCE
 * Reference
 * https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
 * http://marksunghunpark.blogspot.ru/2015/05/how-to-test-asynctask-in-android.html
 */
public class EndpointsAsyncTask extends AsyncTask<String, Void, String> {

    private static final String TAG = EndpointsAsyncTask.class.getSimpleName();

    private Callback mCallback = null;
    private static JokeApi sJokeApi = null;

    public EndpointsAsyncTask setListener(Callback listener) {
        mCallback = listener;
        return this;
    }

    public interface Callback {
        public void onPreExecute();
        public void onPostExecute(String result);
    }

    @Override
    protected void onPreExecute(){
        if(mCallback != null) {
            mCallback.onPreExecute();
        }
    }

    @Override
    protected String doInBackground(String... params) {

        String gceUrl = params[0];
        String joke = null;

        if(sJokeApi == null) {  // Only do this once
            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl(gceUrl)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            sJokeApi = builder.build();
        }

        try {
            joke = sJokeApi.tellJoke().execute().getJoke();
        } catch (IOException e) {
            Log.e(TAG, "Error retrieving joke from GCE", e);
        }

        return joke;
    }

    @Override
    protected void onPostExecute(String result) {
        if(mCallback != null) {
            mCallback.onPostExecute(result);
        }
    }

}

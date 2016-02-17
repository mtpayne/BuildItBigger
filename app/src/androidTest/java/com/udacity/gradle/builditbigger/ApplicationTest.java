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

import android.app.Application;
import android.test.ApplicationTestCase;
import android.text.TextUtils;

import com.udacity.gradle.builditbigger.service.EndpointsAsyncTask;

import java.util.concurrent.CountDownLatch;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 * Referenced http://marksunghunpark.blogspot.ru/2015/05/how-to-test-asynctask-in-android.html
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    String mJoke = null;
    CountDownLatch mLatch = null;
    String mGceUrl = "http://10.0.2.2:8080/_ah/api/";

    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        mLatch = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        mLatch.countDown();
    }

    public void testEndpointsAsyncTask() throws InterruptedException {
        EndpointsAsyncTask task = new EndpointsAsyncTask();
        task.setListener(new EndpointsAsyncTask.Callback() {
            @Override
            public void onPreExecute() {}

            @Override
            public void onPostExecute(String result) {
                mJoke = result;
                mLatch.countDown();
            }

        }).execute(mGceUrl);
        mLatch.await();

        assertFalse(TextUtils.isEmpty(mJoke));
    }


}
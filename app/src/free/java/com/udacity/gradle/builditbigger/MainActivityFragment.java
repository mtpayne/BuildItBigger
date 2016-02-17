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

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


/**
 * A placeholder fragment containing a simple view.
 * Referenced https://github.com/udacity/ud867/blob/master/FinalProject/app/src/main/java/com/udacity/gradle/builditbigger/MainActivityFragment.java
 * AdMod referenced https://developers.google.com/admob/android/quick-start
 *                  https://developers.google.com/admob/android/interstitial
 * ProgressBar referenced http://www.tutorialspoint.com/android/android_loading_spinner.htm
 */
public class MainActivityFragment extends BaseFragment {

    private InterstitialAd mInterstitialAd;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mSpinner = (ProgressBar)root.findViewById(R.id.progressbar_joke);
        mSpinner.setVisibility(View.GONE);

        // Create a banner ad request
        AdView adView = (AdView) root.findViewById(R.id.adview_banner);
        adView.loadAd(getAdRequest());

        // Create an interstitial ad request
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.loadAd(getAdRequest());

        Button tellJokeButton = (Button)root.findViewById(R.id.button_tell_joke);
        tellJokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show ad if loaded. Otherwise go straight to launching joke
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    launchEndpointsAsyncTask();
                }
            }
        });

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Request next ad
                mInterstitialAd.loadAd(getAdRequest());
                // Launch joke
                launchEndpointsAsyncTask();
            }
        });

        return root;
    }

    private AdRequest getAdRequest() {
        Log.d(this.getClass().getSimpleName(),"getAdRequest");
        return new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
    }
}

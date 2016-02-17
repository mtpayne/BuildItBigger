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
 *
 *  For step-by-step instructions on connecting your Android application to this backend module,
 *  see "App Engine Java Endpoints Module" template documentation at
 *  https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
 *
 */


package com.mpayne.android.joke.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.mpayne.android.joke.model.Joke;
import com.mpayne.android.joke.JokeSupply;

//Referenced https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
//Modified and renamed auto-generated class
/** An endpoint class we are exposing */
@Api(
  name = "jokeApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "api.joke.android.mpayne.com",
    ownerName = "api.joke.android.mpayne.com",
    packagePath=""
  )
)
public class JokeEndpoint {

    /** A simple endpoint method that tells a joke */
    @ApiMethod(name = "tellJoke")
    public Joke tellJoke() {
        Joke response = new Joke();
        response.setJoke(JokeSupply.getJoke());
        return response;
    }

}

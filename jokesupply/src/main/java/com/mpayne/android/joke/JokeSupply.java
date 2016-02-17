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
package com.mpayne.android.joke;

import java.util.Random;

/**
 * Supply random jokes.
 * Referenced https://github.com/udacity/ud867/tree/master/4.04-Exercise-CreateAnAndroidLibrary
 */
public class JokeSupply {

    // Jokes from http://www.gotlines.com/jokes/
    private static final String[] jokeArray = {"How do you make holy water? You boil the hell out of it.",
            "I am a nobody, nobody is perfect, therefore I am perfect.",
            "What do you call a bear with no teeth? -- A gummy bear!",
            "I once farted in an elevator, it was wrong on so many levels.",
            "I wondered why the frisbee was getting bigger, and then it hit me."};

    public static String getJoke() {
        Random rn = new Random();
        return jokeArray[rn.nextInt(jokeArray.length)];
    }

}

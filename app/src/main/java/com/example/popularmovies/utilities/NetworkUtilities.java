package com.example.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import com.example.popularmovies.BuildConfig;

import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtilities {

    private static final String TAG = "NetworkUtilities";

    public static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    public static final String LANGUAGE = "language";
    public static final String PAGE = "page";
    public static final String API_KEY = "api_key";


    public static String buildUrl(String category){
        Uri buildUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(category)
                .appendQueryParameter(API_KEY, BuildConfig.API_KEY)
                .appendQueryParameter(LANGUAGE, "en-US")
                .appendQueryParameter(PAGE, "1")
                .build();

        Log.d(TAG, "buildUrl: "+buildUri.toString());

        return buildUri.toString();
    }

}

package com.catenaxio;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by Antonio on 11/11/2015.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "lRLIrMRNzpnlw3O9VaoucfqBEOe8JD2Py3vzAfaN", "pyh7NMX2fIo18ripJooAAuZ9VG5HnI8qNOaDNd6G");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}

package com.ligue1.applicationligue1.util;


import android.app.Application;
import android.os.Parcelable;

public class MyApplication extends Application {

    private Parcelable someVariable;

    public Parcelable getSomeVariable() {
        return someVariable;
    }

    public void setSomeVariable(Parcelable someVariable) {
        this.someVariable = someVariable;
    }
}

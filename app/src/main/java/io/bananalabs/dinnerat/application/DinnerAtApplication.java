package io.bananalabs.dinnerat.application;

import android.app.Application;

import za.co.cporm.model.CPOrm;

/**
 * Created by EDC on 5/1/16.
 */
public class DinnerAtApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CPOrm.initialize(this);
    }
}

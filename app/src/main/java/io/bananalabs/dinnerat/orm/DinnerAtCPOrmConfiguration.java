package io.bananalabs.dinnerat.orm;

import java.util.ArrayList;
import java.util.List;

import io.bananalabs.dinnerat.models.Restaurant;
import za.co.cporm.model.CPOrmConfiguration;

/**
 * Created by EDC on 5/1/16.
 */
public class DinnerAtCPOrmConfiguration implements CPOrmConfiguration {
    public static final String DATABASE_NAME = "dinnerat.db";
    public static final int DATABASE_VERSION = 1;
    @Override
    public String getDatabaseName() {
        return DATABASE_NAME;
    }

    @Override
    public int getDatabaseVersion() {
        return DATABASE_VERSION;
    }

    @Override
    public boolean isQueryLoggingEnabled() {
        return false;
    }

    @Override
    public List<Class<?>> getDataModelObjects() {
        List<Class<?>> domainObjects = new ArrayList<>();
        domainObjects.add(Restaurant.class);
        return domainObjects;
    }
}

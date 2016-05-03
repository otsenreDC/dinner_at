package io.bananalabs.dinnerat.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import io.bananalabs.dinnerat.orm.DinnerAtCPOrmConfiguration;

/**
 * Created by EDC on 5/1/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, DinnerAtCPOrmConfiguration.DATABASE_NAME, null, DinnerAtCPOrmConfiguration.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}

package io.bananalabs.dinnerat;


import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import io.bananalabs.dinnerat.adapters.RestaurantAdapter;
import io.bananalabs.dinnerat.models.Restaurant;
import io.bananalabs.dinnerat.utils.DatabaseUtilities;
import io.bananalabs.dinnerat.utils.Utilities;
import za.co.cporm.model.loader.support.CPOrmLoader;
import za.co.cporm.model.query.Select;

public class MainActivity
        extends
        AppCompatActivity
        implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private RestaurantAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null)
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

        mAdapter = new RestaurantAdapter(this, null, 0);

        ListView listView = (ListView) findViewById(R.id.list_restaurants);
        if (listView != null) {
            listView.setAdapter(mAdapter);
            mAdapter.setOnReserveClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (view.getTag() != null) {
                        String url = (String) view.getTag();
                        Utilities.openUrl(MainActivity.this, url);
                    }
                }
            });
        }

        new DatabaseUtilities(this).populateDatabase();

        getSupportLoaderManager().initLoader(0, Bundle.EMPTY, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Select<Restaurant> select = Select
                .from(Restaurant.class)
                .sortAsc(Restaurant.Contract.COL_NAME);
        CPOrmLoader<Restaurant> loader = new CPOrmLoader<>(this, select);
        loader.setUpdateThrottle(2000);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }


}

package io.bananalabs.dinnerat;


import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import io.bananalabs.dinnerat.models.Restaurant;
import io.bananalabs.dinnerat.utils.DatabaseInitialization;
import za.co.cporm.model.loader.support.CPOrmLoader;
import za.co.cporm.model.query.Select;

public class MainActivity
        extends
        AppCompatActivity
        implements
        LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener{

    private SimpleCursorAdapter mAdapter;
    private ProgressBar mProgressBar;

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

        mAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                null,
                new String[]{Restaurant.Contract.COL_NAME, Restaurant.Contract.COL_CUISINE},
                new int[]{android.R.id.text1, android.R.id.text2},
                0
        );
        ListView listView = (ListView) findViewById(R.id.list_restaurants);
        if (listView != null) {
            listView.setAdapter(mAdapter);
            listView.setOnItemClickListener(this);
        }

        mProgressBar = (ProgressBar) findViewById(R.id.progress_loading);
        initializeData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Select<Restaurant> select = Select.from(Restaurant.class);//.sortAsc(Restaurant.Contract.COL_CUISINE, Restaurant.Contract.COL_NAME);
        CPOrmLoader<Restaurant> loader = new CPOrmLoader<>(this, select);
        loader.setUpdateThrottle(1000);
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

    private void initializeData() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressBar.setVisibility(View.VISIBLE);

            }

            @Override
            protected String doInBackground(Void... voids) {
                String string = DatabaseInitialization.initialize(getApplicationContext()) ? "Database loaded" : "Error loading database";
                return string;
            }

            @Override
            protected void onPostExecute(String result) {
                ListView fab = (ListView) findViewById(R.id.list_restaurants);
                if (fab != null)
                    Snackbar.make(
                            fab,
                            result,
                            Snackbar.LENGTH_SHORT)
                            .setAction("Action", null)
                            .show();

                getSupportLoaderManager().initLoader(0, Bundle.EMPTY, MainActivity.this);
                mProgressBar.setVisibility(View.GONE);
            }
        }.execute();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int positon, long id) {
        Cursor cursor = ((SimpleCursorAdapter)adapterView.getAdapter()).getCursor();
        if (cursor.moveToPosition(positon)) {
            long _id = cursor.getLong(cursor.getColumnIndex(Restaurant.Contract.COL_ID));
            lauchRestaurantActivity(_id);
        }
    }

    private void lauchRestaurantActivity(long id) {
        Intent intent = new Intent(this, RestaurantActivity.class);
        intent.putExtra(RestaurantActivity.RESTAURANT_ID, id);
        startActivity(intent);
    }
}

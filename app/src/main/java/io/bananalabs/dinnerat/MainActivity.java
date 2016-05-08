package io.bananalabs.dinnerat;


import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import io.bananalabs.dinnerat.adapters.RestaurantAdapter;
import io.bananalabs.dinnerat.models.Restaurant;
import io.bananalabs.dinnerat.utils.DatabaseUtilities;
import io.bananalabs.dinnerat.utils.Utilities;
import za.co.cporm.model.loader.support.CPOrmLoader;
import za.co.cporm.model.query.Select;

;

public class MainActivity
        extends
        AppCompatActivity
        implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private RestaurantAdapter mAdapter;
    private SimpleCursorAdapter mSuggestionAdapter;

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
        
        mSuggestionAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                null,
                new String[]{Restaurant.Contract.COL_NAME},
                new int[]{android.R.id.text1},
                0);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_activity, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menu_search));
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setSuggestionsAdapter(mSuggestionAdapter);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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
        mSuggestionAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
        mSuggestionAdapter.swapCursor(null);
    }


}

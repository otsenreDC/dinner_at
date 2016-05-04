package io.bananalabs.dinnerat;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import io.bananalabs.dinnerat.models.Restaurant;
import io.bananalabs.dinnerat.models.requests.RestaurantRequest;
import io.bananalabs.dinnerat.network.ClientUsage;
import io.bananalabs.dinnerat.network.ServerConnection;
import io.bananalabs.dinnerat.utils.Utilities;

public class RestaurantActivity
        extends
        AppCompatActivity
        implements
        Response.Listener<RestaurantRequest.RestaurantResponse>,
        Response.ErrorListener {

    public static final String RESTAURANT_ID = "RestaurantActivity:_id";

    private Restaurant mRestaurant;
    private List<RestaurantRequest.OpenTableRestaurant> restaurantsFound = new ArrayList<>();
    private ArrayAdapter<RestaurantRequest.OpenTableRestaurant> matchesAdapter;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        if (getIntent().hasExtra(RESTAURANT_ID)) {
            Long restaurantId = getIntent().getLongExtra(RESTAURANT_ID, -1);
            mRestaurant = Restaurant.findById(restaurantId);
        }

        if (mRestaurant != null) {
            TextView nameView = (TextView) findViewById(R.id.text_name);
            if (nameView != null)
                nameView.setText(mRestaurant.getName());

            TextView gradeView = (TextView) findViewById(R.id.text_grade);
            if (gradeView != null)
                gradeView.setText(mRestaurant.getGrade());

            TextView cityView = (TextView) findViewById(R.id.text_city);
            if (cityView != null)
                cityView.setText(mRestaurant.getCity());

            TextView streetView = (TextView) findViewById(R.id.text_street);
            if (streetView != null)
                streetView.setText(mRestaurant.getStreet());

            TextView buildingView = (TextView) findViewById(R.id.text_building);
            if (buildingView != null)
                buildingView.setText(mRestaurant.getBuilding());
        }

        ListView matchesList = (ListView) findViewById(R.id.list_matches);
        if (matchesList != null) {
            matchesAdapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_2,
                    android.R.id.text1,
                    restaurantsFound
            );
            matchesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Utilities.opentUrl(RestaurantActivity.this, restaurantsFound.get(position).mobileReserveUrl);
                }
            });
            matchesList.setAdapter(matchesAdapter);
            TextView noMatches = (TextView) findViewById(R.id.text_no_matches);
            matchesList.setEmptyView(noMatches);
        }

        mProgressBar = (ProgressBar) findViewById(R.id.progress_loading);
        /*
        ListView listView = (ListView) findViewById(R.id.list_hours);
        if (listView != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    R.layout.item_hours,
                    R.id.text_hour,
                    new RandomHours().randomSet());
            listView.setAdapter(adapter);
            View emptyView = findViewById(R.id.text_empty);
            listView.setEmptyView(emptyView);

        }*/

        try {
            ClientUsage.requestRestaurant(
                    this,
                    new RestaurantRequest(mRestaurant.getName(), mRestaurant.getState()),
                    this,
                    this,
                    this);
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        ServerConnection.getInstace(this).removeRequestWithTag(this);
        super.onDestroy();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        hideProgressBar();
        new AlertDialog.Builder(this).setMessage(error.getMessage()).show();
    }

    @Override
    public void onResponse(RestaurantRequest.RestaurantResponse response) {
        hideProgressBar();
        restaurantsFound.addAll(response.restaurants);
        matchesAdapter.notifyDataSetChanged();
    }

    private void hideProgressBar() {
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.INVISIBLE);
    }
}

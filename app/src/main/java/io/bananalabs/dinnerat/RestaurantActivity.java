package io.bananalabs.dinnerat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import io.bananalabs.dinnerat.models.Restaurant;
import io.bananalabs.dinnerat.utils.RandomHours;

public class RestaurantActivity extends AppCompatActivity {

    public static final String RESTAURANT_ID = "RestaurantActivity:_id";

    private Restaurant mRestaurant;

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

        ListView listView = (ListView) findViewById(R.id.list_hours);
        if (listView != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    R.layout.item_hours,
                    R.id.text_hour,
                    new RandomHours().randomSet());
            listView.setAdapter(adapter);
        }
    }
}

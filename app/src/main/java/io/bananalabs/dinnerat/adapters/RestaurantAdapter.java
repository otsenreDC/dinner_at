package io.bananalabs.dinnerat.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import io.bananalabs.dinnerat.R;
import io.bananalabs.dinnerat.models.Restaurant;

/**
 * Created by EDC on 5/8/16.
 */
public class RestaurantAdapter extends CursorAdapter implements SectionIndexer{

    private View.OnClickListener onReserveClickListener;
    private String[] sections;
    private HashMap<String, Integer> mMapIndex;

    public RestaurantAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        this.mMapIndex = new LinkedHashMap<>();
        createSections(c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_restaurant, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        viewHolder.reservationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onReserveClickListener != null) {
                    onReserveClickListener.onClick(view);
                }
            }
        });

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        String pictureUrl = cursor.getString(cursor.getColumnIndex(Restaurant.Contract.COL_IMAGE_URL));
        String name = cursor.getString(cursor.getColumnIndex(Restaurant.Contract.COL_NAME));
        String state = cursor.getString(cursor.getColumnIndex(Restaurant.Contract.COL_STATE));
        String city = cursor.getString(cursor.getColumnIndex(Restaurant.Contract.COL_CITY));
        String address = cursor.getString(cursor.getColumnIndex(Restaurant.Contract.COL_ADDRESS));
        String area = cursor.getString(cursor.getColumnIndex(Restaurant.Contract.COL_AREA));
        String phone = cursor.getString(cursor.getColumnIndex(Restaurant.Contract.COL_PHONE));
        String price = cursor.getString(cursor.getColumnIndex(Restaurant.Contract.COL_PRICE));
        String reserveUrl = cursor.getString(cursor.getColumnIndex(Restaurant.Contract.COL_MOBILE_RESERVE_URL));

        Picasso.with(context).load(pictureUrl).into(holder.pictureView);
        holder.nameView.setText(name);
        holder.stateView.setText(state);
        holder.cityView.setText(city);
        holder.addressView.setText(address);
        holder.areaView.setText(area);
        holder.phoneView.setText(phone);
        holder.priceView.setText(price);
        holder.reservationView.setTag(reserveUrl);

    }

    @Override
    public Cursor swapCursor(Cursor newCursor) {
        createSections(newCursor);
        return super.swapCursor(newCursor);
    }

    public void setOnReserveClickListener(View.OnClickListener onClickListener) {
        this.onReserveClickListener = onClickListener;
    }

    public static class ViewHolder {
        public final ImageView pictureView;
        public final TextView nameView;
        public final TextView stateView;
        public final TextView cityView;
        public final TextView addressView;
        public final TextView areaView;
        public final TextView phoneView;
        public final TextView priceView;
        public final Button reservationView;

        public ViewHolder(View view) {
            pictureView = (ImageView) view.findViewById(R.id.image_picture);
            nameView = (TextView) view.findViewById(R.id.text_name);
            stateView = (TextView) view.findViewById(R.id.text_state);
            cityView = (TextView) view.findViewById(R.id.text_city);
            addressView = (TextView) view.findViewById(R.id.text_address);
            areaView = (TextView) view.findViewById(R.id.text_area);
            phoneView = (TextView) view.findViewById(R.id.text_phone);
            priceView = (TextView) view.findViewById(R.id.text_price);
            reservationView = (Button) view.findViewById(R.id.button_reservation);
        }

    }

    private void createSections(Cursor cursor) {
        int index = 0;
        if (cursor != null) {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(Restaurant.Contract.COL_NAME));
                if (!name.isEmpty()) {
                    String chr = name.substring(0, 1);
//                    chr = chr.toUpperCase(Locale.US);
                    if (!mMapIndex.containsKey(chr))
                        mMapIndex.put(chr, index);
                }
                index++;
            }
        }

        Set<String> sectionLetters = mMapIndex.keySet();
        ArrayList<String> sectionList = new ArrayList<>(sectionLetters);
        Collections.sort(sectionList);
        sections = new String[sectionList.size()];
        sectionList.toArray(sections);
    }


    @Override
    public Object[] getSections() {
        return sections;
    }

    @Override
    public int getPositionForSection(int section) {
        return mMapIndex.get(sections[section]);
    }

    @Override
    public int getSectionForPosition(int i) {
        return 0;
    }
}

package io.bananalabs.dinnerat.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by EDC on 5/3/16.
 */
public class Utilities {
    public static boolean opentUrl(Activity context, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (url != null && browserIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(browserIntent);
            return true;
        }

        Toast.makeText(context, "No app available to handle this action", Toast.LENGTH_LONG).show();
        return false;


    }
}

package io.bananalabs.dinnerat.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by EDC on 5/2/16.
 */
public class RandomHours {
    private int[] hours = {10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22};
    private int[] minutes = {0, 30};

    public String randomHour() {
        int hoursSize = hours.length;
        int minutesSize = minutes.length;
        int randomHour = new Random().nextInt(hoursSize);
        int randomMinute = new Random().nextInt(minutesSize);
        int hour = hours[randomHour];
        int minute = minutes[randomMinute];

        return String.format(Locale.getDefault(), "%2d:%02d", hour, minute);
    }

    public List<String> randomSet() {
        int rand = new Random().nextInt(10);
        List<String> hours = new ArrayList<>();
        for (int index = 0; index < rand; index++) {
            hours.add(randomHour());
        }
        Collections.sort(hours);
        return hours;
    }
}

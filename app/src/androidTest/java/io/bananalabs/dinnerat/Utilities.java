package io.bananalabs.dinnerat;

/**
 * Created by EDC on 5/1/16.
 */
public class Utilities {

    public static void delay() {
        long delay = 20;
        long initialTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - initialTime < delay) ;
    }
}

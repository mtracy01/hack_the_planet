package htp.skout.Objects;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;

import htp.skout.MapResources.SyncedMapFragment;
import htp.skout.Tuple;
import htp.skout.frameworks.GPSThread;

/**
 * Created by Matthew on 8/15/2015.
 */
public class Global {
    public static User user = null;
    public static Context context = null;
    public static GPSThread gpsThread = null;
    public static GoogleMap map;
    public static SyncedMapFragment mapFragment;
    public static Activity mapActivity = null;

    //scoring

    public static double weatherPenaltyMultiplier;
    public static double weatherRewardMultiplier;
    public static double timePenaltyMultiplier;
    public static double timeRewardMultiplier;
    public static double lengthPenaltyMultiplier;
    public static Tuple<Integer, Integer> startTime;

    public static ArrayList<User> users = new ArrayList<>();
}

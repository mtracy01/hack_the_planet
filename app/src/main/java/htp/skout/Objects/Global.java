package htp.skout.Objects;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.maps.GoogleMap;

import htp.skout.MapResources.SyncedMapFragment;
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
}

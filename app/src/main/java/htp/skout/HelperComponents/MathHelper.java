package htp.skout.HelperComponents;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Matthew on 8/15/2015.
 */
public class MathHelper {
    public static double distance(LatLng a, LatLng b){
        return Math.sqrt(Math.pow(a.latitude-b.latitude,2) + Math.pow(a.longitude- b.longitude,2));
    }
}

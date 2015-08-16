package htp.skout.MapResources.MapRunner;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ext.SatelliteMenu;
import android.view.ext.SatelliteMenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import htp.skout.HelperComponents.MathHelper;
import htp.skout.MapResources.LatLngInterpolator;
import htp.skout.MapResources.Maps;
import htp.skout.MapResources.SyncedMapFragment;
import htp.skout.Objects.Global;
import htp.skout.Objects.User;
import htp.skout.R;
import htp.skout.frameworks.BackgroundTasks;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private String LOG_TAG = "MapActivity";
    private SyncedMapFragment map;

    private double MAX_DISTANCE = 50000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Global.mapActivity=this;

        //BackgroundTasks.geopoint.execute();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        SupportMapFragment mMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);

        if (mMapFragment == null) {
            mMapFragment = SupportMapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.map, mMapFragment).commit();
        }
        map = new SyncedMapFragment();
        setContentView(R.layout.activity_map);

        Maps.setCenterPosition(Global.user);
        //set up satellite menu
        //TODO:  Set up our satellite menu here... there are some package problems I'll deal with in a bit...
        SatelliteMenu satelliteMenu = new SatelliteMenu(this);

        List<SatelliteMenuItem> items = new ArrayList<>();
        items.add(new SatelliteMenuItem(0, R.drawable.ic_map));
        items.add(new SatelliteMenuItem(1, R.drawable.ic_bike));
        satelliteMenu.addItems(items);


        ///
        if(savedInstanceState==null){
            map = new SyncedMapFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.map,map).commit();
            Global.mapFragment = map;
            Global.mapFragment.getMapAsync(this);
        }

        AsyncTask t = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                while (true) {
                    try {
                        Thread.sleep(1500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.i(LOG_TAG, "Updating");
                    handleUpdates();
                }

            }
        }.execute();



    }

    @Override
    public void onMapReady(GoogleMap map) {
        Maps.readyMap(map);

    }

    public void handleUpdates(){
        //For now, this only handles one user, will later have to be updated

        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                if (Global.map == null) {
                    Log.e(LOG_TAG, "GLobal map not set!!!");
                    return;
                }
                LatLngInterpolator interpolator = new LatLngInterpolator.Linear();
                User u = Global.user;
                Log.e(LOG_TAG, "User: " + u.getUserName() + "Animating to: " + Global.user.getLocation().toString());
                Global.mapFragment.animateMarkerToGB(Global.user.getMarker(), Global.user.getLocation(), interpolator, 1500);

                Global.map.animateCamera(CameraUpdateFactory.newLatLng(Global.user.getLocation()), 1500, new GoogleMap.CancelableCallback() {
                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void onCancel() {

                    }
                });

                ParseGeoPoint point = new ParseGeoPoint(Global.user.getLocation().latitude, Global.user.getLocation().longitude);
                ParseUser.getCurrentUser().put("location", point);
                ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                    }
                });

                //TODO: Add code for animating global list of users here, removing them if they are outside of the radius

                ArrayList<User> inRangeUsers = new ArrayList<User>();
                LatLng centerPoint = Global.user.getLocation();
                int i=0;
                for(User user: Global.users){
                    if(!(MathHelper.distance(centerPoint, user.getLocation())> MAX_DISTANCE)){
                        inRangeUsers.add(user);
                        Marker m;
                        if((m=user.getMarker())!=null){
                            Global.mapFragment.animateMarkerToGB(m, user.getLocation(), interpolator, 1500);
                            inRangeUsers.add(user);
                        }
                        else{
                            //TODO: Set new marker, associate it with user, and display it on the map
                            if(user.getUserAvatar()==null) {
                                Log.e(LOG_TAG, "User didn't have a profile photo! Giving them default profile picture...");
                                Bitmap big = BitmapFactory.decodeResource(Global.mapActivity.getResources(), R.drawable.ic_launcher);
                                big = Bitmap.createScaledBitmap(big, big.getWidth() / 5, big.getHeight() / 5, false);
                                user.setAvatar(big);
                            }

                            Bitmap tmp = user.getUserAvatar();
                            Bitmap doubleSized = Bitmap.createScaledBitmap(tmp, tmp.getWidth() * 2, tmp.getHeight() * 2, false);
                            user.setMarker(Global.map.addMarker(new MarkerOptions()
                                    .position(user.getLocation())
                                    .icon(BitmapDescriptorFactory.fromBitmap(Maps.addBorder(doubleSized, Color.DKGRAY)))));
                        }
                    }
                    else{
                        //TODO: Remove the user's marker from the map so that it does not remain
                        user.getMarker().remove();
                    }
                    i++;
                }

            }
        });
    }


}

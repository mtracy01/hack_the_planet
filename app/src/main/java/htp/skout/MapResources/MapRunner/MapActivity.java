package htp.skout.MapResources.MapRunner;

import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import htp.skout.MapResources.LatLngInterpolator;
import htp.skout.MapResources.Maps;
import htp.skout.MapResources.SyncedMapFragment;
import htp.skout.Objects.Global;
import htp.skout.Objects.User;
import htp.skout.R;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private String LOG_TAG = "MapActivity";
    private SyncedMapFragment map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Global.mapActivity=this;

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

            }
        });
    }


}

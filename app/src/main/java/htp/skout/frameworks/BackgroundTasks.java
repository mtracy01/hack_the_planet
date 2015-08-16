package htp.skout.frameworks;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import htp.skout.GUI.MainActivity;
import htp.skout.Objects.Global;
import htp.skout.Objects.User;
import htp.skout.Tuple;
import htp.skout.Weather;

/**
 * Created by Matthew on 8/15/2015.
 */
public class BackgroundTasks {

    public static AsyncTask<Void, Void, Void> nearbyNeighbors = new AsyncTask<Void, Void, Void>() {
        @Override
        protected Void doInBackground(Void... voids) {
        while (true) {
            try {
                Thread.sleep(6000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put("user", ParseUser.getCurrentUser());

            ParseCloud.callFunctionInBackground("list", params, new FunctionCallback<JSONArray>() {
                public void done(JSONArray users, ParseException e) {
                if (e == null) {
                    //TODO things here

                    for (int i = 0; i < users.length(); i++) {

                        String username = "";
                        try {
                            username = users.getString(0);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                        JSONObject json = null;

                        try {
                            json = users.getJSONObject(1);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }

                        double lat = 0;
                        double lon = 0;
                        try {
                            lat = Double.valueOf(json.get("latitude").toString());
                            lon = Double.valueOf(json.get("longitude").toString());
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }

                        boolean added = false;

                        for (User user : Global.users) {
                            if (user.getUserName().equals(username)) {
                                user.setLocation(new LatLng(lat, lon));
                                added = true;
                            }
                        }

                        if (!added) {
                            //user wasn't found... adding them
                            User user = new User(username);
                            user.setLocation(new LatLng(lat, lon));
                            Global.users.add(user);
                        }

                    }

                }
                //TODO: Remove all markers except for the global user's
                // Add markers for each point into the map
                //for each json object,
                //parse out user name and location
                //if user exists in array:
                // update their locations
                //If not:
                // create a new user and add their marker to the map
                }
            });
        }
        }
    };


    public static AsyncTask<Void, Void, Void> geopoint = new AsyncTask<Void, Void, Void>() {
        @Override
        protected Void doInBackground(Void... voids) {
        while (true) {
            try {
                Thread.sleep(6000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            ParseGeoPoint point = new ParseGeoPoint(Global.user.getLocation().latitude, Global.user.getLocation().longitude);
            ParseUser.getCurrentUser().put("location", point);
            ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    e.printStackTrace();
                }
            });
        }
        }
    };

    public static AsyncTask<Void, Void, Void> weather = new AsyncTask<Void, Void, Void>() {
        @Override
        protected Void doInBackground(Void... voids) {
            while (true) {
                try {
                    Thread.sleep(5 * 60 * 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //get the weather

                Tuple<String, Double[]> weatherConditions = Weather.getWeatherInformation(Global.user.getLocation());

                //update the icon if needed

                //TODO: icon = Tuple[0]

                //push alerts if needed; Pebble

                double rewardMultiplier = 1;
                double penaltyMultiplier = 1;
                String weatherDesc = null;

                List<String> dangerousWeather = new ArrayList<String>();
                dangerousWeather.add("snow");
                dangerousWeather.add("sleet");
                dangerousWeather.add("thunder");
                dangerousWeather.add("smoke");
                dangerousWeather.add("haze");
                dangerousWeather.add("sand");
                dangerousWeather.add("dust");
                dangerousWeather.add("fog");
                dangerousWeather.add("volcanic");
                dangerousWeather.add("squalls");
                dangerousWeather.add("tornado");
                dangerousWeather.add("tropical storm");
                dangerousWeather.add("hurricane");
                dangerousWeather.add("hail");
                dangerousWeather.add("gale");
                dangerousWeather.add("storm");

                if (weatherConditions != null) {
                    for (String s : dangerousWeather) {
                        String desc = weatherConditions.getDesc();
                        if (desc.contains(s)) {
                            penaltyMultiplier = 5;
                            rewardMultiplier = 0.1;
                        }
                        else if (desc.contains("rain") || desc.contains("drizzle")) {
                            penaltyMultiplier = 2;
                            rewardMultiplier = 2;
                        }
                    }
                }
            }
        }
    };
}

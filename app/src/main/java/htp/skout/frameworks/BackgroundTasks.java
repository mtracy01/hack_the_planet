package htp.skout.frameworks;

import android.os.AsyncTask;

import com.parse.FunctionCallback;
import com.parse.Parse;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.json.JSONArray;

/**
 * Created by Matthew on 8/15/2015.
 */
public class BackgroundTasks {

    public static AsyncTask<Void,Void,Void> nearbyNeighbors = new AsyncTask<Void, Void, Void>() {
        @Override
        protected Void doInBackground(Void... voids) {
            while(true){
                try{
                    Thread.sleep(12000);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                /*ParseCloud.callFunctionInBackground("list", ParseUser.getCurrentUser(), new FunctionCallback<JSONArray>() {
                    public void done(JSONArray users, ParseException e) {
                        if (e == null) {
                            // ratings is 4.5
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
                });*/
            }
        }
    };
}

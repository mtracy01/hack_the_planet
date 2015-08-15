package htp.skout;

//Zach Perry - hack the planet
//ZachPurdue github

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class ScoreScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_score_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Ride Breakdown-------------------------------------------------------------------------------

    public float getDistance() { //return distance of trip in miles (mi)
        return 89.41; //placeholder
    }

    public int getDuration() { //return the duration of the ride in minutes (m), rounded down
        //distance / inverse of average speed * 60 == duration
        return 150; //placeholder
    }

    public float getAvgSpeed() { //return average speed of the trip in miles per hour (mph)
        return 35.64 //placeholder
    }

    public float getTopSpeed() { //return top speed of trip in miles per hour (mph)
        return 72.43; //placeholder
    }

    //Violations Breakdown-------------------------------------------------------------------------

    public int getViolations() { //return amount of times the speed limit was violated
        int n = 4;
        if (n = 0) {
            return 0;
            //if no violations, show some other congratulationary message
        }
        else {
            return n; //placeholder
        }
    }

    public float getAverageViolation() { //return average speed above speed limit per violation
        return 3.64; //placeholder
    }

    //Score----------------------------------------------------------------------------------------
    /*
    Uses the scoring algorithm to assign the rider a score based on distance, speed, and
    violations during the ride.
     */
    /*
    In the end, algorithm should take all collected data points into account for a score that
    is as accurate as possible.
     */
    public int rideScore() { //method yet to be decided
        return 1124; //placeholder
    }

}

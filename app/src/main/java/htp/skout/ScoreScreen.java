package htp.skout;

//Created by Zach Perry, 8/15/15 - hack the planet 2015
//ZachPurdue github

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class ScoreScreen extends ListActivity {

    TextView score, duration, average_speed, top_speed, times_over_limit, average_over, highest_over;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_screen);

        score = (TextView) findViewById(R.id.score1);
        duration = (TextView) findViewById(R.id.score2);
        average_speed = (TextView) findViewById(R.id.score3);
        top_speed = (TextView) findViewById(R.id.score4);
        times_over_limit = (TextView) findViewById(R.id.score5);
        average_over = (TextView) findViewById(R.id.score6);
        highest_over = (TextView) findViewById(R.id.score7);

        score.setText("Ride Score: " + getScore());
        duration.setText("Duration of Ride: " + getDuration());
        average_speed.setText("Average Speed: " + getAvgSpeed());
        top_speed.setText("Top Speed: " + getTopSpeed());
        times_over_limit.setText("Times Over Limit: " + getViolations());
        average_over.setText("Average Speed Over: " + getAverageViolation());
        highest_over.setText("Highest Speed Over: " + getHighestViolation());

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

        return 89.41f; //placeholder
    }

    public int getDuration() { //return the duration of the ride in minutes (m), rounded down
        //distance / inverse of average speed * 60 == duration

        int n = getDistance()/(1/getAvgSpeed());

        return n; //placeholder
    }

    public float getAvgSpeed() { //return average speed of the trip in miles per hour (mph)
        return 35.64f; //placeholder

    }

    public float getTopSpeed() { //return top speed of trip in miles per hour (mph)

        return 72.43f; //placeholder
    }

    //Violations Breakdown-------------------------------------------------------------------------

    public int getViolations() { //return amount of times the speed limit was violated
        int n = 4;
        if (n == 0) {
            return 0;
            //if no violations, show some other congratulationary message
        } else {
            return n; //placeholder
        }
    }

    public float getAverageViolation() { //return average speed above speed limit per violation

        return 3.64f; //placeholder
    }

    public float getHighestViolation() { //return highest speed above speed limit
        return 12.43f; //placeholder
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
    public int getScore() { //method yet to be decided

        return 1124; //placeholder
    }

}

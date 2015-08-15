package htp.skout.GUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import htp.skout.R;

import htp.skout.Objects.Global;

/**
 * Created by Jeroen Goossens for Hack The Planet 2015
 */

public class Signup extends AppCompatActivity {


    private EditText username;
    private EditText pw1;
    private EditText pw2;
    private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = (EditText) findViewById(R.id.editText3);
        pw1 = (EditText) findViewById(R.id.editText4);
        pw2 = (EditText) findViewById(R.id.editText5);
        signup = (Button) findViewById(R.id.button3);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pw1.getText().toString().equals(pw2.getText().toString())) {

                    ParseUser user = new ParseUser();
                    user.setUsername(username.getText().toString());
                    user.setPassword(pw1.getText().toString());

                    ParseGeoPoint point = new ParseGeoPoint(Global.user.getLocation().latitude, Global.user.getLocation().longitude);
                    user.put("location", point);


                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                // Hooray! Let them use the app now.

                                Intent intent = new Intent(Signup.this, MainActivity.class);
                                startActivity(intent);
                                finish();


                            } else {
                                // Sign up didn't succeed. Look at the ParseException
                                // to figure out what went wrong
                                Context context = getApplicationContext();
                                CharSequence text = "Username already taken! Sorry!";
                                int duration = Toast.LENGTH_SHORT;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }
                        }
                    });
                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "Messed up the passwords!!!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
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
}

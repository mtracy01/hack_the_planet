package htp.skout;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import htp.skout.GUI.MainActivity;

public class Signup extends AppCompatActivity {


    private EditText username;
    private EditText password;
    private EditText pw2;
    private Button link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        username = (EditText) findViewById(R.id.editText6);
        password = (EditText) findViewById(R.id.editText7);
        pw2 = (EditText) findViewById(R.id.editText8);
        link = (Button) findViewById(R.id.button4);

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(username.getText().length()>0 && password.getText().length()>0&&pw2.getText().length()>0 && password.getText().toString().equals(pw2.getText().toString())){
                    ParseUser user = new ParseUser();
                    user.setUsername(username.getText().toString());
                    user.setPassword(pw2.getText().toString());

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
                                CharSequence text = "Invalid username and/or password combination...";
                                int duration = Toast.LENGTH_SHORT;

                                Toast toast = Toast.makeText(context, text, duration);
                                toast.show();
                            }
                        }
                    });
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

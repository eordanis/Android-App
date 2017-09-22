package com.androiddevelopment.sembasolutions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.CountDownTimer;

public class MainActivity extends AppCompatActivity {

    //counter to restrict login attempt to 3
    int loginCounter;
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hardcoded login credentials
        final String staticUsername = "a";
        final String staticPassword = "b";

        //create variable to hold login attempts counter
        loginCounter = 3;

        //reference EditText & Button Fields
        final EditText usernameET = (EditText) findViewById(R.id.usernameET);
        final EditText passwordET = (EditText) findViewById(R.id.passwordET);
        final Button loginB = (Button) findViewById(R.id.loginButton);

        // Register login button with Event Listener class, and Event Handler method
        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // grab username and password from edit text fields
                username = usernameET.getText().toString();
                password = passwordET.getText().toString();

                //as long as loginCounter is greater than 1
                if (loginCounter > 1) {


                    //if textfield is empty
                    if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                        //display toast message to user asking them to fill in textfields
                        Toast.makeText(MainActivity.this, "Please enter a username and password to continue", Toast.LENGTH_SHORT).show();
                    } else {


                        //check if login credentials match those on file
                        if (staticUsername.equals(username) && staticPassword.equals(password)) {

                            //create an intent object passing current activity and activity you want to go to
                            Intent myIntentH = new Intent(MainActivity.this, userHome.class);
                            MainActivity.this.startActivity(myIntentH); //start activity

                            //display toast message to user that login was successful
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        } else {

                            loginCounter--;//decrease loginCounter

                            //tell user via toast that credentials are invalid and provide attempts remaining
                            Toast.makeText(MainActivity.this, "Login Credentials Invalid! Attempts Remaining: " + loginCounter, Toast.LENGTH_SHORT).show();

                        }//end if username & password are invalid

                    }//end if loginCounter
                }
                else {

                    //initialize a counter to delay shutting downing
                    new CountDownTimer(10000, 1000) {

                        public void onTick(long millisUntilFinished) {

                            //provide warning of shutting down to user via toast
                            Toast.makeText(MainActivity.this, "Login Credentials Invalid! Max Attempts Exceeded. Shutting Down in..." + millisUntilFinished / 1000, Toast.LENGTH_LONG).show();
                        }

                        public void onFinish() {

                            //shutdown system
                            System.exit(0);

                        }// end onFinish
                    }.start();

                }//end if loginCounter exceeds login attempts

            }//end onclick

        }); //end event listener

    }//end onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate the menu; this adds items to action bar if present
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }//end onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle action bar item clicks here. The action bar will
        //automatically handle clicks on the Home/Up button, so long
        //as you specify a parent activity in AndroidManifest.xml

        int id = item.getItemId();

        switch (id){

            case R.id.action_quit:
                if(item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);

                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                return true;
        }//end switch

        return super.onOptionsItemSelected(item);
    }//end onOptionsItemSelected


}//end MainActivity Class

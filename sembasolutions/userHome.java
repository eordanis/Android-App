package com.androiddevelopment.sembasolutions;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class userHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        //call checking and savings button functions
        checkingButton();
        savingsButton();

    }//end onCreate


    //method to create onclick listener for checking button, bring user to checking page upon clicking
    public void checkingButton() {

        //Reference checkingButton
        final Button checkingB = (Button) findViewById(R.id.checkingButton);

        //create onclick listener for checkingbutton
        checkingB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create an intent object passing current activity and activity you want to go to
                Intent myIntentC = new Intent(userHome.this, CheckingActivity.class);
                userHome.this.startActivity(myIntentC); //start activity

            }//end onclick

        }); //end event listener

    }//end checkingButton method



    //method to create onclick listener for savings button, bring user to savings page upon clicking
    public void savingsButton() {

        //reference savings Button Field
        final Button savingsB = (Button) findViewById(R.id.savingsButton);

        //create onclick listener for savingsbutton
        savingsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create an intent object passing current activity and activity you want to go to
                Intent myIntentS = new Intent(userHome.this, SavingsActivity.class);
                userHome.this.startActivity(myIntentS); //start activity

            }//end onclick

        }); //end event listener

    }//end savingsButton method


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


}//endUserHome Class

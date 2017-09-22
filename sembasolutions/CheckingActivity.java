package com.androiddevelopment.sembasolutions;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class CheckingActivity extends AppCompatActivity {

    //create public variables for sharePreferences
    public final static String MY_PREFS ="my_prefs";
    public final static String CHECKING_KEY ="checking_key";
    public final static String SAVINGS_KEY ="savings_key";
    SharedPreferences.Editor myEditor;
    public double checkingBalance, savingsBalance, tempBalance;
    public int emptyField, radioButtonSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking);

        //initialize radioButton as 0
        radioButtonSelected = 0;

        //call updateCheckingTV and submitButton functions
        updateCheckingBalanceTV();
        submitButton();

    }//end onCreate

    //update checkingBalanceTV
    public void updateCheckingBalanceTV(){

            //reference checkingBalanceTV  textfield
            TextView checkBalanceTV = (TextView) findViewById(R.id.checkingBalanceTV);

            //get shared preferences
            SharedPreferences checkingPref = getSharedPreferences(CheckingActivity.MY_PREFS, MODE_PRIVATE);

            //get CHECKING_KEY from sharedPrefs and convert to double then place in checkingBalance
            checkingBalance = Double.longBitsToDouble(checkingPref.getLong(CHECKING_KEY, 100));

            // Create a DecimalFormat object with one decimal place
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator('\'');
            symbols.setDecimalSeparator('.');

            DecimalFormat decimalFormat = new DecimalFormat("$ #,###.00", symbols);
            String checkingBalanceString = decimalFormat.format(checkingBalance);

            //set checkingBalanceTV textView to the users current balance
            checkBalanceTV.setText(checkingBalanceString);

    }//end updateCheckingBalanceTV method


    //withdraw from checking method
    //accepts one argument
    //deducts userInput amount from balance if sufficient funds
    //stores in a variable until submit button is pressed to commit action
    public Double withdrawChecking( Double currbalance){

        //reference checkingBalanceTV  textfield
        EditText userInput = (EditText) findViewById(R.id.checkingEditText);

        // Convert entered value into a double, and assign to userInputValue variable
         Double userInputValue =  Double.parseDouble(userInput.getText().toString());

        //if userInputValue is less than current balance, subtract funds and store in variable
        if (currbalance > userInputValue){
            currbalance = currbalance - userInputValue;
        }//end if

        else {
            Toast.makeText(CheckingActivity.this, "Insufficient Funds", Toast.LENGTH_SHORT).show();
        }//end else if not enough funds to withdraw

        return currbalance;

    }//end updateCheckingBalanceTV method

    //deposit in checking method
    //accepts one argument
    //adds userInput amount to checking balance
    //stores in a variable until submit button is pressed to commit action
    public Double depositChecking( Double currbalance){

        //reference checkingBalanceTV  textfield
        EditText userInput = (EditText) findViewById(R.id.checkingEditText);

        // Convert entered value into a double, and assign to userInputValue variable
        Double userInputValue =  Double.parseDouble(userInput.getText().toString());

            currbalance = currbalance + userInputValue;

        return currbalance;

    }//end updateCheckingBalanceTV method


    //update checking account balance to tempBalance value
    public void updateChecking(){

            //if sharePreferences does not exist, create it and populate with checking and savings balance
            myEditor = getSharedPreferences(MY_PREFS, MODE_PRIVATE).edit();

            //put checkingBalance in CHECKING_KEY in shared preferences, convert to long
            myEditor.putLong(CHECKING_KEY, Double.doubleToLongBits(tempBalance));
            myEditor.apply();

            //assign tempBalance to checkingBalance
            checkingBalance = tempBalance;

    }//end createSharedPreference method


    //method to create onclick listener for submit button
    public void submitButton() {

        //reference submit Button Field
        final Button submitB = (Button) findViewById(R.id.checkingSubmitbutton);

        //create onclick listener for submitbutton
        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emptyField = checkNotEmpty();

                if(emptyField==0){
                    Toast.makeText(CheckingActivity.this, "Please Enter An Amount In TextBox ", Toast.LENGTH_SHORT).show();

                } else {

                    //check to see what radioButton Was selected

                    //if radioButtonSelected equals 0, no option was chosen
                    if (radioButtonSelected == 0) {

                        //ask user to make a selection
                        Toast.makeText(CheckingActivity.this, "Please Select A Radiobutton Choice ", Toast.LENGTH_SHORT).show();

                    }
                    //if radioButtonSelected equals 1, withdraw option was chosen
                    else if (radioButtonSelected == 1) {

                        //withdraw funds for user if sufficient funds exist
                        //call withdawChecking method and pass checkingBalance as parameter
                        //place in tempBalance
                        tempBalance = withdrawChecking(checkingBalance);

                        //updateChecking balance by calling the updateChecking function
                        updateChecking();

                        //updateCheckingBalance textview
                        updateCheckingBalanceTV();

                    }//end withdraw

                    //if radioButtonSelected equals 2, deposit option was chosen
                    else if (radioButtonSelected == 2) {

                        //deposit funds for user
                        //call depositChecking method and pass checkingBalance as parameter
                        //place in tempBalance
                        tempBalance = depositChecking(checkingBalance);

                        //updateChecking balance by calling the updateChecking function
                        updateChecking();

                        //updateCheckingBalance textview
                        updateCheckingBalanceTV();

                    }//end deposit

                    else if (radioButtonSelected == 3) {
                        //transfer
                        //withdraw funds from user checking if sufficient funds exist
                        //call withdawChecking method and pass checkingBalance as parameter
                        //place in tempBalance
                        tempBalance = withdrawChecking(checkingBalance);

                        //deposit funds into savings account balance
                        //grab savings account balance via SAVINGS_KEY in shared preferences
                        //get shared preferences
                        SharedPreferences savingsPref = getSharedPreferences(CheckingActivity.MY_PREFS, MODE_PRIVATE);

                        //get SAVINGS_KEY from sharedPrefs and convert to double then place in savingsBalance
                        savingsBalance = Double.longBitsToDouble(savingsPref.getLong(SAVINGS_KEY, 0));

                        //get userInput value and add to savingsBalance
                        //reference checkingBalanceTV  textfield
                        EditText userInput = (EditText) findViewById(R.id.checkingEditText);

                        // Convert entered value into a double, and assign to userInput variable
                        Double userInputValue =  Double.parseDouble(userInput.getText().toString());

                        savingsBalance = savingsBalance + userInputValue;

                        //create shared preferences editor
                        myEditor = getSharedPreferences(MY_PREFS, MODE_PRIVATE).edit();

                        //put savingsBalance in SAVINGS_KEY in shared preferences, convert to long
                        myEditor.putLong(SAVINGS_KEY, Double.doubleToLongBits(savingsBalance));
                        myEditor.apply();

                        //updateChecking balance by calling the updateChecking function
                        updateChecking();

                        //updateCheckingBalance textview
                        updateCheckingBalanceTV();

                    }//end transfer


                }//field is not empty


            }//end onclick

        }); //end event listener

    }//end savingsButton method



    //method to check if a radio button is clicked
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.withdrawCRB:
                if (checked)
                    radioButtonSelected = 1;
                    break;
            case R.id.depositCRB:
                if (checked)
                    radioButtonSelected = 2;
                    break;
            case R.id.transferCRB:
                if (checked)
                    radioButtonSelected = 3;
                break;
        }//end check which radio button is checked

    }//end onRadioButtonClicked


    //check to see if userinput field is not empty
    //if it is not empty put a 0 in the variable and return it, else a 1
    public int checkNotEmpty(){
        //reference checkingBalanceTV  textfield
        EditText userInput = (EditText) findViewById(R.id.checkingEditText);

        //get text from edittext field
        String struserInput = userInput.getText().toString();

        int empty; //0 if empty, 1 if filled

        //if textfield is empty, set empty to 0, else a 1
        if(TextUtils.isEmpty(struserInput)) {
            empty = 0;
        } else {
            empty = 1;
        }
        return empty;
    }//end checkNotEmpty method


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


}//end CheckingActivity




package com.example.nschool.myapplicationbus;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Activity for user registration
 * if user already exist shows toast "user already exist"
 * else the input details are stored in Database
 **/
public class RegistrationActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    private InputValidation inputValidation;                                 // object to validate the input
    private DbHelper databaseHelper;                                         // object for database
    private User user;                                                      // object for Class user
    private final AppCompatActivity activity = RegistrationActivity.this;   // object for current activity
    private String[] country = {"India", "USA", "China", "Japan", "Other",}; //country names to display in spinner
    private String country_name;                                            //variable to store country name after selected
    //objects for TextInputLayout
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutPhone;
    //objects for TextInputEditText
    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextPhone;
    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;
    private NestedScrollView nestedScrollView;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);              //designing page for Login
        initObjects();                                               //method to object initialization for widgets
        spinner.setOnItemSelectedListener(this);
        // Adapter for spinner to show country list
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, country);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);                                //Setting the ArrayAdapter data on the Spinner
        //onclick method to validate and if success navigate to next page
        appCompatButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                postDataToSQLite();                                //verify data from Database
            }
        });
    }
    
    //method to validate input and if validation success then insert data into database
    private void postDataToSQLite() {
        //to check whether the input is empty or not if empty show error messages
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))
                && !inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))
                && !inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))
                && !inputValidation.isInputEditTextFilled(textInputEditTextPhone, textInputLayoutPhone, getString(R.string.error_message_phone))) {
            return;
        }
        //to check whether the input is valid email id, if  not show error messages "invalid email"
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        //to check whether the input is valid email id, if  not show error messages "invalid email"
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        //to check whether the input is valid password id, if  not show error messages "invalid password"
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        //to check whether the input is valid phone number id, if  not show error messages "invalid phone number"
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPhone, textInputLayoutPhone, getString(R.string.error_message_phone))) {
            return;
        }
        //to check whether the user already exist or not if exist show message "user already exist" else insert data in database
        if (!databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {
            user.setName(textInputEditTextName.getText().toString().trim());
            user.setEmail(textInputEditTextEmail.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());
            databaseHelper.addUser(user);                                                       // add user in database
            emptyInputEditText();                                                               //to clear the fields
            Toast.makeText(activity, "Registered Successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(i);                                                               // insertion suceess full so goto login page
        } else {
            // Toast to show error message that record already exists
            Toast.makeText(activity, "User already exist", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(i);
        }
    }
    
     /**
     * This method is to empty all input fields
     */
    private void emptyInputEditText() {
        textInputEditTextName.setText("");
        textInputEditTextEmail.setText("");
        textInputEditTextPassword.setText("");
        textInputEditTextPhone.setText("");
    }
    
     /**
     * This method is to intialize all the widgets objects
     */
    private void initObjects() {
        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutMail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPass);
        textInputLayoutPhone = (TextInputLayout) findViewById(R.id.textInputLayoutPhone);
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);
        textInputEditTextName = (TextInputEditText) findViewById(R.id.edtuser);
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.edtemail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.edtpass);
        textInputEditTextPhone = (TextInputEditText) findViewById(R.id.edtphone);
        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.btnSignUp);
        spinner = (Spinner) findViewById(R.id.spinner);
        inputValidation = new InputValidation(activity);
        databaseHelper = new DbHelper(activity);
        user = new User();
    }
   
    /**
     * This method is to select country from country list spinner and store the value in country_name
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        country_name = country[position];
    }
  
    /**
     *This method is to show default country name when nothing selected
       */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        country_name = country[0];
    }
}

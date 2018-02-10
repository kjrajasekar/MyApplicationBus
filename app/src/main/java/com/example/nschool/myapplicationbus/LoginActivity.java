package com.example.nschool.myapplicationbus;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Activity for user Login
 * if user already exist they can login after successful validation
 * else they can register their details in database
 **/
public class LoginActivity extends AppCompatActivity {
    private final AppCompatActivity activity = LoginActivity.this;  // object for current activity
    private DbHelper databaseHelper;                                // object for database
    private InputValidation inputValidation;                        // object to validate the input
    private User user;                                              // object for Class user
    //objects for TextInputLayout
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    //objects for TextInputEditText
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private AppCompatButton appCompatButtonLogin,
            appCompatButtonLoginfb;
    private TextView txtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);     //designing page for Login
        initObjects();                                    //method to object initialization for widgets
        //onclick method to navigate registration activity
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(homepage);
            }
        });
        //onclick method to validate and if successful login navigate to next page
        appCompatButtonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                verifyFromSQLite();
            }
        });
        //onclick method to validate and if successful login using facebook login details
        appCompatButtonLoginfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //write code here for facebook login
            }
        });
    }

    /**
     * This method is to validate input and if validation success then
     * verify input from Database
     */
    private void verifyFromSQLite() {
        //to check whether the input is empty or not if empty show error messages "invalid input"
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email)) && !inputValidation.isInputEditTextPassFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        //to check whether the input is valid email or not if its not valid  show error messages "invalid email"
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        //to check whether the input is valid password or not if its not valid show error messages "invalid password"
        if (!inputValidation.isInputEditTextPassFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }

        /**
         * to check whether the user already exist or not
         * if not exist show message "invalid login"
         * else if login successful then navigate to next page
         */
        if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim())) {
            String email = textInputEditTextEmail.getText().toString();
            String password = textInputEditTextPassword.getText().toString();
            Intent accountsIntent = new Intent(activity, SplashActivity.class);           // navigate to next page
            accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
            emptyInputEditText();                                                       //to clear the fields
            startActivity(accountsIntent);                                              // navigate to next page
        } else {
            // Toast to show message that input is wrong
            Toast.makeText(activity, "Invalid Login", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is to intialize all the widgets objects
     */
    private void initObjects() {
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPass);
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.logemail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.logpassword);
        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.btnLogin);
        appCompatButtonLoginfb = (AppCompatButton) findViewById(R.id.btnLogin_fb);
        txtRegister = (TextView) findViewById(R.id.textRegister);
        databaseHelper = new DbHelper(activity);
        user = new User();
        inputValidation = new InputValidation(activity);
    }

    /**
     * This method is to empty all input fields
     */
    private void emptyInputEditText() {
        textInputEditTextEmail.setText("");
        textInputEditTextPassword.setText("");
    }
}

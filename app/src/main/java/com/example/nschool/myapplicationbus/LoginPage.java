package com.example.nschool.myapplicationbus;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import org.w3c.dom.Text;

import static com.basgeekball.awesomevalidation.ValidationStyle.UNDERLABEL;

public class LoginPage extends AppCompatActivity {
    private final AppCompatActivity activity = LoginPage.this;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private DbHelper databaseHelper;
    private User user;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private InputValidation inputValidation;
    private AppCompatButton appCompatButtonLogin, appCompatButtonLoginfb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);   //designing page for Login
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPass);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.logemail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.logpassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.btnLogin);
        appCompatButtonLoginfb = (AppCompatButton) findViewById(R.id.btnLogin_fb);

        TextView txtRegister = (TextView) findViewById(R.id.textRegister);   //Register button

        initObjects();
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepage = new Intent(getApplicationContext(), Registration.class); //navigation to next page
                startActivity(homepage);
            }
        });

        //onclick method to validate and if success navigate to next page
        appCompatButtonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // validating user input
                verifyFromSQLite();
            }
        });

        appCompatButtonLoginfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validating user input

            }
        });
    }

    // validate input and verify input from Database
    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email)) && !inputValidation.isInputEditTextPassFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextPassFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }

        if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim())) {
            String email = textInputEditTextEmail.getText().toString();
            String password = textInputEditTextPassword.getText().toString();


            Intent accountsIntent = new Intent(activity, SplashScreen.class);
            accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);


        } else {
            // Toast to show success message that record is wrong
            Toast.makeText(activity, "Invalid Login", Toast.LENGTH_SHORT).show();
        }
    }

    private void initObjects() {
        databaseHelper = new DbHelper(activity);
        user = new User();
        inputValidation = new InputValidation(activity);

    }

    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
}

package com.example.nschool.myapplicationbus;

import android.content.Intent;
import android.support.design.widget.Snackbar;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Registration extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    private InputValidation inputValidation;
    private final AppCompatActivity activity = Registration.this;
    String[] country = {"India", "USA", "China", "Japan", "Other",}; //country names to display in spinner
    String cou_name;        //string to store country name after selected
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutPhone;
    private DbHelper databaseHelper;
    private User user;
    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextPhone;
    Spinner spin;
    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;
    private NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);   //designing page for Login


        //object initialization for widgets

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


        spin = (Spinner) findViewById(R.id.spinner);          //spinner initialization
        initObjects();                                          //object initialization
        spin.setOnItemSelectedListener(this);

        // set adapter  to spinner to show country list
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, country);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(adapter);

        //onclick method to validate and if success navigate to next page
        appCompatButtonRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                postDataToSQLite();         //verify data from Database
            }
        });


    }
//validate input and insert into database
    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))
                && !inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))
                && !inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))
                && !inputValidation.isInputEditTextFilled(textInputEditTextPhone, textInputLayoutPhone, getString(R.string.error_message_phone))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }

        if(!inputValidation.isInputEditTextMatches(textInputEditTextPhone,textInputLayoutPhone,getString(R.string.error_message_phone )))
        {
            return;
        }

        if (!databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {

            user.setName(textInputEditTextName.getText().toString().trim());
            user.setEmail(textInputEditTextEmail.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());

            databaseHelper.addUser(user);


            emptyInputEditText();
            // insertion suceess full so goto login page
            Toast.makeText(activity, "Registered Successfully", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(Registration.this,LoginPage.class);
            startActivity(i);

        } else {
            // Toast to show error message that record already exists
            Toast.makeText(activity, "User already exist", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(Registration.this,LoginPage.class);
            startActivity(i);

        }
}


    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextPhone.setText(null);
    }
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DbHelper(activity);
        user = new User();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        cou_name = country[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

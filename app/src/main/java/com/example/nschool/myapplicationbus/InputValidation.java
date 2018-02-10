package com.example.nschool.myapplicationbus;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Nschool on 2/9/2018.
 */

public class InputValidation {
    private Context context;            //context for get application context

     public InputValidation(Context context) {
        this.context = context;
    }

    /**
     * method to check InputEditText filled or not.
     * if not reurn false and set error message to that input edit text
     * @param textInputEditText
     * @param textInputLayout
     * @param message to show error message
     * @return true/false
     */
    public boolean isInputEditTextFilled(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message) {
        String value = textInputEditText.getText().toString().trim();
        if (value.isEmpty()) {
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * method to check InputEditText has valid email .
     * if not reurn false and set error message to email field
     * @param textInputEditText is field of email
     * @param textInputLayout is layout of email
     * @param message contains value "invalid email"
     * @return true/false
     */
    public boolean isInputEditTextEmail(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String message) {
        String value = textInputEditText.getText().toString().trim();
        if ( !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            textInputLayout.setError(message);
            hideKeyboardFrom(textInputEditText);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * method to check InputEditText has valid phone number .
     * if not reurn false and set error message to phone number field
     * @param textInputEditText1 is field of phone number
     * @param textInputLayout is layout of phone number
     * @param message contains value "invalid phone number"
     * @return true/false
     */
    public boolean isInputEditTextMatches(TextInputEditText textInputEditText1, TextInputLayout textInputLayout, String message) {
        String value1 = textInputEditText1.getText().toString().trim();
        if (value1.length()<10) {                   //to check phone number length is 10 or not
            textInputLayout.setError(message);     // phone < 10 means set error message
            hideKeyboardFrom(textInputEditText1);
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * method to Hide keyboard
     *
     * @param view is object of Parent view from activity
     */
    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * method to check InputEditText has valid phone number .
     * if not reurn false and set error message to phone number field
     * @param textInputEditTextPassword is field of password
     * @param textInputLayoutPassword   is layout of password
     * @param message contains value "invalid password"
     * @return true/false
     */
    public boolean isInputEditTextPassFilled(TextInputEditText textInputEditTextPassword, TextInputLayout textInputLayoutPassword, String message) {
        String value = textInputEditTextPassword.getText().toString().trim();
        if (value.isEmpty()) {                            //to check password field is empty or not
            textInputLayoutPassword.setError(message);      // if not then set error message
            hideKeyboardFrom(textInputEditTextPassword);
            return false;
        } else {
            textInputLayoutPassword.setErrorEnabled(false);
        }
        return true;
    }
}



package com.example.piotrek.yami;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.piotrek.yami.data.RegisterData;
import com.example.piotrek.yami.data.User;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Piotrek on 2015-01-17.
 */
@EActivity (R.layout.register)
public class Register extends ActionBarActivity{
    private static final String PREFS = "appPrefs";
    @Bean
    @NonConfigurationInstance
    RestRegisterBackgroundTask restRegisterBackgroundTask;
    @ViewById
    EditText nameRegister;
    @ViewById
    EditText surnameRegister;
    @ViewById
    EditText emailRegister;
    @ViewById
    EditText passwordRegister;
    @ViewById
    ProgressBar progressbar;
    @ViewById
    TextView rate;
    @ViewById
    EditText retypeRegister;

    ProgressDialog ring;
    @AfterViews
    void init()
    {
        ring = new ProgressDialog(this);
        ring.setMessage("Trwa rejestracja");
        ring.setIndeterminate(true);

        passwordRegister.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable x) {
            }

            public void beforeTextChanged(CharSequence x, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence x, int start,
                                      int before, int count) {

                if (x.length() != 0) {
                    String znaki = "!@#$%^&*()";
                    int wynik = 0;
                    if (passwordRegister.getText().toString().length() < 6) {
                        rate.setText("Minimum 6 znaków!");
                    }
                    if (passwordRegister.getText().toString().length() >= 6) {
                        int n;
                        for (n = 0; n < passwordRegister.getText().toString().length(); n++) {
                            if (Character.isLowerCase(passwordRegister.getText().toString().charAt(n))) {
                                wynik = wynik + 1;
                                break;
                            }
                        }
                        for (n = 0; n < passwordRegister.getText().toString().length(); n++) {
                            if (Character.isDigit(passwordRegister.getText().toString().charAt(n))) {
                                wynik = wynik + 1;
                                break;
                            }
                        }
                        for (n = 0; n < passwordRegister.getText().toString().length(); n++) {
                            if (Character.isUpperCase(passwordRegister.getText().toString().charAt(n))) {
                                wynik = wynik + 1;
                                break;
                            }
                        }
                        for (n = 0; n < passwordRegister.getText().toString().length(); n++) {
                            if (znaki.indexOf(passwordRegister.getText().toString().charAt(n)) != -1) {
                                wynik = wynik + 1;
                                break;
                            }
                        }
                    }
                    progressbar.setProgress(wynik * 25);
                    rate.setTextColor(Color.parseColor("#FFFFFF"));
                    if (wynik == 1) rate.setText("słabe");
                    if (wynik == 2) rate.setText("średnie");
                    if (wynik == 3) rate.setText("mocne");
                    if (wynik == 4) rate.setText("bardzo mocne");

                }
            }
        });
    }
    @Click
    public void registerClicked()
    {

        if (emailRegister.getText().toString().equals("") || passwordRegister.getText().toString().equals("")
                || nameRegister.getText().toString().equals("") || surnameRegister.getText().toString().equals(""))
        {
            rate.setText("Wprowadź wszystkie dane!");
            rate.setTextColor(Color.parseColor("#FF0000"));
        }
        if (!emailRegister.getText().toString().equals("") && !passwordRegister.getText().toString().equals("")
                || !nameRegister.getText().toString().equals("") || !surnameRegister.getText().toString().equals(""))
        {
            int dlugosc;
            dlugosc = passwordRegister.getText().toString().length();
            if (dlugosc >= 6)
            {
                if (passwordRegister.getText().toString().equals(retypeRegister.getText().toString())) {
                    ring.show();
                    RegisterData registerData = new RegisterData();
                    registerData.first_name = nameRegister.getText().toString();
                    registerData.last_name = surnameRegister.getText().toString();
                    registerData.email = emailRegister.getText().toString();
                    registerData.new_password = passwordRegister.getText().toString();
                    restRegisterBackgroundTask.register(registerData);
                }
                else
                {
                    Toast.makeText(this, "Podane hasła różnią się", Toast.LENGTH_SHORT).show();
                }
            }
            else {

                rate.setTextColor(Color.parseColor("#FF0000"));
            }

        }
    }

    public void registerSuccess(User user)
    {
        ring.dismiss();
        SharedPreferences sp = getSharedPreferences(PREFS, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("id", user.id);
        editor.putString("session_id", user.sessionId );
        editor.commit();
        Recipe_Add_.intent(this).start();
        Toast.makeText(this, "Rejestracja zakonczona", Toast.LENGTH_LONG).show();
    }
    public void showError(Exception e)
    {
        ring.dismiss();
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        e.printStackTrace();
    }

}

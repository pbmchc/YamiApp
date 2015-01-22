package com.example.piotrek.yami;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.piotrek.yami.data.EmailAndPassword;
import com.example.piotrek.yami.data.GlobalData;
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
@EActivity (R.layout.log_in)
public class Login extends ActionBarActivity {


    private SharedPreferences sp;
    private static final String PREFS = "appPrefs";
    @Bean
    @NonConfigurationInstance
    RestLoginBackgroundTask restLoginBackgroundTask;

    @ViewById
    EditText emailEDT;

    @ViewById
    EditText passwordEDT;



    ProgressDialog ring;


    @AfterViews
    void init()
    {
        Toast.makeText(this, "Zaloguj się aby kontynuować", Toast.LENGTH_LONG).show();
        ring = new ProgressDialog(this);
        ring.setMessage("Trwa logowanie");
        ring.setIndeterminate(true);

    }
    @Click
    void registerTXTClicked()
    {
        Register_.intent(this).start();
    }
    @Click
    void loginClicked()
    {
        ring.show();
        EmailAndPassword emailAndPassword = new EmailAndPassword();
        emailAndPassword.email = emailEDT.getText().toString();
        emailAndPassword.password =passwordEDT.getText().toString();
        restLoginBackgroundTask.login(emailAndPassword);
    }
    public void loginSuccess(User user)
    {
        /*
        GlobalData credentials = new GlobalData();
        credentials.setId(user.id);
        credentials.setSessionId(user.sessionId);
        Toast.makeText(this, String.valueOf(credentials.getId()), Toast.LENGTH_LONG).show();
        */
        sp = getSharedPreferences(PREFS, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("id", user.id);
        editor.putString("session_id", user.sessionId );
        editor.commit();
        finish();

        ring.dismiss();
    }
    public void showError(Exception e)
    {
        ring.dismiss();
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        e.printStackTrace();
    }
}

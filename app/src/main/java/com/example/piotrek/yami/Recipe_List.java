package com.example.piotrek.yami;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.piotrek.yami.adapter.RecipeListAdapter;
import com.example.piotrek.yami.data.CookBook;
import com.example.piotrek.yami.data.Recipe;
import com.example.piotrek.yami.data.User;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

@EActivity (R.layout.activity_recipe__list)
@OptionsMenu (R.menu.menu_recipe__list)

public class Recipe_List extends ActionBarActivity {


    public static final String PREFS = "appPrefs";
    @ViewById
    Button logout;
    @ViewById
    ListView recipelist;
    @ViewById
    TextView username;
    @Bean
    RecipeListAdapter adapter;

    @Bean
    @NonConfigurationInstance
    RestBackgroundTask restBack;
    ProgressDialog ringProg;

    @AfterViews
    void init() {

        recipelist.setAdapter(adapter);
        ringProg = new ProgressDialog(this);
        ringProg.setMessage("Wczytuję informacje...");
        ringProg.setIndeterminate(true);
        ringProg.show();
        restBack.getCookBook();

        SharedPreferences check = getSharedPreferences(PREFS, 0);
        if (check.getString("session_id", null) != null)
        {
            int id = check.getInt("id", 0);
            String session = check.getString("session_id", null);
            restBack.getUsername(session, id);
        }

    }

    @ItemClick

    void recipelistItemClicked(Recipe item) {


       Recipe_Details_.intent(this).item(item).start();

    }

    public void showError(Exception e) {

        ringProg.dismiss();

        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

        e.printStackTrace();

    }

    public void updateCookbook(CookBook cookBook) {

        ringProg.dismiss();

        adapter.update(cookBook);

    }
    void showLoggedUser (User loggedUser)
    {
        username.setText(loggedUser.display_name);
    }
    @Click
    void addRecipeClicked()
    {
        SharedPreferences check = getSharedPreferences(PREFS, 0);
        String session = check.getString("session_id", null);

        if (session == null) {
            Login_.intent(this).start();
        }
        else
        {
            Recipe_Add_.intent(this).start();
        }

    }
    @Click
    void logoutClicked()
    {

        SharedPreferences check = getSharedPreferences(PREFS, 0);
        String session = check.getString("session_id", null);
        if (session == null)
        {
            Toast.makeText(this, "Najpierw się zaloguj", Toast.LENGTH_SHORT).show();
        }
        else {
            SharedPreferences.Editor editor = check.edit();
            editor.clear();
            editor.commit();
            Toast.makeText(this, "Wylogowano", Toast.LENGTH_LONG).show();
            username.setText("Niezalogowany");
        }
    }
    @Click
    void favouritesClicked()
    {
        SharedPreferences check = getSharedPreferences(PREFS, 0);
        String session = check.getString("session_id", null);
        if (session == null) {
            Login_.intent(this).start();
        }
        else
        {
            My_Recipe_List_.intent(this).start();
        }


    }
    @Click
    void refreshClicked()
    {
        ringProg.show();
        restBack.getCookBook();

        SharedPreferences check = getSharedPreferences(PREFS, 0);
        if (check.getString("session_id", null) != null)
        {
            int id = check.getInt("id", 0);
            String session = check.getString("session_id", null);
            restBack.getUsername(session, id);
        }
        else
        {
            username.setText("Niezalogowany");
        }


    }


}

package com.example.piotrek.yami;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.piotrek.yami.adapter.RecipeListAdapter;
import com.example.piotrek.yami.data.MyRecipes;
import com.example.piotrek.yami.data.Recipe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Piotrek on 2015-01-21.
 */
@EActivity (R.layout.my_rec_activity)
public class My_Recipe_List extends ActionBarActivity {

    @ViewById
    ListView mylist;

    @Bean
    RecipeListAdapter adapter;

    @Bean
    @NonConfigurationInstance
    RestMyListBackground restBack;

    public static final String PREFS = "appPrefs";
    ProgressDialog ring;

    @AfterViews
            void init()
    {
        mylist.setAdapter(adapter);
        ring = new ProgressDialog(this);
        ring.setMessage("Wczytuję informacje...");
        ring.setIndeterminate(true);
        ring.show();
        SharedPreferences check = getSharedPreferences(PREFS, 0);
        int ownerId = (check.getInt("id", 0));
        restBack.getMyCookbook(ownerId);
    }
    @ItemClick

    void mylistItemClicked(Recipe item) {


        Recipe_Details_.intent(this).item(item).start();

    }
    public void updateMyCookbook(MyRecipes myCookBook) {

        ring.dismiss();

        adapter.myUpdate(myCookBook);

    }
    public void showError(Exception e) {

        ring.dismiss();

        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

        e.printStackTrace();

    }

}

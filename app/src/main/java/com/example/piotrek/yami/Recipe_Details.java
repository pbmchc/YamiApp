package com.example.piotrek.yami;


import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.piotrek.yami.adapter.CommentsListAdapter;
import com.example.piotrek.yami.data.Comment;
import com.example.piotrek.yami.data.Comments;
import com.example.piotrek.yami.data.CookBook;
import com.example.piotrek.yami.data.GlobalData;
import com.example.piotrek.yami.data.Like;
import com.example.piotrek.yami.data.Likes;
import com.example.piotrek.yami.data.MyLikes;
import com.example.piotrek.yami.data.Recipe;
import com.example.piotrek.yami.data.User;
import com.example.piotrek.yami.itemView.CookbookRC;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

@EActivity (R.layout.recipe_details)
public class Recipe_Details extends ActionBarActivity {


    public static final String PREFS = "appPrefs";
    public int size;
    @RestService
    CookbookRC restClient;
    @Extra
    Recipe item;
    @ViewById
    ImageView imageDET;
    @ViewById
    TextView titleDET;
    @ViewById
    TextView introDET;
    @ViewById
    TextView ingredDET;
    @ViewById
    TextView stepsDET;
    @ViewById
    TextView timePREP;
    @ViewById
    TextView timeCOOK;
    @ViewById
    TextView servings;
    @ViewById
    TextView date;
    @ViewById
    ListView commentList;
    @ViewById
    TextView favTXT;
    @ViewById
    TextView authorName;
    @ViewById
    EditText commentEDT;
    @ViewById
    ImageButton favCKB;

    ProgressDialog ringProg;
    @Bean
    CommentsListAdapter adapter;

    @Bean
    @NonConfigurationInstance
    RestDetailsBackground restBack;



    @AfterViews
    void init() {

        commentList.setAdapter(adapter);
        ringProg = new ProgressDialog(this);
        ringProg.setMessage("Wczytuję informacje...");
        ringProg.setIndeterminate(true);
        ringProg.show();
        restBack.getRecipe();
        restBack.getComments(item);
        restBack.countLikes(item);
        SharedPreferences check = getSharedPreferences(PREFS, 0);
        restBack.checkMyLikes(item, check.getInt("id", 0));
        if (check.getString("session_id", null) != null)
        {
            restBack.getUser(check.getString("session_id", null));
        }



        commentList.setOnTouchListener(new ListView.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                int action = event.getAction();

                switch (action)
                {
                    case MotionEvent.ACTION_DOWN:
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:

                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                v.onTouchEvent(event);
                return true;
            }
        });
    }

        void assignData(Recipe recipe) {
            titleDET.setText(recipe.title);
            introDET.setText(recipe.introduction);
            ingredDET.setText(recipe.ingredients);
            stepsDET.setText(recipe.steps);
            timePREP.setText(String.valueOf(recipe.preparationMinutes));
            timeCOOK.setText(String.valueOf(recipe.cookingMinutes));
            servings.setText(String.valueOf(recipe.servings));
            date.setText(recipe.created);


            if (recipe.pictureBytes != null) {

                setImage(recipe.pictureBytes, imageDET);

            } else {

                imageDET.setImageDrawable(null);

            }
        }
    private void setImage(String base64bytes, ImageView image) {

        byte[] decodedString = Base64.decode(base64bytes, Base64.DEFAULT);

        Bitmap decodedBytes = BitmapFactory.decodeByteArray(decodedString, 0,

                decodedString.length);

        image.setImageBitmap(decodedBytes);

    }


    void promptUser (User author)
    {
        authorName.setText(author.display_name);
    }
    public void updateComments(Comments commlist) {


        adapter.update(commlist);

    }
    public void updateLikes(Likes likeList)
    {

        ringProg.dismiss();
        favTXT.setText(toString().valueOf(likeList.likes.size()));
    }
    public void LikesSize(MyLikes myLikes)
    {
       size = myLikes.mylikes.size();
    }
    @Click
    void addCommentClicked()
    {
        SharedPreferences check = getSharedPreferences(PREFS, 0);
        String session = check.getString("session_id", null);

        if (session == null) {
            Login_.intent(this).start();
        }
        else
        {
                ringProg.show();
                Comment comment = new Comment();
                comment.text = commentEDT.getText().toString();
                int ownerId = check.getInt("id", 0);
                comment.ownerId = ownerId;
                comment.recipeId = item.id;
                restBack.addNewCom(session, comment);

        }
    }
    @Click
    void favCKBClicked()
    {
        SharedPreferences check = getSharedPreferences(PREFS, 0);
        String session = check.getString("session_id", null);


        if(session == null)
        {
            Login_.intent(this).start();
        }
        else {

            restBack.checkMyLikes(item, check.getInt("id", 0));
            if (size == 0) {

                ringProg.show();
                Like like = new Like();
                like.recipeId = item.id;
                int ownerId = check.getInt("id", 0);
                like.ownerId = ownerId;

                restBack.addNewLike(session, like);
            }
            else
            {
                Toast.makeText(this, "Już to zrobiłeś!", Toast.LENGTH_SHORT).show();
            }
            }

    }
    @Click
    void favouritesClicked()
    {

    }
    public void commentAdded()
    {

        Toast.makeText(this, "Dodano komentarz", Toast.LENGTH_SHORT).show();
        restBack.getComments(item);
        ringProg.dismiss();

    }
    public void likeAdded()
    {
        Toast.makeText(this, "Polecasz ten przepis", Toast.LENGTH_SHORT).show();
        restBack.countLikes(item);
        ringProg.dismiss();
    }
    public void showError(Exception e) {

        ringProg.dismiss();

        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();

        e.printStackTrace();

    }
    }




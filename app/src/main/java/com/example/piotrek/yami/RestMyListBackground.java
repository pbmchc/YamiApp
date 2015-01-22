package com.example.piotrek.yami;

import com.example.piotrek.yami.data.CookBook;
import com.example.piotrek.yami.data.MyRecipes;
import com.example.piotrek.yami.data.Picture;
import com.example.piotrek.yami.data.Recipe;
import com.example.piotrek.yami.itemView.CookbookRC;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

/**
 * Created by Piotrek on 2015-01-21.
 */
@EBean
public class RestMyListBackground {

    @RootContext
    My_Recipe_List activity;

    @RestService
    CookbookRC restClient;

    @Background
    void getMyCookbook (int ownerId)
    {

        try {

            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");

            MyRecipes myCookbook = restClient.myCookbook("ownerId=" + String.valueOf(ownerId));

            for (Recipe recipe: myCookbook.favors) {

                if (recipe.picture1Id != null) {

                    Picture picture = restClient.getPictureById(recipe.picture1Id);

                    recipe.pictureBytes = picture.base64bytes;

                }

            }

            publishResult(myCookbook);

        } catch (Exception e) {

            publishError(e);

        }

    }

    @UiThread
    void publishResult(MyRecipes myCookbook)
    {
        activity.updateMyCookbook(myCookbook);
    }
    @UiThread
    void publishError(Exception e)
    {
        activity.showError(e);
    }
}


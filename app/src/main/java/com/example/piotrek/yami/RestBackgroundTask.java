package com.example.piotrek.yami;

import com.example.piotrek.yami.data.CookBook;
import com.example.piotrek.yami.data.Picture;
import com.example.piotrek.yami.data.Recipe;
import com.example.piotrek.yami.data.User;
import com.example.piotrek.yami.itemView.CookbookRC;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

@EBean
public class RestBackgroundTask {

    @RootContext
    Recipe_List activity;

    @RestService
    CookbookRC restClient;

    @Background

    void getCookBook() {

        try {

            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");

            CookBook cookbook = restClient.getCookBook();
            for (Recipe recipe: cookbook.records) {

                if (recipe.picture1Id != null) {

                    Picture picture = restClient.getPictureById(recipe.picture1Id);

                    recipe.pictureBytes = picture.base64bytes;

                }

            }

            publishResult(cookbook);

        } catch (Exception e) {

            publishError(e);

        }

    }
    @Background
    void getUsername(String session, int id)
    {
        try
        {
            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");
            restClient.setHeader("X-Dreamfactory-Session-Token", session);
            User loggedUser = restClient.showAuthor(id);
            publishUser(loggedUser);

        }
        catch (Exception e)
        {
            publishError(e);
        }
    }
    @UiThread

    void publishResult(CookBook cookbook) {

        activity.updateCookbook(cookbook);

    }
    @UiThread
    void publishUser(User loggedUser)
    {
        activity.showLoggedUser(loggedUser);
    }
    @UiThread
    void publishError(Exception e) {
        activity.showError(e);
    }

}

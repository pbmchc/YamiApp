package com.example.piotrek.yami;

import com.example.piotrek.yami.data.Recipe;
import com.example.piotrek.yami.data.User;
import com.example.piotrek.yami.itemView.CookbookRC;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

/**
 * Created by Piotrek on 2015-01-18.
 */
@EBean
public class RestAddBackground {

    @RootContext
    Recipe_Add activity;

    @RestService
    CookbookRC restClient;

    @UiThread
    void publishResult()
    {
        activity.addSuccess();
    }
    @UiThread
    void publishError(Exception e)
    {
        activity.showError(e);
    }
    @Background
    public void addNew(String session, Recipe recipe)
    {
        try
        {

            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");
            restClient.setHeader("X-Dreamfactory-Session-Token", session);
            restClient.addRecipe(recipe);
            publishResult();
        }
        catch (Exception e)
        {
            publishError(e);
        }
    }
}

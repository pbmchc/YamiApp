package com.example.piotrek.yami;


import com.example.piotrek.yami.data.RegisterData;
import com.example.piotrek.yami.data.User;
import com.example.piotrek.yami.itemView.CookbookRC;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

/**
 * Created by Piotrek on 2015-01-17.
 */
@EBean
public class RestRegisterBackgroundTask {
    @RootContext
    Register activity;
    @RestService
    CookbookRC restClient;

    @UiThread
    void publishResult(User user)
    {
        activity.registerSuccess(user);
    }
    @UiThread
    void publishError(Exception e)
    {
        activity.showError(e);
    }
    @Background
    public void register(RegisterData registerData)
    {
        try
        {
            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");
            User user = restClient.register(registerData);
            publishResult(user);
        }
        catch (Exception e)
        {
            publishError(e);
        }
    }
}

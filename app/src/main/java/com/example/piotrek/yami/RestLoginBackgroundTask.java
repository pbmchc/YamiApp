package com.example.piotrek.yami;

import com.example.piotrek.yami.data.EmailAndPassword;
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
public class RestLoginBackgroundTask {
    @RootContext
    Login activity;

    @RestService
    CookbookRC restClient;

    @UiThread
    void publishResult(User user)
    {
        activity.loginSuccess(user);
    }
    @UiThread
    void publishError(Exception e)
    {
        activity.showError(e);
    }
    @Background
    public void login(EmailAndPassword emailAndPassword)
    {
        try
        {
            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");
            User user = restClient.login(emailAndPassword);
            publishResult(user);
        }
        catch (Exception e)
        {
            publishError(e);
        }
    }
}

package com.example.piotrek.yami;

import com.example.piotrek.yami.data.Picture;
import com.example.piotrek.yami.data.PictureResult;
import com.example.piotrek.yami.data.User;
import com.example.piotrek.yami.itemView.CookbookRC;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

/**
 * Created by Piotrek on 2015-01-22.
 */
@EBean
public class RestPictureBackgroundTask {
    @RootContext
    Recipe_Add activity;

    @RestService
    CookbookRC restClient;

    @Background
    void addPicture(String pictureBytes, int id, String session) {
        try {
            Picture picture = new Picture();
            picture.ownerId = id;
            picture.base64bytes = pictureBytes;
            restClient.setHeader("X-Dreamfactory-Application-Name", "phonebook");
            restClient.setHeader("X-Dreamfactory-Session-Token", session);
            PictureResult result = restClient.addPicture(picture);
            publishPictureSuccess(result.id);
        } catch (Exception e) {
            publishError(e);
        }
    }

    @UiThread
    void publishPictureSuccess(int id) {
        activity.pictureAdded(id);
    }

    @UiThread
    void publishError(Exception e) {
        activity.addPictureFailed(e);
    }

}

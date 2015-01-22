package com.example.piotrek.yami;

import com.example.piotrek.yami.data.Comment;
import com.example.piotrek.yami.data.Comments;
import com.example.piotrek.yami.data.CookBook;
import com.example.piotrek.yami.data.Like;
import com.example.piotrek.yami.data.Likes;
import com.example.piotrek.yami.data.MyLikes;
import com.example.piotrek.yami.data.Picture;
import com.example.piotrek.yami.data.Recipe;
import com.example.piotrek.yami.data.User;
import com.example.piotrek.yami.itemView.CookbookRC;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

import java.util.Collections;

/**
 * Created by Piotrek on 2015-01-17.
 */
@EBean
public class RestDetailsBackground {
    @RootContext
    Recipe_Details activity;

    @RestService
    CookbookRC restClient;

    @Background
    void getRecipe() {
        try {
            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");

            Recipe recipe = restClient.getRecipe(activity.item.id);

            if (recipe.picture1Id != null) {

                Picture picture = restClient.getPictureById(recipe.picture1Id);

                recipe.pictureBytes = picture.base64bytes;
            }

            publishResult(recipe);
        } catch (Exception e) {

            publishError(e);
        }
    }

    @Background
    void getUser(String session)
    {
        try
        {
            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");
            restClient.setHeader("X-Dreamfactory-Session-Token", session);
            User author = restClient.showAuthor(activity.item.ownerId);
            publishAuthor(author);


        }
        catch (Exception e)
        {
            publishError(e);
        }
    }
    @Background
    void getComments(Recipe item) {
        try {

            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");

            Comments commlist = restClient.getComments("recipeId=" + String.valueOf(item.id));

            publishComments(commlist);

        } catch (Exception e) {

            publishError(e);

        }
    }

    @Background
    void countLikes(Recipe item) {

        try {
            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");
            Likes likeList = restClient.countLikes("recipeId=" + String.valueOf(item.id));
            showLikes(likeList);
        } catch (Exception e) {
            publishError(e);
        }
    }
      @Background
      void checkMyLikes (Recipe item, int id)
        {
            try {
                restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");
                MyLikes myLikes = restClient.getMyLikes("recipeId=" + String.valueOf(item.id), "ownerId=" + String.valueOf(id));
                checkLikes(myLikes);
            }
            catch (Exception e)
            {
                publishError(e);
            }
        }


    @Background
    void addNewCom(String session, Comment comment)
    {
        try {
            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");
            restClient.setHeader("X-Dreamfactory-Session-Token", session);
            restClient.addComment(comment);
            publishComment();
        }
        catch (Exception e)
        {
            publishError(e);
        }
    }
    @Background
    void addNewLike(String session, Like like)
    {
        try
        {
            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");
            restClient.setHeader("X-Dreamfactory-Session-Token", session);
            restClient.addLike(like);
            publishLike();
        }
        catch (Exception e)
        {
            publishError(e);
        }

    }

    @UiThread
    void publishResult(Recipe recipe) {

        activity.assignData(recipe);

    }

    @UiThread
    void publishComments(Comments commlist) {

        activity.updateComments(commlist);

    }

    @UiThread
    void showLikes(Likes likeList) {
        activity.updateLikes(likeList);
    }

    @UiThread
    void publishComment()
    {
        activity.commentAdded();
    }

    @UiThread
    void publishLike()
    {
        activity.likeAdded();
    }
    @UiThread
    void checkLikes(MyLikes myLikes)
    {
        activity.LikesSize(myLikes);
    }
    @UiThread
    void publishAuthor(User author) {
        activity.promptUser(author);
    }
    @UiThread
    void publishError(Exception e) {
        activity.showError(e);
    }
}



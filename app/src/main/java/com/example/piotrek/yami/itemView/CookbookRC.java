package com.example.piotrek.yami.itemView;

import com.example.piotrek.yami.My_Recipe_List;
import com.example.piotrek.yami.data.Comment;
import com.example.piotrek.yami.data.Comments;
import com.example.piotrek.yami.data.CookBook;
import com.example.piotrek.yami.data.EmailAndPassword;
import com.example.piotrek.yami.data.Like;
import com.example.piotrek.yami.data.Likes;
import com.example.piotrek.yami.data.MyLikes;
import com.example.piotrek.yami.data.MyRecipes;
import com.example.piotrek.yami.data.Picture;
import com.example.piotrek.yami.data.PictureResult;
import com.example.piotrek.yami.data.Recipe;
import com.example.piotrek.yami.data.RegisterData;
import com.example.piotrek.yami.data.User;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.RequiresHeader;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Rest (rootUrl = "http://pegaz.wzr.ug.edu.pl:8080/rest", converters = { MappingJackson2HttpMessageConverter.class})
@RequiresHeader({"X-Dreamfactory-Application-Name"})

public interface CookbookRC extends RestClientHeaders {

    @Get("/db/recipes?order=created DESC")
    CookBook getCookBook();
    @Get ("/db/recipes/{id}")
    Recipe getRecipe(int id);
    @Post ("/user/session")
    User login(EmailAndPassword emailAndPassword);
    @Post ("/user/register/?login=true")
    User register(RegisterData registerData);
    @Get ("/db/comments/?filter={id}")
    Comments getComments(String id);
    @RequiresHeader({"X-Dreamfactory-Application-Name","X-Dreamfactory-Session-Token"})
    @Post ("/db/recipes")
    void addRecipe(Recipe recipe);
    @Get("/db/likes/?filter={id}")
    Likes countLikes(String id);
    @RequiresHeader({"X-Dreamfactory-Application-Name","X-Dreamfactory-Session-Token"})
    @Post ("/db/comments")
    void addComment(Comment comment);
    @RequiresHeader({"X-Dreamfactory-Application-Name","X-Dreamfactory-Session-Token"})
    @Post("/db/likes")
    void addLike (Like like);
    @Get ("/db/likes/?filter={recipeId} and {ownerId}")
    MyLikes getMyLikes (String recipeId, String ownerId);
    @RequiresHeader({"X-Dreamfactory-Application-Name","X-Dreamfactory-Session-Token"})
    @Get ("/system/user/{id}")
    User showAuthor(int id);
    @Get("/db/recipes/?filter={ownerId}")
    MyRecipes myCookbook(String ownerId);

    //P
    @Get("/db/pictures/{id}")
    Picture getPictureById(int id);
    @Post("/db/pictures")
    @RequiresHeader({"X-Dreamfactory-Application-Name", "X-Dreamfactory-Session-Token"})
    PictureResult addPicture(Picture picture);



}

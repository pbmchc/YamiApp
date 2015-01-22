package com.example.piotrek.yami.itemView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.piotrek.yami.R;
import com.example.piotrek.yami.data.Recipe;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;

@EViewGroup (R.layout.item_layout)
public class RecipeItemView extends RelativeLayout {


    @ViewById
    ImageView recipeImage;
    @ViewById
    TextView recipetitle;

    @ViewById
    TextView recipedate;

public RecipeItemView(Context context)
    {
        super(context);
    }

public void bind (Recipe recipe)
{
    recipetitle.setText(recipe.title);
    recipedate.setText(recipe.created);

    if (recipe.pictureBytes != null) {

        setImage(recipe.pictureBytes, recipeImage);

    } else {

        recipeImage.setImageDrawable(null);

    }
}
    private void setImage(String base64bytes, ImageView image) {

        byte[] decodedString = Base64.decode(base64bytes, Base64.DEFAULT);

        Bitmap decodedBytes = BitmapFactory.decodeByteArray(decodedString, 0,

                decodedString.length);

        image.setImageBitmap(decodedBytes);

    }


}

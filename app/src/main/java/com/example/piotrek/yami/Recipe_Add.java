package com.example.piotrek.yami;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.piotrek.yami.data.Recipe;
import com.example.piotrek.yami.data.User;
import com.example.piotrek.yami.itemView.CookbookRC;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.rest.RestService;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by Piotrek on 2015-01-17.
 */
@EActivity (R.layout.recipe_add)
public class Recipe_Add extends ActionBarActivity {

    public static final String PREFS = "appPrefs";
    public Integer idnum;

    //P
    public static final int INTENT_SHOOT_WITH_CAMERA = 1;
    public static final int INTENT_SELECT_FILE = 2;

    @RestService
    CookbookRC restClient;

    @ViewById
    EditText addTitleEDT;
    @ViewById
    EditText addIntroEDT;
    @ViewById
    EditText addIngredEDT;
    @ViewById
    EditText addStepsEDT;
    @ViewById
    EditText addServEDT;
    @ViewById
    EditText addPrepEDT;
    @ViewById
    EditText addCookEDT;
    // P
    @ViewById
    ImageView addFotoIMG;

    ProgressDialog ring;

    //P
    @Bean
    @NonConfigurationInstance
    RestPictureBackgroundTask restPictureTask;

    @Bean
    @NonConfigurationInstance
    RestAddBackground restAddBackground;

    @AfterViews
    void init()
    {
        ring = new ProgressDialog(this);
        ring.setMessage("Trwa dodawanie przepisu");
        ring.setIndeterminate(true);
    }
    @Click
    void addClicked()
    {

            SharedPreferences check = getSharedPreferences(PREFS, 0);
            String session = check.getString("session_id", null);
            ring.show();
            Recipe recipe = new Recipe();
            recipe.title = addTitleEDT.getText().toString();
            recipe.introduction = addIntroEDT.getText().toString();
            recipe.ingredients = addIngredEDT.getText().toString();
            recipe.steps = addStepsEDT.getText().toString();
            try {
                recipe.servings = Integer.parseInt(addServEDT.getText().toString());
                recipe.preparationMinutes = Integer.parseInt(addPrepEDT.getText().toString());
                recipe.cookingMinutes = Integer.parseInt(addCookEDT.getText().toString());
            }
            catch (Exception osin)
            {
                if(addPrepEDT.getText().toString().equals(""))
                {
                    recipe.preparationMinutes = 0;
                }
                if (addCookEDT.getText().toString().equals(""))
                {
                    recipe.cookingMinutes = 0;
                }
            }
            recipe.ownerId = check.getInt("id", 0);
            if (idnum != null)
            {
                recipe.picture1Id = idnum;
            }
        if(addTitleEDT.toString().equals("") || addIngredEDT.getText().toString().equals("") ||
                addStepsEDT.getText().toString().equals("") || addServEDT.getText().toString().equals("")) {
            ring.dismiss();
            Toast.makeText(this, "Uzupełnij wymagane pola", Toast.LENGTH_SHORT).show();
        }
        else
        {
            restAddBackground.addNew(session, recipe);

        }


    }
    public void addSuccess()
    {
        ring.dismiss();
        Toast.makeText(this, "Przepis dodano", Toast.LENGTH_LONG).show();
        idnum = null;
        Recipe_List_.intent(this).start();
    }
    public void showError(Exception e)
    {
        ring.dismiss();
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        e.printStackTrace();
    }
    //P
    @Click
    public void addImageClicked()
    {
        openPictureSelectionDialog();
    }
    private void openPictureSelectionDialog() {
        final CharSequence[] items = { "Uruchom aparat", "Wybierz z galerii",
                "Anuluj" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Dodaj zdjęcie");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    startCameraIntent();
                } else if (item == 1) {
                    startSelectFromGalleryIntent();
                } else {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private void startSelectFromGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), INTENT_SELECT_FILE);
    }
    private void startCameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory()+File.separator + "image.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, INTENT_SHOOT_WITH_CAMERA);
    }
    @OnActivityResult(INTENT_SHOOT_WITH_CAMERA)
    void onPhotoFromCameraTaken(int resultCode) {
        if (resultCode == RESULT_OK) {
            File rawCameraImageFile = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
            addCompressedImageFromPath(rawCameraImageFile.getAbsolutePath());
            rawCameraImageFile.delete();
        }
    }
    @OnActivityResult(INTENT_SELECT_FILE)
    void onPhotoFromGallerySelected(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            String selectedImageFilePath = getPath(selectedImageUri, Recipe_Add.this);
            addCompressedImageFromPath(selectedImageFilePath);
        }
    }
    private void addCompressedImageFromPath(String selectedImageFilePath) {
        Bitmap bitmap = decodeSampledBitmapFromFile(selectedImageFilePath, 400, 400);
        addFotoIMG.setImageBitmap(bitmap);
        String pictureBytes = compressAndEncodeToBase64(bitmap);
        SharedPreferences check = getSharedPreferences(PREFS, 0);
        int id = check.getInt("id", 0);
        String session = check.getString("session_id", null);
        restPictureTask.addPicture(pictureBytes, id, session);
    }

    public Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight)
    { // BEST QUALITY MATCH

        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int sourceWidth = options.outWidth;
        final int sourceHeight = options.outHeight;
        options.inSampleSize = calculateInSampleSize(sourceWidth, sourceHeight, reqWidth, reqHeight);
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return getAccuratelyResizedBitmap(bitmap, reqWidth, reqHeight);

    }
    public static int calculateInSampleSize(int sourceWidth, int sourceHeight, int reqWidth, int reqHeight) {
        int inSampleSize = 1;

        if (sourceHeight > reqHeight || sourceWidth > reqWidth) {

            final int halfHeight = sourceHeight / 2;
            final int halfWidth = sourceWidth / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public Bitmap getAccuratelyResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        if (scaleWidth < scaleHeight) {
            newWidth = Math.round(width*scaleWidth);
            newHeight = Math.round(height*scaleWidth);
        } else {
            newWidth = Math.round(width*scaleHeight);
            newHeight = Math.round(height*scaleHeight);
        }
        return Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
    }

    public String getPath(Uri uri, Activity activity) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public String compressAndEncodeToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.NO_WRAP | Base64.NO_PADDING);
        Log.d(getClass().getSimpleName(), imageEncoded);
        Toast.makeText(this, "" + imageEncoded.length(), Toast.LENGTH_SHORT).show();
        return imageEncoded;
    }

    public void pictureAdded(int id) {
        Toast.makeText(this, "Dodano zdjęcie z id=" + id + ".", Toast.LENGTH_SHORT).show();
        idnum = id;
    }

    public void addPictureFailed(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        e.printStackTrace();
    }





}


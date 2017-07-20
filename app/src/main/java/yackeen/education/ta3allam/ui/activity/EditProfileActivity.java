package yackeen.education.ta3allam.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import yackeen.education.ta3allam.Manifest;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.model.dto.request.EditProfilePictureRequest;
import yackeen.education.ta3allam.model.dto.request.EditProfileRequest;
import yackeen.education.ta3allam.model.dto.request.MessageListRequest;
import yackeen.education.ta3allam.model.dto.response.EditProfilePictureResponse;
import yackeen.education.ta3allam.model.dto.response.EditProfileResponse;
import yackeen.education.ta3allam.model.dto.response.MessageListResponse;
import yackeen.education.ta3allam.server.api.API;
import yackeen.education.ta3allam.util.SelectImageUtil;
import yackeen.education.ta3allam.util.UserHelper;

public class EditProfileActivity extends AppCompatActivity implements SelectImageUtil.SelectImageUtilListener {
    private ImageView profileImage;
    private ImageView iconEditImage;
    private Button editButton;
    private ProgressBar editProgressBar;
    private Toolbar editToolbar;
    private EditText nameEditText;
    private EditText aboutEditText;
    private TextView aboutword;
    private final String TAG = "Edit_Profile_Activity";
    private static final String PhotoParam = "photo_url";
    private static final String NameParam = "user_name";
    private static final String AboutParam = "user_about";
    private String photoUrl;
    private String userName;
    private String userabout;
    SelectImageUtil selectImageUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        retrieveDataFromIntent();
        createView();
        addValuesToView();
        addListener();

    }

    public static Intent newEditProfileActivtyIntent(Context context, String photoUrl, String name, String about) {
        Intent intent = new Intent(context, EditProfileActivity.class);
        intent.putExtra(PhotoParam, photoUrl);
        intent.putExtra(NameParam, name);
        intent.putExtra(AboutParam, about);
        return intent;
    }

    public void retrieveDataFromIntent() {
        Intent intent = getIntent();
        photoUrl = intent.getStringExtra(PhotoParam);
        userName = intent.getStringExtra(NameParam);
        userabout = intent.getStringExtra(AboutParam);
    }

    public void createView() {
        profileImage = (ImageView) findViewById(R.id.edit_profile_image);
        iconEditImage = (ImageView) findViewById(R.id.edit_icon);
        editButton = (Button) findViewById(R.id.btn_log_out);
        editProgressBar = (ProgressBar) findViewById(R.id.edit_progress);
        editProgressBar.setVisibility(View.GONE);
        editToolbar = (Toolbar) findViewById(R.id.edit_profile_toolbar);
        setSupportActionBar(editToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        nameEditText = (EditText) findViewById(R.id.edit_name_edit_text);
        aboutEditText = (EditText) findViewById(R.id.edit_about_edit_text);
        aboutword = (TextView) findViewById(R.id.about_word_edit);
        if (UserHelper.getUserType(this) == 1) {
            aboutword.setVisibility(View.INVISIBLE);
            aboutEditText.setEnabled(false);
            aboutEditText.setVisibility(View.INVISIBLE);
        }

    }

    public void addValuesToView() {
        if (photoUrl != null) {
            Picasso.with(this).load(photoUrl).placeholder(R.drawable.default_emam).error(R.drawable.default_emam).into(profileImage);
        }
        if (userName != null) {
            nameEditText.setText(userName);
        }
        if (userabout != null) {
            aboutEditText.setText(userabout);
        }
    }

    public void addListener() {
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nameEditText.getText() != null) {
                    editProgressBar.setVisibility(View.VISIBLE);
                    editProfile();
                    if (profileImage != null) {
                        editProfilePicture();
                    } else {
                        Toast.makeText(EditProfileActivity.this, "اختر صوره", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditProfileActivity.this, "املأ خانه الاسم", Toast.LENGTH_SHORT).show();
                }
                editProgressBar.setVisibility(View.GONE);
                finish();
            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectImageUtil == null)
                    selectImageUtil = new SelectImageUtil(EditProfileActivity.this, EditProfileActivity.this);
                selectImageUtil.showDialog();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (selectImageUtil != null && selectImageUtil.shouldHandleResult(requestCode)) {
            selectImageUtil.handleResult(requestCode, resultCode, data, "image.jpg");
            profileImage.setImageURI(selectImageUtil.getMediaUri());
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void editProfile() {
        EditProfileRequest body = new EditProfileRequest();
        body.setName(nameEditText.getText().toString());

        if (UserHelper.getUserType(this) == 2) {
            body.setAbout(aboutEditText.getText().toString());
        } else {
            body.setAbout("no");
        }

        body.setUserID(UserHelper.getUserId(this));
        API.getUserAPIs().editProfile(body, getEditProfileListener(),
                getEditProfileFailedListener(), this);


    }

    public void editProfilePicture() {
//        EditProfilePictureRequest body = new EditProfilePictureRequest();
//        byte[] imageAsBytes = selectImageUtil.getImageAsBytes();
//        body.setImage(imageAsBytes);
        if (selectImageUtil != null) {
            if (selectImageUtil.isFileSelected()) {
                API.getUserAPIs().editProfilePicture(selectImageUtil.getUploadMap(), getEditProfilePictureListener(),
                        getEditProfileFailedListener(), this);
            }
        }


    }

    //network response
    private Response.Listener<EditProfileResponse> getEditProfileListener() {
        return new Response.Listener<EditProfileResponse>() {
            @Override
            public void onResponse(EditProfileResponse response) {
                if (response.isSuccess()) {
                    Toast.makeText(EditProfileActivity.this, "تم التعديل ", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(EditProfileActivity.this, "الاسم ربما يكون خاطأ", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onResponse: "+response.getErrorMessage());
                }
            }
        };
    }

    private Response.Listener<EditProfilePictureResponse> getEditProfilePictureListener() {
        return new Response.Listener<EditProfilePictureResponse>() {
            @Override
            public void onResponse(EditProfilePictureResponse response) {
                if (response.isSuccess()) {
                    Toast.makeText(EditProfileActivity.this, "تم التعديل ", Toast.LENGTH_LONG).show();
                    UserHelper.dismissProgressDialog();

                } else {
                    Log.e(TAG, "onResponse: " + response.getErrorMessage());
                    Toast.makeText(EditProfileActivity.this, "تاكد من اختيار الصوره", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onResponse: "+response.getErrorMessage());

                }
            }
        };
    }

    private Response.ErrorListener getEditProfileFailedListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ".concat(error.toString()));
                Toast.makeText(EditProfileActivity.this, yackeen.education.ta3allam.R.string.network_error, Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void onIntentReady(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
        }
    }

}

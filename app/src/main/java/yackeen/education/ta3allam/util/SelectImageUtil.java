package yackeen.education.ta3allam.util;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import yackeen.education.ta3allam.BuildConfig;
import yackeen.education.ta3allam.R;
import yackeen.education.ta3allam.server.request.MultipartRequest;

import static android.app.Activity.RESULT_OK;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

/**
 * Created by Abdelrhman Walid on 1/24/2017.
 */

public class SelectImageUtil {
    public Map<String, MultipartRequest.DataPart> getUploadMap() {
        Map<String, MultipartRequest.DataPart> partMap = new HashMap<>();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), mediaUri);
            byte[] inputData = getBytes(bitmap);
            MultipartRequest.DataPart dataPart = new MultipartRequest.DataPart("image", inputData);
            partMap.put("image", dataPart);
            return partMap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final String TAG = SelectImageUtil.class.getSimpleName();
    public final int TAKE_PHOTO_REQUEST = 20;
    public final int PICK_PHOTO_REQUEST = 21;
    private Context context;
    private Uri mediaUri;
    private String fileName;
    private boolean imageSelected;
    private SelectImageUtilListener listener;
    private DialogInterface.OnClickListener dialogLlistener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i) {
                case 0:
                    takePhoto();
                    break;
                case 1:
                    // choose a picture
                    chooseFile("image/*");
                    break;
            }
        }
    };

    public SelectImageUtil(Context context, SelectImageUtilListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public boolean isImageSelected() {
        return imageSelected;
    }

    public void reset() {
        imageSelected = false;
    }

    public String getFileName() {
        return fileName;
    }

    public Uri getMediaUri() {
        return mediaUri;
    }

    public void takePhoto() {
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = getOutputFileUri(MEDIA_TYPE_IMAGE);
        if (file == null) {
            Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
            return;
        }
        mediaUri = FileProvider.getUriForFile(context,
                BuildConfig.APPLICATION_ID + ".provider",
                file);
        if (mediaUri == null) {
            // display error
            Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
        } else {
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, mediaUri);
            listener.onIntentReady(takePhotoIntent, TAKE_PHOTO_REQUEST);
        }
    }

    public void chooseFile(String type) {
        Intent choosePhotoIntent = new Intent(Intent.ACTION_GET_CONTENT);
        choosePhotoIntent.setType(type);
        listener.onIntentReady(choosePhotoIntent, PICK_PHOTO_REQUEST);
    }

    public Context getContext() {
        return context;
    }

    public void showDialog() {
        new AlertDialog.Builder(getContext())
                .setItems(R.array.camera_choices, dialogLlistener)
                .show();
    }

    private File getOutputFileUri(int mediaType) {
        if (isExternalStorageAvailable()) {
            File mediaFile;
            Date now = new Date();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(now);

            String path = getContext().getExternalCacheDir().getPath() + File.separator;

            if (mediaType == MEDIA_TYPE_IMAGE) {
                fileName = path + "IMG_" + timeStamp + ".jpg";
                mediaFile = new File(fileName);
            } else {
                return null;
            }
            Log.d(TAG, "File: " + Uri.fromFile(mediaFile));
            return mediaFile;

        } else {
            return null;
        }
    }

//    private Uri getOutputFileUri() {
//        ContentResolver contentResolver = context.getContentResolver();
//        ContentValues cv = new ContentValues();
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
//        cv.put(MediaStore.Images.Media.TITLE, timeStamp);
//        return contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);
//    }


    private byte[] getBytes(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteBuffer);
        return byteBuffer.toByteArray();
    }

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    public boolean shouldHandleResult(int requestCode) {
        return requestCode == PICK_PHOTO_REQUEST || requestCode == TAKE_PHOTO_REQUEST;
    }

    /**
     * should call shouldHandleResult(int requestCode) first
     *
     * @param requestCode
     * @param resultCode
     * @param data
     * @param fileName
     */
    public void handleResult(int requestCode, int resultCode, Intent data, @Nullable String fileName) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_PHOTO_REQUEST) {
                if (!haveStoragePermission()) {
                    listener.requestStoragePermission();
                }
                if (data != null) {
                    mediaUri = data.getData();
                    if (mediaUri != null)
                        this.fileName = context.getContentResolver().getType(mediaUri);
                    if (fileName != null)
                        this.fileName = fileName;
                } else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
            imageSelected = true;
        }
    }

    public boolean isFileSelected() {
        return imageSelected;
    }

    public byte[] getImageAsBytes() {

        if (!haveStoragePermission())
            return null;
        try {
            if (mediaUri == null)
                return null;
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), mediaUri);
            return getBytes(bitmap);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    private boolean haveStoragePermission() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED;
    }

    public String getContentType() {
        return "image/*";
    }


    public interface SelectImageUtilListener {
        void onIntentReady(Intent intent, int requestCode);

        void requestStoragePermission();
    }
}



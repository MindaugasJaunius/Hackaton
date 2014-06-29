package com.happyshoper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends BaseSampleActivity {

    private static final int TAKE_PICTURE_REQUEST_B = 100;

    private Bitmap mCameraBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String action = "";
        if(getIntent().getExtras() != null) {
            action = getIntent().getExtras().getString("action");
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.simple_underlines);

//        int id = this.getResources().getIdentifier("android:id/search_src_text", null, null);
//        TextView textView = (TextView)findViewById(id);
//        textView.setTextColor(Color.BLACK);

        mAdapter = new TestFragmentAdapter(getSupportFragmentManager(), getApplicationContext());
        mAdapter.setNotification("moneyIncoming");

        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        UnderlinePageIndicator indicator = (UnderlinePageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        indicator.setFades(false);
        mIndicator = indicator;
        mIndicator.setCurrentItem(1);

        if(action.equalsIgnoreCase("signed")){
            Toast.makeText(MainActivity.this, "Forma buvo išsiųsta",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == TAKE_PICTURE_REQUEST_B) {
//            if (resultCode == RESULT_OK) {
//                // Recycle the previous bitmap.
//                if (mCameraBitmap != null) {
//                   mCameraBitmap.recycle();
//                    mCameraBitmap = null;
//                }
//                Bundle extras = data.getExtras();
//                byte[] cameraData = extras.getByteArray(CameraActivity.EXTRA_CAMERA_DATA);
//                if (cameraData != null) {
                    //mCameraBitmap = BitmapFactory.decodeByteArray(cameraData, 0, cameraData.length);
                    //saveImageToFile(openFileForImage());
                    Intent mIntent = new Intent(getApplicationContext(), FullReceiptInfoActivity.class);
                    startActivity(mIntent);
//                }
//            } else {
//                mCameraBitmap = null;
//            }
       // }
    }

//    private void startImageCapture() {
//        startActivityForResult(new Intent(MainActivity.this, CameraActivity.class), TAKE_PICTURE_REQUEST_B);
//    }

    private File openFileForImage() {
        File imageDirectory = null;
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            imageDirectory = new File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "com.happyshoper");
            if (!imageDirectory.exists() && !imageDirectory.mkdirs()) {
                imageDirectory = null;
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_mm_dd_hh_mm",
                        Locale.getDefault());

                return new File(imageDirectory.getPath() +
                        File.separator + "image_" +
                        dateFormat.format(new Date()) + ".png");
            }
        }
        return null;
    }

    private void saveImageToFile(File file) {
        if (mCameraBitmap != null) {
            FileOutputStream outStream = null;
            try {
                outStream = new FileOutputStream(file);
                if (!mCameraBitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)) {
                    Toast.makeText(MainActivity.this, "Unable to save image to file.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Saved image to: " + file.getPath(),
                            Toast.LENGTH_LONG).show();
                }
                outStream.close();
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "Unable to save image to file.",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}

package com.happyshoper;

import java.io.IOException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CameraActivity extends Activity implements PictureCallback, SurfaceHolder.Callback {

    public static final String EXTRA_CAMERA_DATA = "camera_data";

    private static final String KEY_IS_CAPTURING = "is_capturing";

    private Camera mCamera;
    private ImageView mCameraImage;
    private SurfaceView mCameraPreview;
    private Button mSaveImageButton;
    private Button mCaptureImageButton;
    private byte[] mCameraData;
    private boolean mIsCapturing;
    //private Spinner mCategoriesDropdown;
    private ImageView btnShoes;
    private ImageView btnCloths;
    private ImageView btnHardware;
    private ImageView btnHome;
//    Button cust;
    Dialog custom;
    EditText Fname;
    EditText Lname;
    Button savebtn;
    Button canbtn;
    String fname;
    String lname;
    boolean isShoesSelected = false;
    boolean isClothsSelected = false;
    boolean isHardwareSelected = false;
    RelativeLayout categoryButtons;

    private OnClickListener mCaptureImageButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            captureImage();
        }
    };

    private OnClickListener mRecaptureImageButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            setupImageCapture();
        }
    };

    private OnClickListener mDoneButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mCameraData != null) {
                Intent intent = new Intent();
                intent.putExtra(EXTRA_CAMERA_DATA, mCameraData);
                setResult(RESULT_OK, intent);
            } else {
                setResult(RESULT_CANCELED);
            }
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);

        mCameraImage = (ImageView) findViewById(R.id.camera_image_view);
        mCameraImage.setVisibility(View.INVISIBLE);

        mCameraPreview = (SurfaceView) findViewById(R.id.preview_view);
        final SurfaceHolder surfaceHolder = mCameraPreview.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        //mCategoriesDropdown = (Spinner)findViewById(R.id.categoriesDropdown);
        btnHome = (ImageView)findViewById(R.id.homeButton);
        btnHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(CameraActivity.this, MainActivity.class);
                startActivity(mIntent);
            }
        });
        categoryButtons = (RelativeLayout)findViewById(R.id.categoryButtons);
        btnShoes = (ImageView)findViewById(R.id.categoryShoes);
        btnCloths = (ImageView)findViewById(R.id.categoryClothing);
        btnHardware = (ImageView)findViewById(R.id.categoryHardware);

        btnShoes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isShoesSelected){
                    btnShoes.setImageResource(R.drawable.batas_on);
                    isShoesSelected = true;
                } else {
                    btnShoes.setImageResource(R.drawable.batas);
                    isShoesSelected = false;
                }
            }
        });

        btnCloths.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isClothsSelected){
                    btnCloths.setImageResource(R.drawable.maik_on);
                    isClothsSelected = true;

                } else {
                    btnCloths.setImageResource(R.drawable.maike);
                    isClothsSelected = false;
                }
            }
        });

        btnHardware.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isHardwareSelected){
                    btnHardware.setImageResource(R.drawable.kompas_on);
                    isHardwareSelected = true;
                } else {
                    btnHardware.setImageResource(R.drawable.kompas);
                    isHardwareSelected = false;
                }
            }
        });

        mCaptureImageButton = (Button) findViewById(R.id.capture_image_button);
        mCaptureImageButton.setOnClickListener(mCaptureImageButtonClickListener);

        mSaveImageButton = (Button) findViewById(R.id.done_button);
        mSaveImageButton.setOnClickListener(mDoneButtonClickListener);

        mIsCapturing = true;
        final Toast tag = Toast.makeText(CameraActivity.this, "Įsitikinkite, kad čekis telpa į kameroje nurdorytus rėmelius.\n" +
                        "Čekyje taip pat turi aiškiai matytis:\n" +
                        "- Įmonės rekvizitai\n" +
                        "- kvito numeris\n" +
                        "- prekės pavadinimas\n" +
                        "- kaina\n" +
                        "- data bei laikas.",
                    Toast.LENGTH_LONG);


        tag.show();

        new CountDownTimer(2000, 1000)
        {

            public void onTick(long millisUntilFinished) {tag.show();}
            public void onFinish() {tag.show();}

        }.start();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putBoolean(KEY_IS_CAPTURING, mIsCapturing);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mIsCapturing = savedInstanceState.getBoolean(KEY_IS_CAPTURING, mCameraData == null);
        if (mCameraData != null) {
            setupImageDisplay();
        } else {
            setupImageCapture();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mCamera == null) {
            try {
                mCamera = Camera.open();
                mCamera.setDisplayOrientation(90);
                mCamera.setPreviewDisplay(mCameraPreview.getHolder());
                if (mIsCapturing) {
                    mCamera.startPreview();
                }
            } catch (Exception e) {
                Toast.makeText(CameraActivity.this, "Unable to open camera.", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        mCameraData = data;
        setupImageDisplay();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mCamera != null) {
            try {
                mCamera.setPreviewDisplay(holder);
                if (mIsCapturing) {
                    mCamera.startPreview();
                }
            } catch (IOException e) {
                Toast.makeText(CameraActivity.this, "Unable to start camera preview.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    private void captureImage() {
        mCamera.takePicture(null, null, this);
    }

    private void setupImageCapture() {
        mCameraImage.setVisibility(View.INVISIBLE);
        mCameraPreview.setVisibility(View.VISIBLE);
        mCamera.startPreview();
        mCaptureImageButton.setText(R.string.capture_image);
        mSaveImageButton.setVisibility(View.GONE);
//        btnCloths.setVisibility(View.GONE);
//        btnShoes.setVisibility(View.GONE);
//        btnHardware.setVisibility(View.GONE);
        categoryButtons.setVisibility(View.GONE);
        mCaptureImageButton.setOnClickListener(mCaptureImageButtonClickListener);
    }

    private void setupImageDisplay() {
        Bitmap source = BitmapFactory.decodeByteArray(mCameraData, 0, mCameraData.length);
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        mCameraImage.setImageBitmap( Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true));
        mCamera.stopPreview();
        mCameraPreview.setVisibility(View.INVISIBLE);
        mCameraImage.setVisibility(View.VISIBLE);
        mCaptureImageButton.setText(R.string.recapture_image);
        mCaptureImageButton.setOnClickListener(mRecaptureImageButtonClickListener);
        mSaveImageButton.setVisibility(View.VISIBLE);
        //mCategoriesDropdown.setVisibility(View.VISIBLE);
        categoryButtons.setVisibility(View.VISIBLE);
    }

}

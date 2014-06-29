package com.happyshoper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public final class TestFragment extends Fragment implements OnClickListener {
    private static final String KEY_CONTENT = "TestFragment:Content";
    private static final String[] MOBILE_OS = new String[] {
            "Android", "iOS","Windows", "Blackberry" };
    private Context context;
    private String mContent = "???";

    public static TestFragment newInstance(String content, Context context) {
        TestFragment fragment = new TestFragment();

        StringBuilder builder = new StringBuilder();

        builder.append(content);

        String page = builder.toString();

        fragment.mContent = page;
        fragment.context = context;
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null)
                && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        LinearLayout layout = null;

        if(mContent.equalsIgnoreCase("partners")) {
            return inflater.inflate(
                    R.layout.partners, null);
        } else if(mContent.equalsIgnoreCase("advices")) {
            return inflater.inflate(
                    R.layout.advices, null);
        } else {
            layout = (LinearLayout) inflater.inflate(
                    R.layout.home, null);
            GridView gridView = (GridView) layout.findViewById(R.id.receiptGridView);

            gridView.setAdapter(new ImageAdapter(context, MOBILE_OS));

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent mIntent = new Intent(context, FullReceiptInfoActivity.class);
                    startActivity(mIntent);
                }
            });

            ImageView takePhoto = (ImageView)layout.findViewById(R.id.takePhotoButton);
            takePhoto.setOnClickListener(this);
        }


        return layout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }

    @Override
    public void onClick(View v) {

        startActivityForResult(new Intent(context, CameraActivity.class), 100);
//        if (mContent.contains("lowBalance")) {
//            Intent myIntent = new Intent(v.getContext(),
//                    TransferOptionsActivity.class);
//            myIntent.putExtra("key", "some text"); // Optional parameters
//            startActivity(myIntent);
//        } else {
//            Intent myIntent = new Intent(v.getContext(),
//                    SavingsGoalsActivity.class);
//            myIntent.putExtra("key", "some text"); // Optional parameters
//            startActivity(myIntent);
//        }
    }

}

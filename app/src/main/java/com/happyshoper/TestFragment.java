package com.happyshoper;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public final class TestFragment extends Fragment implements OnClickListener {
    private static final String KEY_CONTENT = "TestFragment:Content";

    public static TestFragment newInstance(String content) {
        TestFragment fragment = new TestFragment();

        StringBuilder builder = new StringBuilder();

        builder.append(content);

        String page = builder.toString();

        fragment.mContent = page;

        return fragment;
    }

    private String mContent = "???";

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

        FrameLayout layout = null;

        layout = (FrameLayout) inflater.inflate(
                R.layout.home, null);
        TextView header = (TextView) layout
                .findViewById(R.id.notificationHeader);
        header.setText("Current balance:");
        TextView message = (TextView) layout
                .findViewById(R.id.notificationMessage);
        message.setText("249,32 kr");


        return layout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }

    @Override
    public void onClick(View v) {
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

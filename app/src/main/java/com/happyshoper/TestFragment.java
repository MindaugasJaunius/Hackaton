package com.happyshoper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public final class TestFragment extends Fragment implements OnClickListener, View.OnTouchListener {
    private static final String KEY_CONTENT = "TestFragment:Content";
    private static final String[] MOBILE_OS = new String[] {
            "Android", "iOS","Windows", "Blackberry" };
    private Context context;
    private String mContent = "???";
    private Animation slide_in_left, slide_out_right;
    private ViewSwitcher viewSwitcher;
    private GestureDetector gestureDetector;


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
            ViewSwitcher layoutAdvices = (ViewSwitcher) inflater.inflate(
                    R.layout.advices, null);
            TextView textView1 = (TextView) layoutAdvices.findViewById(R.id.adviceTextView1);
            TextView textView2 = (TextView) layoutAdvices.findViewById(R.id.adviceTextView2);
            TextView textView3 = (TextView) layoutAdvices.findViewById(R.id.adviceTextView3);
            TextView textView4 = (TextView) layoutAdvices.findViewById(R.id.adviceTextView4);
            TextView textView5 = (TextView) layoutAdvices.findViewById(R.id.adviceTextView5);

            final TextView textViewContent1 = (TextView) layoutAdvices.findViewById(R.id.adviceTextViewContent1);
            final TextView textViewContent2 = (TextView) layoutAdvices.findViewById(R.id.adviceTextViewContent2);
            final TextView textViewContent3 = (TextView) layoutAdvices.findViewById(R.id.adviceTextViewContent3);
            final TextView textViewContent4 = (TextView) layoutAdvices.findViewById(R.id.adviceTextViewContent4);
            final TextView textViewContent5 = (TextView) layoutAdvices.findViewById(R.id.adviceTextViewContent5);

            textViewContent1.setVisibility(View.GONE);
            textViewContent2.setVisibility(View.GONE);
            textViewContent3.setVisibility(View.GONE);
            textViewContent4.setVisibility(View.GONE);
            textViewContent5.setVisibility(View.GONE);

            expandAdvice(textView1, textViewContent1);
            expandAdvice(textView2, textViewContent2);
            expandAdvice(textView3, textViewContent3);
            expandAdvice(textView4, textViewContent4);
            expandAdvice(textView5, textViewContent5);

            //viewSwitcher = (ViewSwitcher) layoutAdvices.findViewById(R.id.viewswitcher);

            slide_in_left = AnimationUtils.loadAnimation(context,
                    android.R.anim.slide_in_left);
            slide_out_right = AnimationUtils.loadAnimation(context,
                    android.R.anim.slide_out_right);

            layoutAdvices.setInAnimation(slide_in_left);
            layoutAdvices.setOutAnimation(slide_out_right);

            gestureDetector = new GestureDetector(context, new GestureListener());

            layoutAdvices.setOnTouchListener(this);
            viewSwitcher = layoutAdvices;
            return layoutAdvices;

        } else {
            layout = (LinearLayout) inflater.inflate(
                    R.layout.home, null);
            GridView gridView = (GridView) layout.findViewById(R.id.receiptGridView);

            gridView.setAdapter(new ImageAdapter(context));

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

    private void expandAdvice(final TextView textView, final TextView textViewContent) {
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textViewContent.isShown()){
                    slideUp(view.getContext(), textViewContent);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            textViewContent.setVisibility(View.GONE);
                        }
                    }, 300);

                } else {
                    textViewContent.setVisibility(View.VISIBLE);
                    slideDown(view.getContext(), textViewContent);
                }
            }
        });
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
    private static void slideDown(Context ctx, View v){
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }
    private static void slideUp(Context ctx, View v){
        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
        if(a != null){
            a.reset();
            if(v != null){
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void onSwipeRight() {
        viewSwitcher.showNext();
    }

    public void onSwipeLeft() {
        viewSwitcher.showPrevious();
    }

    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}

package com.happyshoper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Mindaugas on 6/29/2014.
 */
public class FillFormActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fill_form);
        Button ok = (Button)findViewById(R.id.fillFormOkButton);
        Button back = (Button)findViewById(R.id.fillFormBackButton);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(FillFormActivity.this, PdfWebViewActivity.class);
                mIntent.putExtra("url", "http://jurgiz.lt/forma.pdf");
                mIntent.putExtra("type", "form");
                startActivity(mIntent);
            }
        });
    }
}

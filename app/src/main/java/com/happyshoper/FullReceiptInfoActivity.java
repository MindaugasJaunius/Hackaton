package com.happyshoper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Mindaugas on 6/29/2014.
 */
public class FullReceiptInfoActivity extends Activity {
    Dialog custom;
    EditText Fname;
    EditText Lname;
    Button savebtn;
    Button canbtn;
    String fname;
    String lname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.full_receipt_info);

        TextView userRights = (TextView)findViewById(R.id.userRights);
        userRights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(FullReceiptInfoActivity.this, PdfWebViewActivity.class);
                mIntent.putExtra("url", "http://jurgiz.lt/prekes.pdf");
                mIntent.putExtra("type", "userRights");
                startActivity(mIntent);
            }
        });

        TextView category = (TextView)findViewById(R.id.productCategory);
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCategoryDialog();
            }
        });

    }

    private void showCategoryDialog(){
        custom = new Dialog(FullReceiptInfoActivity.this);
        custom.setContentView(R.layout.category_dialog);
        Fname = (EditText)custom.findViewById(R.id.fname);
        Lname = (EditText)custom.findViewById(R.id.lname);
        savebtn = (Button)custom.findViewById(R.id.savebtn);
        canbtn = (Button)custom.findViewById(R.id.canbtn);
        custom.setTitle("Custom Dialog");
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                fname = Fname.getText().toString();
                lname = Lname.getText().toString();
                custom.dismiss();
            }
        });
        canbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                custom.dismiss();
            }
        });
        custom.show();
    }


}

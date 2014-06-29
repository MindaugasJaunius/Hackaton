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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Mindaugas on 6/29/2014.
 */
public class FullReceiptInfoActivity extends Activity {
    Dialog custom;
    CheckBox checkClothing;
    CheckBox checkShoes;
    CheckBox checkHardware;
    Button savebtn;
    Button canbtn;
    TextView category;

    //LinearLayout layout;

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

        category = (TextView)findViewById(R.id.productCategory);
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCategoryDialog();
            }
        });
        ImageView imageView = (ImageView) findViewById(R.id.fillReturnFormButton);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(FullReceiptInfoActivity.this, FillFormActivity.class);
                startActivity(mIntent);
            }
        });

    }

    private void showCategoryDialog(){
        custom = new Dialog(FullReceiptInfoActivity.this);
        custom.setContentView(R.layout.category_dialog);
        checkClothing = (CheckBox) custom.findViewById(R.id.checkBoxClothingCategory);
        checkShoes = (CheckBox) custom.findViewById(R.id.checkBoxShoesCategory);
        checkShoes.setChecked(true);
        checkHardware = (CheckBox) custom.findViewById(R.id.checkBoxHardwareCategory);
        savebtn = (Button)custom.findViewById(R.id.savebtn);
        canbtn = (Button)custom.findViewById(R.id.canbtn);
        custom.setTitle("Choose Product Category");
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkClothing.isChecked()){
                 category.setText("Drabuziai");
                }else if (checkHardware.isChecked()){
                  category.setText("Elektronika");
                } else if (checkShoes.isChecked()) {
                   category.setText("Avalyne");
                }

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

package com.happyshoper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ImageAdapter extends BaseAdapter {
    private String category;
    private Context context;
    private final int[] receiptValuesShoes = new int[]{R.drawable.cekis1, R.drawable.cekis2, R.drawable.cekis3, R.drawable.cekis2};
    private final int[] receiptValuesCloths = new int[]{R.drawable.cekis1, R.drawable.cekis2, R.drawable.cekis3, R.drawable.cekis3, R.drawable.cekis3};
    private final int[] receiptValuesHardware = new int[]{R.drawable.cekis1, R.drawable.cekis2, R.drawable.cekis3};


    public ImageAdapter(Context context) {
        this.context = context;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.small_receipt_image, null);


            // set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);
            TextView shopNameTextView = (TextView) gridView.findViewById(R.id.imageShopNameTextView);
            TextView shopAddressTextView = (TextView) gridView.findViewById(R.id.imageAddressTextView);
            TextView shopDateTextView = (TextView) gridView.findViewById(R.id.imageDateTextView);

            if(category.equalsIgnoreCase("shoes")) {
                imageView.setImageResource(receiptValuesShoes[position]);
            } else if(category.equalsIgnoreCase("cloths")){
                imageView.setImageResource(receiptValuesCloths[position]);
            } else if(category.equalsIgnoreCase("hardware")){
                imageView.setImageResource(receiptValuesHardware[position]);
            }
                shopNameTextView.setText("Danija H&M");
                shopAddressTextView.setText("Ozo g. 25, Vilnius");
                shopDateTextView.setText("2014-05-06");


        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        if(category.equalsIgnoreCase("shoes")){
            return receiptValuesShoes.length;
        } else if (category.equalsIgnoreCase("cloths")){
            return receiptValuesCloths.length;
        }else if(category.equalsIgnoreCase("hardware")){
            return receiptValuesHardware.length;
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
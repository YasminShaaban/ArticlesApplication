package com.example.toshiba.articlesapplication;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by toshiba on 8/12/2017.
 */

public class Data {
    String image;
    String txt1;
    String txt2;
    ImageView imageview;
    View view2;
    Context c;
    Data(String image,String txt1,String txt2){
    this.image=image;
    this.txt1=txt1;
    this.txt2=txt2;
    }

    public   String getImage() {
       /* imageview=(ImageView)view2.findViewById(R.layout.list_row);
        Picasso.with(c).load(image).into(imageview);
        return imageview;*/
       return image;

    }

    public void setImage(ImageView image) {
        this.imageview=image;

    }

    public String getTxt1() {
        return txt1;
    }

    public void setTxt1(String txt1) {
        this.txt1 = txt1;
    }

    public String getTxt2() {
        return txt2;
    }

    public void setTxt2(String txt2) {
        this.txt2 = txt2;
    }
}

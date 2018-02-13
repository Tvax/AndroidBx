package com.ligue1.applicationligue1.util;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.ahmadrosid.svgloader.SvgLoader;
import com.squareup.picasso.Picasso;

public final class Display {

    public static void displayToast(String message, Context context){
        Toast t = Toast.makeText(context, message, Toast.LENGTH_LONG);
        t.show();
    }

    public static void setImageViewToImage(String url, Activity activity, Context context, ImageView imageView){
        if (isSVG(url)){
            SvgLoader.pluck().with(activity)
                    .load(url, imageView);
        }
        else {
            Picasso.with(context)
                    .load(url)
                    .into(imageView);
        }
    }

    private static boolean isSVG(String string){
        return string.endsWith(".svg");
    }


}

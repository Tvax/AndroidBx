package com.ligue1.applicationligue1.util;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.ahmadrosid.svgloader.SvgLoader;
import com.squareup.picasso.Picasso;

/**
 * L'affichage des images et des {@link Toast}
 */
public final class Display {

    /**
     * Afficher un Toast
     * @param message le contenu du message à afficher
     * @param context le contexte de l'application
     */
    public static void displayToast(String message, Context context){
        Toast t = Toast.makeText(context, message, Toast.LENGTH_LONG);
        t.show();
    }

    /**
     * Télécharge et assigne à une {@link ImageView} une image
     * @param url lien de l'image
     * @param activity l'activité de l'{@link ImageView}
     * @param context le contexte de l'application
     * @param imageView {@link ImageView} à laquelle assigner une image
     */
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

    /**
     * Vérifie qu'un lien correspond à un lien de fichier SVG
     * @param url lien de l'image
     * @return si {@see url} est un lien de fichier SVG
     */
    private static boolean isSVG(String url){
        return url.endsWith(".svg");
    }


}

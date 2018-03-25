package com.example.shushantkumar.videorequest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.MediaController;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Sketch Project Studio
 * Created by Angga on 12/04/2016 14.27.
 */
public class AppHelper {
    public MediaController mc;


    /**
     * Turn drawable resource into byte array.
     *
     * @param context parent context
     * @param id      drawable resource id
     * @return byte array
     */
    public static byte[] getFileDataFromDrawable(Context context, int id) {
        Drawable drawable = ContextCompat.getDrawable(context, id);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

//    /**
//     * Turn drawable into byte array.
//     *
//     * @param context data
//     * @return byte array
//     */
    public static byte[] getFileDataFromDrawable(Context context, String path) throws FileNotFoundException {
//        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
//        Bitmap bitmap;
//        bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), android.graphics.Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//        drawable.draw(canvas);
//
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
//        return byteArrayOutputStream.toByteArray();


        FileInputStream fis = new FileInputStream(path);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];

        try {
            for (int readNum; (readNum = fis.read(b)) != -1;) {
                bos.write(b, 0, readNum);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] bytes = bos.toByteArray();

        return bytes;
    }


}

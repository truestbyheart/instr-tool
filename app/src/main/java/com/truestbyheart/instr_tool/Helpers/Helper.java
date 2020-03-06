package com.truestbyheart.instr_tool.Helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;

public class Helper {
    private Context mContext;

    public Helper(Context mContext) {
        this.mContext = mContext;
    }

    public static Target picassoImageTarget(Context context) {
        return new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(() -> {
                    final File imageFile = new File(Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                            .toString() + "/Instr"
                    );

                    if (!imageFile.exists()) {
                        imageFile.mkdir();
                    }

                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(new File(imageFile, UUID.randomUUID() + ".jpg"));
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.flush();
                        fos.close();
                    } catch (IOException e) {
                        Log.d("Download Error", "onBitmapLoaded: " + e);
                    } finally {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            Log.d("Failure to close", "onBitmapLoaded: " + e);
                        }
                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
    }

    public Picasso picassoDownloader() {
        final OkHttpClient client = new OkHttpClient.Builder()
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .build();

        return new Picasso.Builder(mContext)
                .downloader(new OkHttp3Downloader(client))
                .build();

    }


}

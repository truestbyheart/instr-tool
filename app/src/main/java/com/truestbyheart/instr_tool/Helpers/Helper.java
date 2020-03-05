package com.truestbyheart.instr_tool.Helpers;

import android.content.Context;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.Collections;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;

public class Helper {
    private Context mContext;

    public Helper(Context mContext) {
        this.mContext = mContext;
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

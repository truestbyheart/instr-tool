package com.truestbyheart.instr_tool;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.truestbyheart.instr_tool.API.Interfaces.PostData;
import com.truestbyheart.instr_tool.API.RetrofitClient;
import com.truestbyheart.instr_tool.API.models.PostModel;
import com.truestbyheart.instr_tool.Adapters.ResultAdapter;
import com.truestbyheart.instr_tool.Layouts.Single;

import java.util.ArrayList;
import java.util.Collections;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    EditText instLink;
    Button sendLink;
    RecyclerView rvPost;
    ResultAdapter resultAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init();
    }

    private void init() {
        instLink = findViewById(R.id.link);
        sendLink = findViewById(R.id.getImage);


        sendLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInstrData();
            }
        });
    }

    private void getInstrData() {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        PostData postData = retrofit.create(PostData.class);
        String link = instLink.getText().toString();

        Call call = postData.getImage(link);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.body() != null) {
                    PostModel postModel = (PostModel) response.body();
                        if (postModel.getImgURL() != null) {
                            Intent singleIntent = new Intent(MainActivity.this, Single.class);
                            singleIntent.putExtra("post", postModel);
                            startActivity(singleIntent);
                        } else if (postModel.getImgURLs() != null) {
                            Log.d("TAG", "onResponse: " + postModel.getImgURLs());
                        } else if (postModel.getVideoURL() != null) {
                            Log.d("TAG", "onResponse: " + postModel.getVideoURL());
                        }


                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("TAG", "onFailure: " + t);
            }
        });
    }
}
package com.truestbyheart.instr_tool;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.truestbyheart.instr_tool.API.Interfaces.PostData;
import com.truestbyheart.instr_tool.API.RetrofitClient;
import com.truestbyheart.instr_tool.API.models.PostModel;
import com.truestbyheart.instr_tool.Layouts.Single;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    EditText instLink;
    Button sendLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyhavePermission()) {
                requestForSpecificPermission();
            }
        }

        init();
    }


    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                } else {
                    //not granted
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
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
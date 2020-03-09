package com.truestbyheart.Instr;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.truestbyheart.Instr.API.Interfaces.PostData;
import com.truestbyheart.Instr.API.RetrofitClient;
import com.truestbyheart.Instr.API.models.PostModel;
import com.truestbyheart.Instr.Helpers.LoadingHelper;
import com.truestbyheart.Instr.Layouts.Single;
import com.truestbyheart.Instr.Layouts.VideoSingle;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    EditText instLink;
    Button sendLink, pasteBtn;
    String txtPaste;
    LoadingHelper loadingHelper;

    ClipboardManager clipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);


        Log.d("Paste", "onCreate: " + txtPaste);

        int MyVersion = Build.VERSION.SDK_INT;
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyhavePermission()) {
                requestForSpecificPermission();
            }
        }

        loadingHelper = new LoadingHelper(this);

        init();
    }


    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_NETWORK_STATE);

        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED &&
                result2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_EXTERNAL_STORAGE
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
        pasteBtn = findViewById(R.id.paste_btn);


        sendLink.setOnClickListener(v -> getInstrData());

        pasteBtn.setOnClickListener(v -> {

            // Retrieve the text copied in the clipboard manager
            assert clipboardManager != null;
            ClipData pData = clipboardManager.getPrimaryClip();

            assert pData != null;
            ClipData.Item item = pData.getItemAt(0);

            txtPaste = item.getText().toString();

            // Check if the text is an Instagram link.
            Pattern mPattern = Pattern.compile("instagram.com");
            Matcher matcher = mPattern.matcher(txtPaste);

            if (matcher.find()) {
                instLink.setText(txtPaste);
//                instLink.setWidth(View.ma);
            } else {
                Toast.makeText(this, "Please provide a Instagram link", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void getInstrData() {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        PostData postData = retrofit.create(PostData.class);
        String link = instLink.getText().toString();

        Call call = postData.getImage(link);
        loadingHelper.startLoadingDialoag();
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.body() != null) {

                    PostModel postModel = (PostModel) response.body();

                    if (postModel.getImgURL() != null) {
                        loadingHelper.stopLoadingDialog();
                        Intent singleIntent = new Intent(MainActivity.this, Single.class);
                        singleIntent.putExtra("post", postModel);
                        startActivity(singleIntent);
                    } else if (postModel.getVideoURL() != null) {
                        loadingHelper.stopLoadingDialog();
                        Intent videoIntent = new Intent(MainActivity.this, VideoSingle.class);
                        videoIntent.putExtra("post", postModel);
                        startActivity(videoIntent);
                    }


                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                loadingHelper.stopLoadingDialog();
                Log.d("TAG", "onFailure: " + t);
            }
        });
    }
}
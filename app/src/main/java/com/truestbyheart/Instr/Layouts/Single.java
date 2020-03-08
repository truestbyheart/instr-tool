package com.truestbyheart.Instr.Layouts;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.truestbyheart.Instr.API.models.PostModel;
import com.truestbyheart.Instr.Helpers.Helper;
import com.truestbyheart.Instr.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.view.View.GONE;

public class Single extends AppCompatActivity {
    Helper helper = new Helper(this);
    ImageView postImg;
    TextView postText;
    Picasso picasso;
    ProgressBar progressBar;
    RelativeLayout postImgWaitContainer;
    Button btnDownload, btnRepost;
    ClipboardManager clipboardManager;
    ClipData clipData;
    String toBeCopied;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        postImg = findViewById(R.id.post_image);
        postText = findViewById(R.id.post_text);
        btnDownload = findViewById(R.id.download);
        btnRepost = findViewById(R.id.repost);
        progressBar = findViewById(R.id.post_wait);
        postImgWaitContainer = findViewById(R.id.post_wait_container);

        picasso = helper.picassoDownloader();

        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        Intent intent = getIntent();
        PostModel postModel = intent.getParcelableExtra("post");

        toBeCopied = "Repost by @instr-tool " + postModel.getPostText();



        postImg.setVisibility(GONE);
        progressBar.setVisibility(View.VISIBLE);


        imageHandler(postModel.getImgURL());

        postText.setText(postModel.getPostText());

        btnDownload.setOnClickListener(v -> {
            picasso
                    .load(postModel.getImgURL())
                    .into(Helper.picassoImageTarget(Single.this));
            Toast.makeText(Single.this, "Image saved successfully", Toast.LENGTH_SHORT).show();
        });

        btnRepost.setOnClickListener(v -> {
            picasso.load(postModel.getImgURL())
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            clipData = ClipData.newPlainText("text", toBeCopied);
                            clipboardManager.setPrimaryClip(clipData);

                            Intent intent = new Intent("android.intent.action.SEND");
                            intent.setType("image/*");
                            intent.putExtra("android.intent.extra.STREAM", getlocalBitmapUri(bitmap));
                            startActivity(Intent.createChooser(intent, "share"));
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
        });

    }


    private void imageHandler(String url){
        picasso
                .load(url)
                .error(R.drawable.ic_launcher_background)
                .into(postImg, new Callback() {
                    @Override
                    public void onSuccess() {
                        postImgWaitContainer.setVisibility(GONE);
                        progressBar.setVisibility(GONE);
                        postImg.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    private Uri getlocalBitmapUri(Bitmap bitmap) {
        Uri imgUri = null;
        try {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.close();
            imgUri = Uri.fromFile(file);
        } catch (IOException e) {
            Log.d("Single", "getlocalBitmapUri: " + e);
        }

        return imgUri;
    }
}

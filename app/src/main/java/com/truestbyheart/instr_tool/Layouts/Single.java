package com.truestbyheart.instr_tool.Layouts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.truestbyheart.instr_tool.API.models.PostModel;
import com.truestbyheart.instr_tool.Helpers.Helper;
import com.truestbyheart.instr_tool.R;

public class Single extends AppCompatActivity {
    Helper helper = new Helper(this);
    ImageView postImg;
    TextView postText;
    Picasso picasso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        postImg = findViewById(R.id.post_image);
        postText = findViewById(R.id.post_text);

        picasso = helper.picassoDownloader();

        Intent intent = getIntent();
        PostModel postModel = intent.getParcelableExtra("post");

        Log.d("TAG", "onCreate: " + postModel.getImgURL() + postModel.getPostText());

        picasso
                .get()
                .load(postModel.getImgURL())
                .error(R.drawable.ic_launcher_background)
                .into(postImg);

        postText.setText(postModel.getPostText());
    }
}

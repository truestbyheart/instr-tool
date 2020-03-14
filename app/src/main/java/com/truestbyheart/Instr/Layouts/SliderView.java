package com.truestbyheart.Instr.Layouts;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.truestbyheart.Instr.API.models.PostData;
import com.truestbyheart.Instr.API.models.PostModel;
import com.truestbyheart.Instr.R;

import java.util.List;


public class SliderView extends AppCompatActivity {
    TextView fullName, username, postText;
    ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_view);


        // views
        fullName = findViewById(R.id.full_name);
        username = findViewById(R.id.username);
        postText = findViewById(R.id.post_text);
        profile = findViewById(R.id.profile_image);


        Intent intent = getIntent();
        PostModel data = intent.getParcelableExtra("post");

        //declaring a HashMap for associative array implementation.
        assert data != null;
        List<PostData> itemSize = data.getPostData();


        // view pager setup
        ViewPager pager = findViewById(R.id.slider);
        PagerAdapter pagerAdapter = new com.truestbyheart.Instr.Adapters.PagerAdapter(
                getSupportFragmentManager(),
                itemSize);
        pager.setAdapter(pagerAdapter);

        // view assignments
        Glide.with(this).load(data.getOwner().getProfilePic()).into(profile);
        fullName.setText(data.getOwner().getFullName());
        username.setText(data.getOwner().getUsername());
        postText.setText(data.getPostText());
    }
}

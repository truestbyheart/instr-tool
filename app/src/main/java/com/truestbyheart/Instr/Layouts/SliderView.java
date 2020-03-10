package com.truestbyheart.Instr.Layouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.truestbyheart.Instr.API.models.PostModel;
import com.truestbyheart.Instr.Fragments.ImageFragment;
import com.truestbyheart.Instr.Fragments.VideoFragment;
import com.truestbyheart.Instr.Helpers.Helper;
import com.truestbyheart.Instr.R;

import java.util.ArrayList;
import java.util.List;

public class SliderView extends AppCompatActivity {
      TextView fullName, username,postText;
      ImageView profile;
      Helper helper = new Helper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_view);

        // picasso
        Picasso picasso = helper.picassoDownloader();

        // views
        fullName = findViewById(R.id.full_name);
        username = findViewById(R.id.username);
        postText = findViewById(R.id.post_text);
        profile =findViewById(R.id.profile_image);

        List<Fragment> list = new ArrayList<>();
        ImageFragment imageFragment;

        Intent intent = getIntent();
        PostModel data = intent.getParcelableExtra("post");

        Bundle link = new Bundle();

        assert data != null;
        Log.d("data", "onCreate: " + data.getOwner().getUsername());

        for(int i =0; i < data.getPostData().size(); i++) {
            if (data.getPostData().get(i).getImgUrl() != null) {
                imageFragment =new ImageFragment(i);
                link.putString("link", data.getPostData().get(i).getImgUrl());
                imageFragment.setArguments(link);
                list.add(imageFragment);
            }
        }

        // view pager setup
        list.add(new VideoFragment());
        ViewPager pager = findViewById(R.id.slider);
        PagerAdapter pagerAdapter = new com.truestbyheart.Instr.Adapters.PagerAdapter(getSupportFragmentManager(), list);
        pager.setAdapter(pagerAdapter);

        // view assignments
        picasso.load(data.getOwner().getProfilePic()).error(R.drawable.ic_launcher_background).into(profile);
        fullName.setText(data.getOwner().getFullName());
        username.setText(data.getOwner().getUsername());
        postText.setText(data.getPostText());
    }
}

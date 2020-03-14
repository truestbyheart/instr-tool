package com.truestbyheart.Instr.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.truestbyheart.Instr.R;

import java.util.HashMap;


public class SliderFragment extends Fragment {
    private String path;
    private Context mContext;


    public static SliderFragment newInstance(HashMap<String, String> data) {
        SliderFragment fragment = new SliderFragment();
        if (data.get("img") != null) {
            fragment.setPath(data.get("img"));
        } else {
            Log.d("video", "newInstance: " + data.get("video"));
            fragment.setPath(data.get("video"));
        }

        return fragment;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater
                .inflate(R.layout.image_slide,
                        container,
                        false);
        ImageView imageView = view.findViewById(R.id.image_slide);
        Glide.with(mContext).load(path).into(imageView);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

}

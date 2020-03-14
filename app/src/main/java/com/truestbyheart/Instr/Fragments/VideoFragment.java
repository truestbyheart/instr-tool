package com.truestbyheart.Instr.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.truestbyheart.Instr.R;

public class VideoFragment extends Fragment {
    private String videoPath;

    public static VideoFragment newInstance(String videoPath) {
        VideoFragment fragment = new VideoFragment();
        fragment.setVideoPath(videoPath);
        return fragment;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater
                .inflate(R.layout.video_slide,
                        container,
                        false);
    }

}

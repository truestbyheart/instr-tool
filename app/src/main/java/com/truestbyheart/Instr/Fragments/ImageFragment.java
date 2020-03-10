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

import com.squareup.picasso.Picasso;
import com.truestbyheart.Instr.Helpers.Helper;
import com.truestbyheart.Instr.R;


public class ImageFragment extends Fragment {
    Helper helper;
    ImageView imageView;
    private int id;

    public ImageFragment(int id){
        this.id =id;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
       View view = inflater
                .inflate(R.layout.image_slide,
                        container,
                        false);
        imageView = view.findViewById(R.id.image_slide);

        assert getArguments() != null;
        String imgLink = getArguments().getString("link");

        Picasso picasso = helper.picassoDownloader();
        picasso.load(imgLink)
                .error(R.drawable.ic_launcher_background)
                .into(imageView);

        view.setId(id);

        Log.d("image link", "onCreateView: " + imgLink);
        return view;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        helper = new Helper(context);


    }
}

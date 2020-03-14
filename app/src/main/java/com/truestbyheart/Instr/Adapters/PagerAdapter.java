package com.truestbyheart.Instr.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.truestbyheart.Instr.Fragments.SliderFragment;

import java.util.HashMap;
import java.util.List;

public class PagerAdapter extends FragmentPagerAdapter {
    private List<com.truestbyheart.Instr.API.models.PostData> itemSize;


    public PagerAdapter(FragmentManager fm, List<com.truestbyheart.Instr.API.models.PostData> itemSize) {
        super(fm);
        this.itemSize = itemSize;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        HashMap<String, String> img = new HashMap<>();
        HashMap<String, String> video = new HashMap<>();

        if (itemSize.get(position).getImgUrl() != null) {
            img.put("img", itemSize.get(position).getImgUrl());
            return SliderFragment.newInstance(img);
        } else {
            video.put("video", itemSize.get(position).getVideoUrl());
            return SliderFragment.newInstance(video);
        }
    }

    @Override
    public int getCount() {
        return itemSize.size();
    }
}

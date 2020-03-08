package com.truestbyheart.Instr.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;
import com.truestbyheart.Instr.API.models.PostModel;
import com.truestbyheart.Instr.R;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {
    Context mContext;
    ArrayList<PostModel> dataList;

    public ResultAdapter(Context mContext, ArrayList<PostModel> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String url = this.dataList.get(position).getImgURL();
        Log.d("url", "onBindViewHolder: " + url);
        Picasso
                .get()
                .load(url)
                .error(R.drawable.ic_launcher_background)
                .into(holder.postImg);
    }

    @Override
    public int getItemCount() {
        return this.dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView postImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postImg = itemView.findViewById(R.id.postImage);
        }
    }
}

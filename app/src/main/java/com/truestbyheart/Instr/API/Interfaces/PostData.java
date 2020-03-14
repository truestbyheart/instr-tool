package com.truestbyheart.Instr.API.Interfaces;

import com.truestbyheart.Instr.API.models.PostModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostData {
   @FormUrlEncoded
   @POST("/link")
   Call<PostModel> getImage(@Field("link") String Link);
}

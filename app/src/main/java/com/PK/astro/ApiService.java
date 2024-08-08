package com.PK.astro;

import java.util.List;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiService {
    @POST("get_subpage")
    Call<List<Zodiac>> getZodiacData(@Body Map<String, String> body);

    @GET("get_home")
    Call<List<HomeItem>> getHomeItems();

    @POST("get_detail")
    Call<List<YourResponseItem>> getDetail(@Body YourRequestBodyModel body);

    @POST("api/post_enquiry")
    Call<ApiResponse4> postEnquiry(@Body Enquiry enquiry);

    @POST("get_aboutus")
    Call<List<AboutUsResponse>> getAboutUs(@Body CatNameRequest catNameRequest);

    @GET("get_video")
    Call<List<Video>> getVideos();

    @GET
    Call<TrainingResponse[]> getTrainingData(@Url String url);

    @GET("get_aboutus") // Relative URL endpoint
    Call<List<AboutItem>> getAboutUs();

    @GET("api/get_popimage")
    Call<List<ImageResponse>> getImages();

}

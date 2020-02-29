package com.georgebrown.myapplication;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface YelpService {
    // To get all the businesses, only allow 50 maximum
    @GET ("businesses/search")
    Call<ResponseBody> getRest(@Header ("Authorization") String authHeader,@Query("location") String location,@Query("limit")int limit);
    // To get specific business's reviews
    @GET("businesses/{id}/reviews")
    Call<ResponseBody> getReview(@Header ("Authorization") String authHeader,@Path("id") String id);
    // To get specific term of business
    @GET ("businesses/search")
    Call<ResponseBody> getSearchRest(@Header ("Authorization") String authHeader, @Query("term") String searchTerm, @Query("location") String location,@Query("limit") int limit);
}


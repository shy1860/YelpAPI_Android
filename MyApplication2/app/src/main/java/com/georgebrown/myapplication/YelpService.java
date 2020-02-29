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
    //@Headers({"Cache-Control: max-age=640000", "Authorization: Bearer EIXMis1tr7wIkS9QcssPoFA11Hlt6sxfluj94fC1yhlHuFa_q3SxCaKnDq80IA-sXft7266yD_5di7HTF5buYdN9FnrFwyaDkuG-gRPnGByn7TFT3VfGRP9yDWVYXnYx"})
    @GET ("businesses/search")
    Call<ResponseBody> getRest(@Header ("Authorization") String authHeader,@Query("location") String location,@Query("limit")int limit);
    @GET("businesses/{id}/reviews")
    Call<ResponseBody> getReview(@Header ("Authorization") String authHeader,@Path("id") String id);
    @GET ("businesses/search")
    Call<ResponseBody> getSearchRest(@Header ("Authorization") String authHeader, @Query("term") String searchTerm, @Query("location") String location,@Query("limit") int limit);
}


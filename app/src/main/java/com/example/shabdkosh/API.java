package com.example.shabdkosh;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface API {
    public final String
            BASE_URL = "https://od-api.oxforddictionaries.com/api/v2/entries/en-gb/",
            APP_ID="f829c0bc",
            APP_KEY="358f2825e86660276c5452b77c5d406a";

    @GET("{id}?fields=etymologies&strictMatch=false")
    Call<Word> getData(@Path("id") String word, @Header("app_id") String app_id, @Header("app_key") String app_key);
}

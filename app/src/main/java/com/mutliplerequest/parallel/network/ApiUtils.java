package com.mutliplerequest.parallel.network;

public class ApiUtils {

    public static final int SECONDS_30   = 90;
    public static final int SECONDS_60   = 60;
    public static final String BASE_URL  = "https://blog.truecaller.com";

    public ApiUtils() {
    }

    public static ApiInterface getApiService(){
        return APIClient.getApiClient(BASE_URL, SECONDS_30).create(ApiInterface.class);
    }
}

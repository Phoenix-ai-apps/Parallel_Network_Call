package com.mutliplerequest.parallel.network;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface ApiInterface {

    /*@GET("/2018/01/22/life-as-an-android-engineer")
    Call<ResponseBody> getContent();
    */
    @GET("/2018/01/22/life-as-an-android-engineer/")
    Observable<ResponseBody> getContent1();

    @GET("/2012/06/08/free-number-lookup-on-truecallers-website/")
    Observable<ResponseBody> getContent2();

    @GET("/2016/02/15/how-to-enable-truecaller-caller-id-on-samsung/")
    Observable<ResponseBody> getContent3();


}

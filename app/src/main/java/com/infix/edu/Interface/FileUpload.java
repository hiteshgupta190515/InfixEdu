package com.infix.edu.Interface;

import com.infix.edu.myconfig.MyConfig;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FileUpload {

    @Multipart
    @POST(MyConfig.UPLOAD_HOMEWORK)
    Call<String> uploadFile(@Part MultipartBody.Part file);

}

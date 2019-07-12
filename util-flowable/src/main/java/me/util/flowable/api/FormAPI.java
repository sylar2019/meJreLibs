package me.util.flowable.api;

import me.util.flowable.model.form.FormData;
import me.util.flowable.model.form.SubmitFormRequest;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: luhao
 * @create: 2019-7-2 18:22
 */
public interface FormAPI {

    @GET("form/form-data")
    List<FormData> getFormDatas();

    @GET("form/form-data")
    List<FormData> getFormDatas(@QueryMap Map<String, Object> params);

    @POST("for/form-data")
    Void createFormData(@Body SubmitFormRequest request);
}

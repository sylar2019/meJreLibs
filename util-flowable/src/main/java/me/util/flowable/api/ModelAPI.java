package me.util.flowable.api;

import me.util.flowable.model.Paging;
import me.util.flowable.model.models.Model;
import me.util.flowable.model.models.ModelRequest;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: luhao
 * @create: 2019-7-2 18:22
 */
public interface ModelAPI {

    @GET("repository/models")
    Paging<Model> getModels();

    @GET("repository/models")
    Paging<Model> getModels(@QueryMap Map<String, Object> params);

    @POST("repository/models")
    Model createModel(@Body ModelRequest request);

    @DELETE("repository/models/{modelId}")
    Void deleteModel(@Path("modelId") String modelId);

    @GET("repository/models/{modelId}")
    Model getModel(@Path("modelId") String modelId);

    @PUT("repository/models/{modelId}")
    Model getModel(@Body ModelRequest request);

    @GET("repository/models/{modelId}/source")
    List<String> getModelSource(@Path("modelId") String modelId);
}

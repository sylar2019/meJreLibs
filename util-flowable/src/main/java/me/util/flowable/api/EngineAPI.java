package me.util.flowable.api;

import me.util.flowable.model.engine.EngineInfo;
import retrofit2.http.GET;

/**
 * @description:
 * @author: luhao
 * @create: 2019-7-2 18:22
 */
public interface EngineAPI {

    @GET("management/engine")
    EngineInfo getEngineInfo();
}

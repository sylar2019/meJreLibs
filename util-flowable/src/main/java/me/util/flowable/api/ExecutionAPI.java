package me.util.flowable.api;

import me.util.flowable.filter.ExecutionQuery;
import me.util.flowable.model.Paging;
import me.util.flowable.model.RestVariable;
import me.util.flowable.model.execution.ChangeState;
import me.util.flowable.model.execution.Execution;
import me.util.flowable.model.execution.SignalEvent;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: luhao
 * @create: 2019-7-2 18:22
 */
public interface ExecutionAPI {

    @POST("query/executions")
    Paging<Execution> queryExcutions(@Body ExecutionQuery query);

    @GET("runtime/executions")
    Paging<Execution> getExecutions();

    @GET("runtime/executions")
    Paging<Execution> getExecutions(@QueryMap Map<String, Object> params);

    @PUT("runtime/executions")
    Void putExecution(@Body SignalEvent signalEvent);

    @GET("runtime/executions/{executionId}")
    Execution getExecution(@Path("executionId") String executionId);

    @PUT("runtime/executions/{executionId}")
    Void putExecution(@Path("executionId") String executionId, @Body SignalEvent signalEvent);

    @GET("runtime/executions/{executionId}/activities")
    List<String> getExecutionActivities(@Path("executionId") String executionId);

    @POST("runtime/executions/{executionId}/change-state")
    Void changeExectionState(@Path("executionId") String executionId, @Body ChangeState changeState);

    @DELETE("runtime/executions/{executionId}/variables")
    Void deleteAllVariablesById(@Path("executionId") String executionId);

    @GET("runtime/executions/{executionId}/variables")
    List<Execution> getVariablesById(@Path("executionId") String executionId, @Query("scope") String scope);

    @GET("runtime/executions/{executionId}/variables")
    List<Execution> getVariablesById(@Path("executionId") String executionId);

    @POST("runtime/executions/{executionId}/variables")
    Void createVaribles(@Path("executionId") String executionId, @Body List<RestVariable> variables);

    @PUT("runtime/executions/{executionId}/variables")
    Void updateVaribles(@Path("executionId") String executionId, @Body List<RestVariable> variables);

    @PUT("runtime/executions/{executionId}/variables/{variableName}")
    Void deleteVarible(@Path("executionId") String executionId, @Path("variableName") String variableName);

    @GET("runtime/executions/{executionId}/variables/{variableName}")
    RestVariable getVarible(@Path("executionId") String executionId, @Path("variableName") String variableName);

    @PUT("runtime/executions/{executionId}/variables/{variableName}")
    Void updateVarible(@Path("executionId") String executionId, @Path("variableName") String variableName, @Body RestVariable variable);

    @PUT("runtime/executions/{executionId}/variables/{variableName}/data")
    Void getBinaryData(@Path("executionId") String executionId, @Path("variableName") String variableName);
}

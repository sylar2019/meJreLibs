package me.util.flowable.api;

import me.util.flowable.model.Paging;
import me.util.flowable.model.deployment.Deployment;
import me.util.flowable.model.deployment.DeploymentResource;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: luhao
 * @create: 2019-7-2 18:22
 */
public interface DeploymentAPI {

    @GET("repository/deployments")
    Paging<Deployment> getDeployments(@QueryMap Map<String, Object> params);

    @GET("repository/deployments")
    Paging<Deployment> getDeployments();

    @DELETE("repository/deployments/{id}")
    String deleteDeployment(@Path("id") String id);

    @GET("repository/deployments/{id}")
    Deployment getDeployment(@Path("id") String id);

    @GET("repository/deployments/{deploymentId}/resources")
    List<DeploymentResource> getResourcesById(@Path("deploymentId") String deploymentId);

    @GET("repository/deployments/{deploymentId}/resourcedata/{resourceName}")
    String getResourceContentById(@Path("deploymentId") String deploymentID, @Path("resourceName") String resourceName);

    @GET("repository/deployments/{deploymentId}/resources/{resourceId}")
    DeploymentResource getResourceWildcard(@Path("deploymentId") String deploymentId, @Path("resourceId") String resourceId);
}

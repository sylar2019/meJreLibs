package me.java.library.component.flowable.api;

import me.java.library.component.flowable.model.Paging;
import me.java.library.component.flowable.model.users.User;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

import java.util.Map;

/**
 * @description:
 * @author: luhao
 * @create: 2019-7-2 18:24
 */
public interface UserAPI {

    @GET("identity/users")
    Paging<User> getUsers(@QueryMap Map<String, Object> params);

    @GET("identity/users")
    Paging<User> getUsers();

    @DELETE("identity/users/{id}")
    String deleteUsers(@Path("id") String id);

    @GET("identity/users/{id}")
    User getUser(@Path("id") String id);
}

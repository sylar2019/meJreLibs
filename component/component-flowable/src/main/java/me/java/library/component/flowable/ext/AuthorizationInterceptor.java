package me.java.library.component.flowable.ext;

import me.java.library.component.flowable.Version;
import me.java.library.component.flowable.utils.Base64;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @description:
 * @author: luhao
 * @create: 2019-7-2 18:22
 */
public class AuthorizationInterceptor implements Interceptor {

    private static final String AUTH_HEADER = "Authorization";

    private String username;

    private String password;

    public AuthorizationInterceptor(String username, String password) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("username should not be null or empty");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("password should not be null or empty");
        }
        this.username = username;
        this.password = password;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String credentials = username + ":" + password;
        String auth = "Basic " + Base64.encodeBytes(credentials.getBytes());
        Request newRequest = chain.request().newBuilder().addHeader(AUTH_HEADER, auth)
                .removeHeader("User-Agent").addHeader("User-Agent", getUSerAgent()).build();
        return chain.proceed(newRequest);
    }

    private String getUSerAgent() {
        return "Toucan-Flowable-Client/" + Version.getVersion();
    }
}

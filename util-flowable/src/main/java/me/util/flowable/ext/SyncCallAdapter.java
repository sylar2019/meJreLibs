package me.util.flowable.ext;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.HttpException;
import retrofit2.Response;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @description:
 * @author: luhao
 * @create: 2019-7-2 18:22
 */
public class SyncCallAdapter<T> implements CallAdapter<T, T> {

    private final Type responseType;

    SyncCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return this.responseType;
    }

    @Override
    public T adapt(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            }
            throw new HttpException(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new RuntimeException();
    }
}

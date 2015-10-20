package com.caine.allan.networkarchitecture.web;

import com.caine.allan.networkarchitecture.exceptions.UnauthorizedException;

import java.net.HttpURLConnection;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by allancaine on 2015-10-20.
 */
public class RetrofitErrorHandler implements ErrorHandler{

    @Override
    public Throwable handleError(RetrofitError cause) {
        Response response = cause.getResponse();
        if(response != null && response.getStatus() == HttpURLConnection.HTTP_UNAUTHORIZED){
            return new UnauthorizedException(cause);
        }
        return cause;
    }
}

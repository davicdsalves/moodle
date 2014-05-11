package br.com.unirio.moodle.client;

import br.com.unirio.moodle.Constants;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by davi.alves on 10/05/2014.
 */
public interface MoodleService {

    @FormUrlEncoded
    @POST("/" + Constants.LOGIN_PARAM + "/" + Constants.INDEX_NAME)
    Response login(@Field(Constants.EMAIL_NAME) String email, @Field(Constants.PASSWORD_NAME) String password);

    @GET("/" + Constants.LOGIN_PARAM + "/" + Constants.INDEX_NAME)
    Response accessPage();
}

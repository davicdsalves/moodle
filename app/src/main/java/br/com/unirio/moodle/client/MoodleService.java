package br.com.unirio.moodle.client;

import br.com.unirio.moodle.Constants;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by davi.alves on 10/05/2014.
 */
public interface MoodleService {

    @FormUrlEncoded
    @POST(Constants.DIVISOR + Constants.LOGIN_PARAM + Constants.DIVISOR + Constants.INDEX_NAME)
    Response login(@Field(Constants.EMAIL_NAME) String email, @Field(Constants.PASSWORD_NAME) String password);

    @GET(Constants.DIVISOR + Constants.COURSE_PARAM + Constants.DIVISOR + Constants.VIEW_NAME)
    Response view(@Query(Constants.ID_NAME) Long id);

    @GET(Constants.DIVISOR + Constants.LOGIN_PARAM + Constants.DIVISOR + Constants.INDEX_NAME)
    Response accessPage();

    @GET(Constants.DIVISOR + Constants.MOD_PARAM + Constants.DIVISOR + Constants.RESOURCE_PARAM + Constants.DIVISOR + Constants.VIEW_NAME)
    Response getFile(@Query(Constants.ID_NAME) Long id);

    @GET("/file.php/{id}/{name}")
    Response download(@Path("id") Long id, @Path("name") String name);

    @GET("/file.php/{id}/{folder}/{name}")
    Response download(@Path("id") Long id, @Path("folder") String folder, @Path("name") String name);

}

package br.com.unirio.moodle;

/**
 * Created by davi.alves on 17/04/2014.
 */
public class Constants {

    public static final String EMPTY = "";

    public static final String LOGIN_PARAM = "login";
    public static final String INDEX_NAME = "index.php";

    public static final String EMAIL_NAME = "username";
    public static final String PASSWORD_NAME = "password";

    public static final String COURSES_KEY = "courses";

    public static final String API_URL = "http://uniriodb2.uniriotec.br/";

    public static final String LOGIN_URL = "http://uniriodb2.uniriotec.br/" + LOGIN_PARAM + "/" + INDEX_NAME;

    public static final String COURSES_XPATH = "//*[@id='middle-column']/div/ul";

    public static final String COURSE_NAME_XPATH = "//*[@class='name']/a/text()[1]";

    public static final String TEACHER_NAME_XPATH = "//*[@class='teachers']/li[1]/a/text()[1]";

    public static final String COURSE_URL_XPATH = "//*[@class='teachers']/li[1]/a/@href/text()[1]";
    //*[@id="middle-column"]/div/ul/li[1]/div/div[1]/div/a

    public static final String RESOURCE_URL_XPATH = "//*[@class='activity resource']/li[1]/a/   //*[@class='teachers']/li[1]/a/@href/text()[1]";  // //activity resource

}

package br.com.unirio.moodle;

/**
 * Created by davi.alves on 17/04/2014.
 */
public class Constants {

    public static final String EMPTY = "";

    public static final String DIVISOR = "/";

    public static final String LOGIN_PARAM = "login";
    public static final String INDEX_NAME = "index.php";
    public static final String EMAIL_NAME = "username";
    public static final String PASSWORD_NAME = "password";

    public static final String USER_PARAM = "user";
    public static final String VIEW_NAME = "view.php";
    public static final String ID_NAME = "username";

    public static final String COURSES_KEY = "courses";
    public static final String COURSE_NAME_KEY = "courseName";
    public static final String COURSE_URL_KEY = "courseUrl";

    public static final String API_URL = "http://uniriodb2.uniriotec.br/";

    public static final String LOGIN_URL = "http://uniriodb2.uniriotec.br/" + LOGIN_PARAM + "/" + INDEX_NAME;

    public static final String COURSES_XPATH = "//*[@id='middle-column']/div/ul";

    public static final String COURSE_NAME_XPATH = "//*[@class='name']/a/text()[1]";

    public static final String TEACHER_NAME_XPATH = "//*[@class='teachers']/li[1]/a/text()[1]";

    public static final String COURSE_URL_XPATH = "//*[@class='name']/a/@href/text()[1]";

    public static final String RESOURCE_NAME_XPATH = "//*[@class='activity resource']/a/span[1]/text()[1]";

    public static final String RESOURCE_TYPE_XPATH = "/*[@class='activity resource']/a/img/@src/text()[1]";

    public static final String RESOURCE_URL_XPATH = "//*[@class='activity resource']/a/@href/text()[1]";

    //*[@id="middle-column"]/div/ul/li[1]/div/div[1]/div/a
}

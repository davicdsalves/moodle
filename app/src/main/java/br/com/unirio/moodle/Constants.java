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

    public static final String COURSE_PARAM = "course";
    public static final String VIEW_NAME = "view.php";
    public static final String ID_NAME = "id";

    public static final String MOD_PARAM = "mod";
    public static final String RESOURCE_PARAM = "resource";

    public static final String COURSES_KEY = "courses";
    public static final String COURSE_NAME_KEY = "courseName";
    public static final String COURSE_URL_KEY = "courseUrl";

    public static final String API_URL = "http://uniriodb2.uniriotec.br/";
    public static final String ATT_URL = "http://uniriodb2.uniriotec.br/pix/f/";
    public static final String GIF = ".gif";

    public static final String COURSES_XPATH = "//*[@id='middle-column']/div/ul";

    public static final String COURSE_NAME_XPATH = "//*[@class='name']/a/text()[1]";

    public static final String TEACHER_NAME_XPATH = "//*[@class='teachers']/li[1]/a/text()[1]";

    public static final String COURSE_URL_XPATH = "//*[@class='name']/a/@href/text()[1]";

    public static final String RESOURCES_XPATH = "//*[@class='activity resource']";

    public static final String RESOURCE_NAME_XPATH = "//span";

    public static final String RESOURCE_TYPE_XPATH = "//img/@src/text()[1]";

    public static final String RESOURCE_URL_XPATH = "//a/@href/text()[1]";

    public static final String ZIP_URL_XPATH = "//*[@id='content']/div/a/@href/text()[1]";

    public static final String TEST_DB2_COOKIE = "MoodleSessionTestuniriodb2";
    public static final String SESSION_DB2_COOKIE = "MoodleSessionuniriodb2";
    public static final String UNIRIO_DB2_COOKIE = "MOODLEID1_uniriodb2";

    //MoodleSessionTestuniriodb2=cZny5EYkFU; MoodleSessionuniriodb2=m7m9i5b1gnchmp3ntnrkhi953m5asqcrv9kgrd9ctr5vjd0vb2f1; MOODLEID1_uniriodb2=5%253B%251E%25A7%25A7gn%25B0%2500%25F7%25FD

}

package br.com.unirio.moodle;

/**
 * Created by davi.alves on 12/05/2014.
 */
public class StringUtil {

    public static Long getIdFromUrl(String url) {
        int idIndex = url.lastIndexOf(Constants.ID_NAME);
        return Long.valueOf(url.substring(url.indexOf("=", idIndex) + 1));
    }

    public static String getFileName(String url) {
        int lastSlash = url.lastIndexOf('/');
        return url.substring(lastSlash + 1);
    }

    //http://uniriodb2.uniriotec.br/file.php/18/Exemplo_PM.zip
    public static String getDownloadLink(String url) {
        return url.replace(Constants.API_URL + "file.php/", Constants.EMPTY);
    }
}

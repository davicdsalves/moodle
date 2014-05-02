package br.com.unirio.moodle.client;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import br.com.unirio.moodle.Constants;
import br.com.unirio.moodle.util.Logger;

/**
 * Created by davi.alves on 17/04/2014.
 */
public class MoodleClient {

    public MoodleClient() {
    }

    public void authenticate(String email, String password) {
        String[] pathParams = {Constants.LOGIN_PARAM, Constants.INDEX_NAME};
        String[] formKeys = {Constants.USERNAME_NAME, Constants.PASSWORD_NAME, Constants.TEST_COOKIES};
        String[] formValues = {email, password, String.valueOf(1)};

        HttpConnector<String> connector = new HttpConnector<String>(String.class);
        MultiValueMap<String, Object> body = createMultiValueMap(formKeys, formValues);
        String response = connector.path(pathParams).form().body(body).post();

        Logger.i(response);

    }

    /**
     * @param keys chaves
     * @param values valores
     * @return lista de nameValuePair
     */
    private List<NameValuePair> createValuePairs(String[] keys, String[] values) {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        if (keys != null && values != null && keys.length == values.length) {
            for (int i = 0; i < keys.length; i++) {
                list.add(new BasicNameValuePair(keys[i], values[i]));
            }
        }
        return list;
    }

    /**
     * @param keys chaves
     * @param values valores
     * @return mapa com chave e valor
     */
    private MultiValueMap<String, Object> createMultiValueMap(String[] keys, Object[] values) {
        int capacity = (keys != null ? keys.length : 0);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>(capacity);
        if (keys != null && values != null) {
            for (int i = 0; i < keys.length; i++) {
                map.add(keys[i], values[i]);
            }
        }
        return map;
    }
}

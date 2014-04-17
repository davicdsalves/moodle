package br.com.unirio.moodle.client;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * Created by davi.alves on 17/04/2014.
 */
public class HttpConnector<T> {

    public static final String API_URL = "http://uniriodb2.uniriotec.br";

    private UriComponentsBuilder builder;

    private RestTemplate template;

    private Class<T> generic;

    private HttpEntity<?> requestEntity;

    private HttpHeaders headers;

    /**
     * @param generic
     */
    protected HttpConnector(Class<T> generic) {
        this.builder = UriComponentsBuilder.fromUriString(API_URL);
        this.template = new RestTemplate();
        this.template.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        this.generic = generic;
        this.template.getMessageConverters().add(new GsonHttpMessageConverter());
        this.headers = new HttpHeaders();
    }

    /**
     * @param pathSegments
     * @return
     */
    protected HttpConnector<T> path(String[] pathSegments) {
        this.builder = builder.pathSegment(pathSegments);
        return this;
    }

    /**
     * @param headerParams
     * @return
     */
    protected HttpConnector<T> header(List<NameValuePair> headerParams) {
        for (NameValuePair param : headerParams) {
            headers.add(param.getName(), param.getValue());
        }
        return this;
    }

    /**
     * @param queryParams
     * @return
     */
    protected HttpConnector<T> query(List<NameValuePair> queryParams) {
        for (NameValuePair param : queryParams) {
            this.builder = builder.queryParam(param.getName(), param.getValue());
        }
        return this;
    }

    /**
     * @param body
     * @return
     */
    protected HttpConnector<T> body(MultiValueMap<String, ? extends Object> body) {
        this.requestEntity = new HttpEntity<Object>(body, headers);
        return this;
    }

    /**
     * @param body
     * @return
     */
    protected HttpConnector<T> body(Object body) {
        this.requestEntity = new HttpEntity<Object>(body, headers);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return this;
    }

    /**
     * @return
     */
    protected HttpConnector<T> form() {
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return addFormConverter();
    }

    /**
     * @return
     */
    protected HttpConnector<T> multipart() {
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        return addFormConverter();
    }

    /**
     * @return
     */
    private HttpConnector<T> addFormConverter() {
        this.template.getMessageConverters().add(new FormHttpMessageConverter());
        return this;
    }

    /**
     * @return
     */
    protected T post() {
        return sendRequest(HttpMethod.POST);
    }

    /**
     * @return
     */
    protected T put() {
        return sendRequest(HttpMethod.PUT);
    }

    /**
     * @return
     */
    protected T get() {
        return sendRequest(HttpMethod.GET);
    }

    /**
     * @param method
     * @return
     */
    private T sendRequest(HttpMethod method) {
        HttpStatus status = null;
        ResponseEntity<T> response;
        try {
            response = template.exchange(toUri(), method, getRequestEntity(), generic);
            status = response.getStatusCode();
            if (HttpStatus.OK.equals(status)) {
                return response.getBody();
            }
        } catch (Exception e) {
            Log.e(getClass().toString(), String.format("Error %s request, status: %s", method.toString(), status), e);
        }
        return null;
    }

    /**
     * @return
     */
    private HttpEntity<?> getRequestEntity() {
        if (this.requestEntity == null) {
            this.requestEntity = new HttpEntity<Object>(headers);
        }
        return requestEntity;
    }

    /**
     * @return
     */
    private URI toUri() {
        return this.builder.build().toUri();
    }

    /**
     * @return
     */
    public HttpHeaders getHeaders() {
        return headers;
    }
}

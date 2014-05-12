package br.com.unirio.moodle.model;

/**
 * Created by Carlos.Velez on 11/05/2014.
 */
public class Resource {

    private String name;

    private String url;

    private String type;

    public Resource() {
    }

    public Resource(String name, String url, String type) {
        this.name = name;
        this.url = url;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

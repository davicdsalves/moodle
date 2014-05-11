package br.com.unirio.moodle.model;

/**
 * Created by Carlos.Velez on 11/05/2014.
 */
public class Resource {

    private String name;

    private String url;

    private FileType type;

    public Resource() {
    }

    public Resource(String name) {
        this.name = name;
    }

    public Resource(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public Resource(String name, String url, FileType type) {
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

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }
}

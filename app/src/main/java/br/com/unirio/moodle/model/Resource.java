package br.com.unirio.moodle.model;

/**
 * Created by Everyone on 11/05/2014.
 */
public class Resource {

    private String name;

    public Resource() {
    }

    public Resource(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

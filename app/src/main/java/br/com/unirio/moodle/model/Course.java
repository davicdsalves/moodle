package br.com.unirio.moodle.model;

import java.io.Serializable;

/**
 * Created by davi.alves on 11/05/2014.
 */
public class Course implements Serializable {

    private String name;
    private String teacher;
    private String url;

    public Course() {
    }

    public Course(String name, String teacher, String url) {
        this.name = name;
        this.teacher = teacher;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

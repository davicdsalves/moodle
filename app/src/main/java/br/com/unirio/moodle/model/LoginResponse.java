package br.com.unirio.moodle.model;

import java.util.List;

/**
 * Created by davi.alves on 11/05/2014.
 */
public class LoginResponse {

    private boolean logged;

    private List<Course> courses;

    public LoginResponse(boolean logged, List<Course> courses) {
        this.logged = logged;
        this.courses = courses;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}

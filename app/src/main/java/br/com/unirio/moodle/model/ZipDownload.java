package br.com.unirio.moodle.model;

/**
 * Created by davi.alves on 12/05/2014.
 */
public class ZipDownload {

    private String folder;
    private Long id;
    private String name;

    public ZipDownload() {
    }

    public ZipDownload(String folder, Long id, String name) {
        this.folder = folder;
        this.id = id;
        this.name = name;
    }

    public ZipDownload(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

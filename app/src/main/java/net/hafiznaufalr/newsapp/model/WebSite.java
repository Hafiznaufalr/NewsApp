package net.hafiznaufalr.newsapp.model;

import java.util.List;

import javax.xml.transform.Source;

public class WebSite {
    private String status;
    private List<net.hafiznaufalr.newsapp.model.Source> sources;

    public WebSite() {
    }

    public WebSite(String status, List<net.hafiznaufalr.newsapp.model.Source> sources) {
        this.status = status;
        this.sources = sources;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<net.hafiznaufalr.newsapp.model.Source> getSources() {
        return sources;
    }

    public void setSources(List<net.hafiznaufalr.newsapp.model.Source> sources) {
        this.sources = sources;
    }
}
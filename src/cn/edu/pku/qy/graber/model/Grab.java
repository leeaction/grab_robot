package cn.edu.pku.qy.graber.model;

import cn.edu.pku.qy.graber.core.Grabber;
import cn.edu.pku.qy.graber.core.Persistence;
import cn.edu.pku.qy.graber.core.UrlParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limeng6 on 2017/2/1.
 */
public class Grab {

    private String name;

    private Map<String,UrlParser> urlMap = new HashMap<>();

    private Grabber parserClazz;

    private Persistence persistenceClazz;

    private String persistenceName;

    private String userName;

    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String,UrlParser> getUrlMap() {
        return urlMap;
    }

    public Grabber getParserClazz() {
        return parserClazz;
    }

    public void putUrl(String url,UrlParser parser) {
        this.urlMap.put(url,parser);
    }

    public void putUrls(Map<String,UrlParser> urlMap) {
        this.urlMap.putAll(urlMap);
    }

    public void setParserClazz(Grabber parserClazz) {
        this.parserClazz = parserClazz;
    }

    public Persistence getPersistenceClazz() {
        return persistenceClazz;
    }

    public void setPersistenceClazz(Persistence persistenceClazz) {
        this.persistenceClazz = persistenceClazz;
    }

    public String getPersistenceName() {
        return persistenceName;
    }

    public void setPersistenceName(String persistenceName) {
        this.persistenceName = persistenceName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

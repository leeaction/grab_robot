package cn.edu.pku.qy.graber.utils;

import cn.edu.pku.qy.graber.config.CommonConfigManager;
import cn.edu.pku.qy.graber.core.Grabber;
import cn.edu.pku.qy.graber.core.Persistence;
import cn.edu.pku.qy.graber.core.UrlParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by limeng6 on 2017/2/2.
 */
public class Utils {

    public static Grabber createGrabber(String className){
        Class<?> clazz = loadClass(className);
        try {
            Grabber grabber = (Grabber)clazz.newInstance();
            return grabber;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static UrlParser createUrlParser(String className){
        Class<?> clazz = loadClass(className);
        try {
            UrlParser urlParser = (UrlParser)clazz.newInstance();
            return urlParser;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Persistence createPersistence(String className){
        Class<?> clazz = loadClass(className);
        try {
            Persistence persistence = (Persistence)clazz.newInstance();
            return persistence;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> loadClass(String clazz){
        try {
            Class<?> parseClazz = Class.forName(clazz);
            return parseClazz;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String,String> getCookies(WebDriver driver){
        Set<Cookie> cookies = driver.manage().getCookies();
        Map<String, String> newCookies = new HashMap<>();

        Iterator<Cookie> iterator = cookies.iterator();
        while(iterator.hasNext()){
            Cookie cookie = iterator.next();
            String name = cookie.getName();
            String value = cookie.getValue();
            newCookies.put(name,value);
        }
        return newCookies;
    }

    public static int safeConvertInt(String str,int defaultValue){
        try{
            return Integer.parseInt(str);
        }catch(Exception e){
            return defaultValue;
        }
    }

    public static Document createJsoupDocument(String url,WebDriver driver) throws IOException {
        Map<String, String> newCookies = Utils.getCookies(driver);
        Document document = Jsoup.connect(url).cookies(newCookies)
                .timeout(CommonConfigManager.getInstance().getRequestTimeout())
                .get();
        return document;
    }
}

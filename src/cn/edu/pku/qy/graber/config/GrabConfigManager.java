package cn.edu.pku.qy.graber.config;

import cn.edu.pku.qy.graber.core.Grabber;
import cn.edu.pku.qy.graber.core.Persistence;
import cn.edu.pku.qy.graber.core.UrlParser;
import cn.edu.pku.qy.graber.model.Grab;
import cn.edu.pku.qy.graber.utils.Utils;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by limeng6 on 2017/2/1.
 */
public class GrabConfigManager {

    private static final String GRAB_CONFIG_PATH = "config/";

    private static final String PARSER_PACKAGE = "cn.edu.pku.qy.graber.biz.";

    private static GrabConfigManager mInstance;

    private GrabConfigManager(){

    }

    public static GrabConfigManager getInstance(){
        if(mInstance == null){
            mInstance = new GrabConfigManager();
        }
        return mInstance;
    }

    public List<Grab> parse(){

        List<Grab> grabs = new ArrayList<>();

        String grabConfigName = CommonConfigManager.getInstance().getGrabConfigName();
        DocumentBuilder db = null;
        try {
            db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        File file = new File(GRAB_CONFIG_PATH + grabConfigName);
        Document document = null;
        try {
            document = db.parse(file);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element root = document.getDocumentElement();
        NodeList noteList = root.getElementsByTagName("grab");

        for(int i=0;i<noteList.getLength();i++){

            Grab grab = new Grab();

            Node note =  noteList.item(i);

            int type = note.getNodeType();
            if(type != Node.ELEMENT_NODE){
                continue;
            }

            Element element = (Element)note;

            String name = element.getAttribute("name");
            grab.setName(name);

            String parser = element.getAttribute("parser");
            Grabber grabber = Utils.createGrabber(PARSER_PACKAGE + parser);
            grab.setParserClazz(grabber);

            String persistenceType = element.getAttribute("persistence_type");
            Persistence persistence = Utils.createPersistence(PARSER_PACKAGE + persistenceType);
            grab.setPersistenceClazz(persistence);

            String persistenceName = element.getAttribute("persistence_name");
            grab.setPersistenceName(persistenceName);

            String username = element.getAttribute("username");
            String password = element.getAttribute("password");
            grab.setUserName(username);
            grab.setPassword(password);

            Map<String,UrlParser> urlMap = parseUrls(element);
            grab.putUrls(urlMap);

            grabs.add(grab);
        }

        return grabs;
    }

    private Map<String,UrlParser> parseUrls(Element element){
        Map<String,UrlParser> urlMap = new HashMap<>();
        NodeList nodes = element.getElementsByTagName("urls");
        for(int i=0;i<nodes.getLength();i++){
            Node note =  nodes.item(i);
            int type = note.getNodeType();
            if(type != Node.ELEMENT_NODE){
                continue;
            }

            Element urlsElement = (Element)note;
            Map<String,UrlParser> urls = parseUrl(urlsElement);
            urlMap.putAll(urls);
        }
        return urlMap;
    }

    private Map<String,UrlParser> parseUrl(Element element){
        Map<String,UrlParser> urlMap = new HashMap<>();
        NodeList nodes = element.getElementsByTagName("url");
        for(int i=0;i<nodes.getLength();i++){
            Node node =  nodes.item(i);
            int type = node.getNodeType();
            if(type != Node.ELEMENT_NODE){
                continue;
            }
            Element urlElement = (Element)node;
            String parser = urlElement.getAttribute("parser");
            UrlParser urlParser = Utils.createUrlParser(PARSER_PACKAGE + parser);
            String url = urlElement.getFirstChild().getNodeValue();
            urlMap.put(url,urlParser);
        }
        return urlMap;
    }
}

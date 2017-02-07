package cn.edu.pku.qy.graber.core;

import cn.edu.pku.qy.graber.model.GrabInfo;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;

/**
 * Created by limeng6 on 2017/2/2.
 */
public interface UrlParser {

    public void parse(WebDriver mWebDriver,String url,Persistence persistence,String persistenceName);

}

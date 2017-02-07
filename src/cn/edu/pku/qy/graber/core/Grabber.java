package cn.edu.pku.qy.graber.core;

import cn.edu.pku.qy.graber.model.Grab;
import cn.edu.pku.qy.graber.model.GrabInfo;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;

/**
 * Created by limeng6 on 2017/2/1.
 */
public interface Grabber {

    public void setUp();

    public WebDriver getWebDriver();

    public void grab(Grab grab);
}

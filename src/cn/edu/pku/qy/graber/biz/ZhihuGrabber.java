package cn.edu.pku.qy.graber.biz;

import cn.edu.pku.qy.graber.core.ConsoleManager;
import cn.edu.pku.qy.graber.core.Grabber;
import cn.edu.pku.qy.graber.core.Persistence;
import cn.edu.pku.qy.graber.core.UrlParser;
import cn.edu.pku.qy.graber.model.Grab;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;

/**
 * Created by limeng6 on 2017/2/2.
 */
public class ZhihuGrabber implements Grabber {

    private WebDriver mWebDriver;

    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver");
        mWebDriver = new ChromeDriver();
    }

    public WebDriver getWebDriver(){
        return mWebDriver;
    }

    @Override
    public void grab(Grab grab) {

        ConsoleManager.getInstance().msg("开始登陆...");

        login(grab.getUserName(),grab.getPassword());

        ConsoleManager.getInstance().msg("登陆成功...");

        Persistence persistence = grab.getPersistenceClazz();
        String persistenceName = grab.getPersistenceName();

        Map<String,UrlParser> urlMap = grab.getUrlMap();
        Set<Map.Entry<String,UrlParser>> entrySet = urlMap.entrySet();
        Iterator<Map.Entry<String,UrlParser>> iterator = entrySet.iterator();

        while(iterator.hasNext()){
            Map.Entry<String,UrlParser> entry = iterator.next();
            String url = entry.getKey();
            UrlParser parser = entry.getValue();
            parse(url,parser,persistence,persistenceName);
        }
    }

    public void login(String userName,String password){
        mWebDriver.get("https://www.zhihu.com/#signin");

        await();

        WebElement accountElement = mWebDriver.findElement(By.name("account"));
        WebElement passwordElement = mWebDriver.findElement(By.name("password"));
        WebElement loginElement = mWebDriver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/form/div[2]/button"));

        accountElement.sendKeys(userName);
        passwordElement.sendKeys(password);
        loginElement.click();

        WebDriverWait wait = new WebDriverWait(mWebDriver, 20);
        wait.until(new ExpectedCondition<Boolean>(){

            @Override
            public Boolean apply(WebDriver webDriver) {
                WebElement accountElement = mWebDriver.findElement(By.xpath("//*[@id=\":0\"]/span[1]"));
                return accountElement.isDisplayed();
            }
        });
    }

    private void parse(String url,UrlParser parser,Persistence persistence,String persistenceName){
        ConsoleManager.getInstance().msg("开始抓取url:"+url+"...");
        parser.parse(mWebDriver,url,persistence,persistenceName);
        ConsoleManager.getInstance().msg("抓取url:"+url+"完成让");
    }

    private void await(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

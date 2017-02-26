package cn.edu.pku.qy.graber.biz;

import cn.edu.pku.qy.graber.config.CommonConfigManager;
import cn.edu.pku.qy.graber.core.ConsoleManager;
import cn.edu.pku.qy.graber.core.Persistence;
import cn.edu.pku.qy.graber.core.UrlParser;
import cn.edu.pku.qy.graber.model.GrabInfo;
import cn.edu.pku.qy.graber.utils.Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by limeng6 on 2017/2/2.
 */
public class ZhihuFeedParser extends ZhihuBaseUrlParser {

    private boolean isFinish;

    private int mTotalQuestionCount = 0;

    @Override
    public void parse(WebDriver driver, String url,Persistence persistence,String persistenceName) {

        driver.get(url);

        ConsoleManager.getInstance().msg("开始Feed抓取...");

        String timeoutStr = CommonConfigManager.getInstance().getConfigValue("feed_wait_timeout");
        int timeout = Integer.parseInt(timeoutStr);

        while (!isFinish) {

            WebDriverWait wait = new WebDriverWait(driver,timeout);
            wait.until(new ExpectedCondition<Boolean>() {

                @Override
                public Boolean apply(WebDriver webDriver) {

                    List<WebElement> elements = webDriver.findElements(By.className("question_link"));
                    int count = elements.size();
                    isFinish = count <= mTotalQuestionCount;
                    return !isFinish;
                }
            });

            List<WebElement> elements = driver.findElements(By.className("question_link"));
            int newTotalCount = elements.size();

            int offset = newTotalCount - mTotalQuestionCount;
            List<WebElement> currentElements = elements.subList(offset, newTotalCount);

            //解析当前问题列表
            List<GrabInfo> grabInfos = parseQuestionLink(driver, currentElements);

            //保存
            persistence.save(persistenceName,grabInfos);

            mTotalQuestionCount = newTotalCount;

            ConsoleManager.getInstance().msg("抓取条数"+mTotalQuestionCount+" ...OK");

            scorll2Bottom(driver);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void scorll2Bottom(WebDriver driver) {
        JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
        JSExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    private List<GrabInfo> parseQuestionLink(WebDriver driver, List<WebElement> currentElements) {
        List<GrabInfo> grabInfos = new ArrayList<>();
        for (WebElement element : currentElements) {
            String url = element.getAttribute("href");
            GrabInfo info = parseAnswer(driver,url);
            System.out.println("info:"+info.toString());
            grabInfos.add(info);
        }
        return grabInfos;
    }

}

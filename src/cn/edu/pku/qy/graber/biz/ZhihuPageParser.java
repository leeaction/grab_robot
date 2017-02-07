package cn.edu.pku.qy.graber.biz;

import cn.edu.pku.qy.graber.core.ConsoleManager;
import cn.edu.pku.qy.graber.core.Persistence;
import cn.edu.pku.qy.graber.model.GrabInfo;
import cn.edu.pku.qy.graber.utils.Utils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by limeng6 on 2017/2/2.
 */
public class ZhihuPageParser extends ZhihuBaseUrlParser {

    @Override
    public void parse(WebDriver driver, String url,Persistence persistence,String persistenceName) {

        try {

            int pageNum = 1;
            int pageIndex = 1;

            while (pageIndex <= pageNum) {

                String targetUrl = url + "?page=" + pageIndex;
                Document document = Utils.createJsoupDocument(targetUrl,driver);

                Element body = document.body();

                if (pageNum <= 1) {
                    Elements pagerElements = body.getElementsByClass("zm-invite-pager");
                    int pagerElementsCount = pagerElements.size();
                    if (pagerElementsCount > 0) {
                        Element pagerElement = pagerElements.get(0);
                        Elements pagerChildren = pagerElement.children();
                        int pagerElementCount = pagerChildren.size();
                        Element pageNumElement = pagerChildren.get(pagerElementCount - 2);
                        String pageNumStr = pageNumElement.child(0).text();
                        pageNum = Utils.safeConvertInt(pageNumStr, 1);
                    }
                }

                ConsoleManager.getInstance().msg("抓取第" + pageIndex +"页... 共" + pageNum + "页");

                Elements elements = body.getElementsByClass("question_link");

                List<GrabInfo> grabInfos = parseQuestionLink(driver, elements);

                //保存
                persistence.save(persistenceName,grabInfos);

                ConsoleManager.getInstance().msg("第" + pageIndex +"页...OK");

                pageIndex++;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<GrabInfo> parseQuestionLink(WebDriver driver, Elements currentElements) {
        List<GrabInfo> grabInfos = new ArrayList<>();
        for (int i = 0; i < currentElements.size(); i++) {
            Element element = currentElements.get(i);
            String url = element.absUrl("href");
            GrabInfo info = parseAnswer(driver, url);
            grabInfos.add(info);
        }
        return grabInfos;
    }
}

package cn.edu.pku.qy.graber.biz;

import cn.edu.pku.qy.graber.core.UrlParser;
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
import java.util.Map;

/**
 * Created by limeng6 on 2017/2/3.
 */
public abstract class ZhihuBaseUrlParser implements UrlParser{

    protected GrabInfo parseAnswer(WebDriver driver, String url){

        GrabInfo grabInfo = new GrabInfo();

        List<String> tags = new ArrayList<>();

        try{

            Document document = Utils.createJsoupDocument(url,driver);

            Element body = document.body();

            //title
            Elements titleElements = body.getElementsByClass("QuestionHeader-title");
            int titleSize = titleElements.size();
            if(titleSize > 0 ){
                String title = titleElements.text();
                grabInfo.setTitle(title);
            }

            //tags
            Elements elements = body.getElementsByClass("QuestionHeader-topics");
            int size = elements.size();
            if(size > 0 ){
                Element element = elements.get(0);
                Elements tagElements = element.getElementsByClass("Tag-content");
                for(int i = 0;i<tagElements.size();i++){
                    Element tagElement = tagElements.get(i);
                    Element tagContent = tagElement.child(0).child(0).child(0);
                    String data = tagContent.text();
                    tags.add(data);
                }
                grabInfo.putTags(tags);
            }

            //赞数
            Elements likeElements = body.getElementsByClass("AnswerItem-extraInfo");
            int likeSize = likeElements.size();
            if(likeSize > 0 ){
                Element element = likeElements.get(0);
                Elements likeCountElements = element.getElementsByClass("Button Button--plain");
                int likeCountSize = likeCountElements.size();
                if(likeCountSize > 0){
                    Element firstLikeCountElement = likeCountElements.get(0);
                    String likeCount = firstLikeCountElement.text();
                    grabInfo.setLikeCount(likeCount);
                }
            }

            //编辑时间
            Elements modifiedTimeElements = body.getElementsByClass("answer-date-link meta-item");
            int modifiedTimeSize = modifiedTimeElements.size();
            if(modifiedTimeSize > 0 ){
                Element element = modifiedTimeElements.get(0);
                String modifiedTimeCount = element.text();
                grabInfo.setModifiedTime(modifiedTimeCount);
            }

            //回答数量
            Elements answerNumElements = body.getElementsByClass("List-headerText");
            int answerNumSize = answerNumElements.size();
            if(answerNumSize > 0 ){
                Element element = answerNumElements.get(0);
                String answerNUm = element.child(0).text();
                grabInfo.setAnswerCount(answerNUm);
            }

            //提问时间
            String logTargetUrl = url+"/log";
            Document logDocument = Utils.createJsoupDocument(logTargetUrl,driver);
            Element logBody = logDocument.body();
            Elements logElements = logBody.getElementsByClass("zm-item-meta");
            int logSize = logElements.size();
            if(logSize > 0){
                Element lastElement = logElements.get(logSize - 1);
                Elements timeElements = lastElement.getElementsByTag("time");
                Element timeElement = timeElements.get(0);
                String time = timeElement.text();
                grabInfo.setAskTime(time);
            }

            //最近回答时间
            String answererUrl = url+"?sort=created";
            Document answererDocument = Utils.createJsoupDocument(answererUrl,driver);
            Element answererBody = answererDocument.body();
            Elements answererElements = answererBody.getElementsByClass("ContentItem-time");
            int answererSize = answererElements.size();
            if(answererSize > 0){
                Element latestAnswererElement = answererElements.get(0);
                String latestAnswererTime = latestAnswererElement.text();
                grabInfo.setLatestAnswererTime(latestAnswererTime);
            }

            //最远一次回答
            Elements pagerElements = answererBody.getElementsByClass("Pagination");
            int pagerElementsCount = pagerElements.size();
            if(pagerElementsCount > 0){
                Element pagerElement = pagerElements.get(0);
                Elements pagerChildren = pagerElement.children();
                int pagerElementCount = pagerChildren.size();
                Element pageNumElement = pagerChildren.get(pagerElementCount -2);
                String pagenum = pageNumElement.child(0).text();

                String farthestAnswererUrl = url+"?sort=created&page=" + pagenum;
                answererDocument = Utils.createJsoupDocument(farthestAnswererUrl,driver);
                answererBody = answererDocument.body();
                answererElements = answererBody.getElementsByClass("ContentItem-time");
                answererSize = answererElements.size();
            }
            if(answererSize > 0){
                Element farthestAnswererElement = answererElements.get(answererSize -1);
                String farthestAnswererTime = farthestAnswererElement.text();
                grabInfo.setFarthestAnswererTime(farthestAnswererTime);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return grabInfo;
    }
}

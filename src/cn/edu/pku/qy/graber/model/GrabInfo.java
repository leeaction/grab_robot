package cn.edu.pku.qy.graber.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limeng6 on 2017/2/2.
 */
public class GrabInfo {

    private String title;

    private List<String> tags = new ArrayList<>();

    private String answerCount;

    private String latestAnswererTime;

    private String farthestAnswererTime;

    private String askTime;

    private String likeCount;

    private String modifiedTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getAnswerCount() {
        return answerCount;
    }

    public String getLatestAnswererTime() {
        return latestAnswererTime;
    }

    public String getFarthestAnswererTime() {
        return farthestAnswererTime;
    }

    public String getAskTime() {
        return askTime;
    }

    public void putTags(List<String> tags) {
        this.tags.addAll(tags);
    }

    public void putTags(String tags) {
        this.tags.add(tags);
    }

    public void setAnswerCount(String answerCount) {
        this.answerCount = answerCount;
    }

    public void setLatestAnswererTime(String latestAnswererTime) {
        this.latestAnswererTime = latestAnswererTime;
    }

    public void setFarthestAnswererTime(String farthestAnswererTime) {
        this.farthestAnswererTime = farthestAnswererTime;
    }

    public void setAskTime(String askTime) {
        this.askTime = askTime;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Override
    public String toString() {
        /*return "title:"+title+" answerCount:"+answerCount+" latestAnswererTime:"+latestAnswererTime+" farthestAnswererTime:"+farthestAnswererTime+
                " askTime:"+askTime+" tags:"+tags.toString();*/
        return title + ","+answerCount + "," + latestAnswererTime + "," + farthestAnswererTime + "," + askTime + " ,"+tagsLine(tags) + " ,"
                +likeCount +" ,"+ modifiedTime;
    }

    private String tagsLine(List<String> tags){
        StringBuilder sb = new StringBuilder();
        for(String tag : tags){
            sb.append(tag).append(" ");
        }
        return sb.toString();
    }
}

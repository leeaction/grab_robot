package cn.edu.pku.qy.graber.core;

import cn.edu.pku.qy.graber.config.GrabConfigManager;
import cn.edu.pku.qy.graber.model.Grab;
import cn.edu.pku.qy.graber.model.GrabInfo;
import cn.edu.pku.qy.graber.utils.Utils;

import java.util.List;

/**
 * Created by limeng6 on 2017/2/1.
 */
public class GraberManager {

    public void init(){
        ConsoleManager.getInstance().msg("初始化抓取管理器...");
    }

    public void start(){

        ConsoleManager.getInstance().msg("加载抓取配置...");

        List<Grab> grabs = loadGrabConfig();
        grab(grabs);
    }

    private List<Grab> loadGrabConfig(){
        return GrabConfigManager.getInstance().parse();
    }

    private void grab(List<Grab> grabs){
        for (Grab grab : grabs){
            grab(grab);
        }
    }

    private void grab(Grab grab){
        Grabber grabber = grab.getParserClazz();

        ConsoleManager.getInstance().msg("建立 " + grab.getName() + " 抓取...");

        grabber.setUp();

        ConsoleManager.getInstance().msg("开始抓取 " + grab.getName() + " ...");

        grabber.grab(grab);

        ConsoleManager.getInstance().msg(grab.getName() + " 抓取完成");
    }

}

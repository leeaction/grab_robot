package cn.edu.pku.qy.graber;

import cn.edu.pku.qy.graber.config.CommonConfigManager;
import cn.edu.pku.qy.graber.core.ConsoleManager;
import cn.edu.pku.qy.graber.core.GraberManager;

/**
 * Created by limeng6 on 2017/2/1.
 */
public class MainControl {

    public static void main(String[] args){

        ConsoleManager.getInstance().msg("主控程序启动...");

        CommonConfigManager.getInstance().init();

        GraberManager graber = new GraberManager();
        graber.init();
        graber.start();
    }

}

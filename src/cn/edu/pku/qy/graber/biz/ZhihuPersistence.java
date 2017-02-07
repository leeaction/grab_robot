package cn.edu.pku.qy.graber.biz;

import cn.edu.pku.qy.graber.config.CommonConfigManager;
import cn.edu.pku.qy.graber.core.Persistence;
import cn.edu.pku.qy.graber.model.CommonConfig;
import cn.edu.pku.qy.graber.model.GrabInfo;
import cn.edu.pku.qy.graber.utils.FileManager;

import java.util.List;
import java.util.Map;

/**
 * Created by limeng6 on 2017/2/2.
 */
public class ZhihuPersistence implements Persistence<GrabInfo>{

    @Override
    public void save(String name,List<GrabInfo> result) {

        String path = CommonConfigManager.getInstance().getPersistencePath() + name;

        for(GrabInfo info : result){
            String line = createLine(info);
            FileManager.writeFile(path,line);
        }
    }

    private String createLine(GrabInfo info){
        return info.toString();
    }
}

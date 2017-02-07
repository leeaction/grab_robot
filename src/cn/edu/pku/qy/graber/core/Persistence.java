package cn.edu.pku.qy.graber.core;

import cn.edu.pku.qy.graber.model.GrabInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by limeng6 on 2017/2/1.
 */
public interface Persistence<T> {

    public void save(String name,List<T> result);
}

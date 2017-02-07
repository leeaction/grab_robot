package cn.edu.pku.qy.graber.config;

import cn.edu.pku.qy.graber.core.ConsoleManager;

import java.io.*;
import java.util.Properties;

/**
 * Created by limeng6 on 2017/2/1.
 */
public class CommonConfigManager {

    private static final String PATH = "config/config.properties";

    private Properties mProperties = new Properties();

    private static CommonConfigManager mInstance;

    private String mGrabConfigName;

    private String mPersistencePath;

    private int mRequestTimeout;

    private CommonConfigManager(){
    }

    public static CommonConfigManager getInstance(){
        if(mInstance == null){
            mInstance = new CommonConfigManager();
        }
        return mInstance;
    }

    public void init(){

        ConsoleManager.getInstance().msg("加载全局配置信息...");

        try {
            FileInputStream fis = new FileInputStream(PATH);
            InputStream is = new BufferedInputStream(fis);
            mProperties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mGrabConfigName = mProperties.getProperty("grab_config_name");
        mPersistencePath = mProperties.getProperty("persistence_path");
        String mRequestTimeoutStr = mProperties.getProperty("request_timeout");
        mRequestTimeout = Integer.parseInt(mRequestTimeoutStr);
    }

    public String getGrabConfigName(){
        return mGrabConfigName;
    }

    public String getPersistencePath(){
        return mPersistencePath;
    }

    public int getRequestTimeout(){
        return mRequestTimeout;
    }

    public String getConfigValue(String key){
        return mProperties.getProperty(key);
    }
}

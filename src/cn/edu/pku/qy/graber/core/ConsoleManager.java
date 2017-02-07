package cn.edu.pku.qy.graber.core;

/**
 * Created by limeng6 on 2017/2/4.
 */
public class ConsoleManager {

    private static ConsoleManager mInstance;

    private ConsoleManager(){

    }

    public static ConsoleManager getInstance(){
        if(mInstance == null){
            mInstance = new ConsoleManager();
        }
        return mInstance;
    }

    public void msg(String msg){
        System.out.println(msg);
    }
}

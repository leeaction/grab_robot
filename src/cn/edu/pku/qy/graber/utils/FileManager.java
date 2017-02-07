package cn.edu.pku.qy.graber.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by limeng6 on 2017/2/4.
 */
public class FileManager {

    private static final String LINE = "\r\n";

    public static void writeFile(String path, String line) {
        File file = null;
        FileWriter fileWriter = null;

        if (path == null || line == null) {
            return;
        }

        try {
            file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            fileWriter = new FileWriter(file, true);
            StringBuilder content = new StringBuilder();
            content.append(line).append(LINE);
            fileWriter.write(content.toString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

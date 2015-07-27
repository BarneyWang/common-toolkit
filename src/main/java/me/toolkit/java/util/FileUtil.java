package me.toolkit.java.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdi5 on 2015/7/9.
 */
public class FileUtil {

    /**
     * 追加一行
     *
     * @param filePath
     * @param content
     * @throws java.io.IOException
     */
    public static void writeLine(String filePath, String content) throws IOException {
        FileWriter fw = new FileWriter(filePath, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(content);
        bw.newLine();
        bw.flush();
        bw.close();
        fw.close();
    }

    /**
     * 按行读取
     * @param filePath
     * @return
     * @throws java.io.IOException
     */
    public static List<String> readFileByLine(String filePath) throws IOException {
        List<String> temp = new ArrayList<String>();
        FileReader reader = new FileReader(filePath);
        BufferedReader br = new BufferedReader(reader);
        String str = null;
        if ((str = br.readLine()) != null) {
            temp.add(str);
        }

        return temp;
    }
}

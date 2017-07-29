package org.mercury.oschina.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Mercury on 2017/7/29.
 */

public class PropertyHelper {

    public static Properties get() {
        Properties prop = new Properties();
        InputStream is = null;
        File file = new File("/");
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File isFile : files) {

            }
        }
        try {
            is = new FileInputStream("local.properties");
            prop.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return prop;
    }
}

package cn.sdt.pushclient.util;

import android.content.Context;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.sdt.pushclient.FLog;

/**
 * Created by Administrator on 2018/5/14.
 */

public class FileUtil {
    public static void copyFile(String fileName, Context context) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = context.getAssets().open(fileName);
            File file = new File(context.getFilesDir(), fileName);
            FLog.d(file.getAbsolutePath() + " exists:" + file.exists());
            if (!file.exists()) {
                file.createNewFile();
                os = new FileOutputStream(file);
                byte[] data = new byte[1024];
                int len = 0;
                while ((len = is.read(data, 0, 1024)) != -1) {
                    os.write(data);
                }
                os.flush();
                FLog.d("copy assets file to sdcard success");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIo(is);
            closeIo(os);
        }
    }

    public static void closeIo(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

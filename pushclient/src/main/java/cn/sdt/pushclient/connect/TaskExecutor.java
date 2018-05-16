package cn.sdt.pushclient.connect;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2018/5/8.
 */

public class TaskExecutor {
    protected final static ExecutorService mAdvertThreadPool = Executors.newCachedThreadPool();

    public static void executeTask(Runnable task) {
        mAdvertThreadPool.execute(task);
    }
}

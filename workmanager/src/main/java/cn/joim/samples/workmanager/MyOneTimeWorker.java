package cn.joim.samples.workmanager;

import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Worker;

/**
 * Created by joim on 2018/6/13.
 */

public class MyOneTimeWorker extends Worker {

    @NonNull
    @Override
    public WorkerResult doWork() {
        Log.i("joim", "handle-1");

        return WorkerResult.SUCCESS;
    }
}

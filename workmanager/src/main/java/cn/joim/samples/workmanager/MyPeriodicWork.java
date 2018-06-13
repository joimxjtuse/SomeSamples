package cn.joim.samples.workmanager;

import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Worker;

/**
 * Created by joim on 2018/6/13.
 */

public class MyPeriodicWork extends Worker {

    private int mHandleTimeCnt = 0;

    @NonNull
    @Override
    public WorkerResult doWork() {
        Log.i("joim", "handle- times : " + (++mHandleTimeCnt));
        return WorkerResult.SUCCESS;
    }
}

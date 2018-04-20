package cn.joim.samples.headerandfooterrecyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by joim on 2018/4/20.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);

        initView(itemView);
    }

    protected abstract void initView(@NonNull View itemView);

    public void onRecycle() {
        // Just like cacel image request.
    }
}

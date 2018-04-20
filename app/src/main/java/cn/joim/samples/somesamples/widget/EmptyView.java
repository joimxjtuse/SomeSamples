package cn.joim.samples.somesamples.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by joim on 2018/4/20.
 */

public class EmptyView extends AppCompatTextView {


    public EmptyView(Context context) {
        this(context, null);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initEmptyContent();
    }

    private void initEmptyContent() {
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        setTextSize(30);
        setText("点击重试!");

        setGravity(Gravity.CENTER);
    }
}

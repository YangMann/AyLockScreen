package edu.SJTU.AyLockScreen.layouts;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.RelativeLayout;
import android.widget.TextView;
import edu.SJTU.AyLockScreen.R;

/**
 * Created by Yang Zhang on 2014/4/4.
 */
public class SliderRelativeLayout extends RelativeLayout { // TODO: 未完成

    private static String TAG = "SLIDER_RELATIVE_LAYOUT";

    private Context mContext; // 初始化图片拖拽时的Bitmap对象
    private Bitmap dragBitmap; // 拖拽图片

    private TextView sliderIcon; // 拖拽控件

    public SliderRelativeLayout(Context context) {
        super(context);
        mContext = context;
    }

    private void initDragBitmap() {
        if (dragBitmap == null) {
            dragBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
        }
    }
}
